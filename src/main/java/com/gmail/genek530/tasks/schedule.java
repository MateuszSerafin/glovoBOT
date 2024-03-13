package com.gmail.genek530.tasks;

import com.gmail.genek530.commons.response;
import com.gmail.genek530.commons.urlHelper;
import com.gmail.genek530.userContainer;
import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;

public class schedule {

    //BUGBUG tested with multiple accounts sometimes one account sees slot as booked but it was not and opposite
    //this is bug from their api or just some weird abuse prevention
    HashMap<String, Integer> doneIDs = new HashMap<String, Integer>();

    userContainer userContainerInstance;

    urlHelper urlhelp;

    private final String tosend = "{\"booked\":true,\"storeAddressId\":%s}";

    private ExecutorService executor;

    public schedule(userContainer userContainer, urlHelper urlhelper, ExecutorService executorService, ArrayList<userContainer> kontenery){
        this.userContainerInstance = userContainer;
        this.urlhelp = urlhelper;
        this.executor = executorService;
        this.kontenery = kontenery;
    }

    private ArrayList<userContainer> kontenery;

    //checkSchedule and if found hour take this hour + inform other users in ArrayList<userContainer> about available hour
    //Up to 3 couriers could take some hours, at least that's how it was in my city.
    public boolean checkSchedule(){
        try {
            response prbablyJson = urlhelp.requestWithInput(urlHelper.glovoURL.CALENDAR.gettype(), urlHelper.requestType.GET.getUrl(), true, true);
            Any any  = JsonIterator.deserialize(prbablyJson.getString());

            for(Any Day : any.get("days")){
                for(Any slot : Day.get("slots")){
                    //System.out.println(String.format("%s %s", Day.get("name"), slot.get("id").toString()));
                    if(slot.get("status").toString().equals("AVAILABLE")){
                        String slotID = slot.get("id").toString();
                        if(!doneIDs.containsKey(slotID)) doneIDs.put(slotID, 1);
                        //todo
                        int frommap = doneIDs.get(slotID);
                        if(frommap > 2){
                            continue;
                        }
                        String address = slot.get("addresses").toString();

                        byte[] tosendBytes = String.format(tosend, address).getBytes(StandardCharsets.UTF_8);

                        for(userContainer kont : kontenery){
                            executor.submit(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        System.out.println(kont.urlherlper.requestWithoutOutput(urlHelper.glovoURL.SLOT.getSlot(slotID), urlHelper.requestType.PUT.getUrl(), tosendBytes,true, false).getString());
                                    } catch (Exception e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            });
                        }
                        userContainerInstance.log(String.format("slotID %s", slotID));
                        int toadd = frommap + 1;
                        doneIDs.put(slotID, toadd);
                    }
                }
            };
            return true;
        } catch (Exception e) {
            userContainerInstance.log(String.format("checkSchedule exception %s", e.toString()));
            return false;
        }
    }

}
