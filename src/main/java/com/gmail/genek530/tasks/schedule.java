package com.gmail.genek530.tasks;

import com.gmail.genek530.commons.reponse;
import com.gmail.genek530.commons.urlhelper;
import com.gmail.genek530.container;
import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class schedule {

    //BUGBUG tested with multiple accounts sometimes one account sees slot as booked but it was not and opposite
    //this is bug from their api or just some weird abuse prevention
    HashMap<String, Integer> doneIDs = new HashMap<String, Integer>();

    container containerInstance;

    urlhelper urlhelp;

    private final String tosend = "{\"booked\":true,\"storeAddressId\":%s}";

    private ExecutorService executor;

    public schedule(container container, urlhelper urlhelper, ExecutorService executorService, ArrayList<container> kontenery){
        this.containerInstance = container;
        this.urlhelp = urlhelper;
        this.executor = executorService;
        this.kontenery = kontenery;
    }

    private ArrayList<container> kontenery;

    //Skanowanie co 10 sekund checkowanie ID wymaga innej implementacji
    public boolean checkSchedule(){
        try {
            reponse prbablyJson = urlhelp.reqBezwejscia(urlhelper.glovoURL.CALENDAR.gettype(), urlhelper.requestTYP.GET.getUrl(), true, true);
            Any any  = JsonIterator.deserialize(prbablyJson.getString());

            //todo
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

                        for(container kont : kontenery){
                            executor.submit(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        System.out.println(kont.urlherlper.reqZwejsciemiwyjesciem(urlhelper.glovoURL.SLOT.getSlot(slotID), urlhelper.requestTYP.PUT.getUrl(), tosendBytes,true, false).getString());
                                    } catch (Exception e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            });
                        }



                        containerInstance.log(String.format("slotID %s", slotID));
                        int toadd = frommap + 1;
                        doneIDs.put(slotID, toadd);
                    }
                }
            };
            return true;
        } catch (Exception e) {
            containerInstance.log(String.format("checkSchedule exception %s", e.toString()));
            return false;
        }
    }

}
