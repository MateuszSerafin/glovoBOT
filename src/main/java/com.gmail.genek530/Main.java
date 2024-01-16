package com.gmail.genek530;

import com.gmail.genek530.commons.reponse;
import com.gmail.genek530.commons.urlhelper;
import com.jsoniter.JsonIterator;
import com.jsoniter.annotation.JsonObject;
import com.jsoniter.any.Any;

import javax.net.ssl.HttpsURLConnection;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Main{

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(100);
        ArrayList<container> konenery = new ArrayList<>();

        // All of parameters except email and password are just same length as from orginal requests. But i just manually changed them.

        //container i1 = new container(executor, "*************@****.com", "*************", "4d17cee4-fc22-4a8e-ad04-834eeba1bcc8", "a11941cb25f28b9d", "dbAR5GOXSti_fjNXo-rdXx:APA91bHs9gjoeQjzAWZObPs5qbSnVGGnhUnZq_0gWn0A5nDDMo14aNh6_75xSwfOQ0G0C8iO04Ysf47LjbE3LSq0kEeHyBpW11LBHDrD5Jd-VzDIFkudBgx8mj1tghB7Q5r4uaKfvqaN", "dbAR5GOXSti_fjNXo-rdXx", "dbAR5GOXSti_fjNXo-rdXx", konenery);
        //i1.zalogujInstance.zaloguj();
        //i1.deviceIDInstance.deviceIDcon();
        //konenery.add(i1);


        //container i2 = new container(executor, "*************@****.com", "*************","4d17cee4-fc33-4b8e-ad04-834eeba1baa8", "b11941cb25f28b9d", "daAR5GOXSti_fjNXo-rdax:APC91bHs9gjoeQjzAWZObPs5qbSnVGGnhUnZq_0gWn0A5nDDMo14aNh6_75xSwfOQ0G0C8iO04Ysf47LjbE3LSq0kEeHyBpW11LBHDrD5Jd-VzDIFkudBgx8mj1tghB7Q5r4uaKfvqaN", "dbBA1GOXSti_fjNXo-rdXx", "dbRA5GOXSti_fjNXo-rcXx", konenery);
        //i2.zalogujInstance.zaloguj();
        //i2.deviceIDInstance.deviceIDcon();
        //konenery.add(i2);


        //container i3 = new container(executor, "*************@****.com", "*************", "4d17cea3-fc33-4b8e-ad04-834eeba1baa8", "b11941cb25f28a2a", "daAR5GOXSti_fjNXo-rdax:APC91bHs9gjoeQjzAWZObPs5qbSnVGGnhUzZq_0gWn0A5nADMo14aNh6_75xSwFOq0G0C9iO04Ysf47LjbE3LSq0kEeHyBpW11LBHDrD5Jd-VzDIFkudBgx8mj1tghB7Q5r4uaKfvqaN", "dbBa3GOXsti_fjNXo-rdXx", "dbRA5gOXSti_fjNXo-raxx", konenery);
        //i3.zalogujInstance.zaloguj();
        //i3.deviceIDInstance.deviceIDcon();
        //konenery.add(i3);

        container i4 = new container(executor, "*************@****.com", "*************","4d17cee4-fc22-4a8e-ad04-834eaba1bcc8", "b12941cb25f28b9d", "dbAR5GOXSti_fjNXo-rdXx:APA91bHs9gjoaQjzAWZObPs5qbSnVGGnhUnZq_0gWn0A5nDDMo14aNh6_75xSwfOQ0G0C8iO04Ysf47LjbE3LSq0kEeHyBpW11LBHDrD5Jd-VzDIFkudagx8mj1tghB7Q5r4uaKfvqaN", "dbAR5GOAsti_fjNXo-rdXx", "dbAa5GOXSti_fjNXo-rdXx", konenery);
        i4.zalogujInstance.zaloguj();
        i4.deviceIDInstance.deviceIDcon();
        konenery.add(i4);


        //This yielded best results. Made each account scann and if it found a slot it made other accounts try taking the slot.
        //Problem is sometimes they release hours in different times.
        //So one account lets say scanning at 1 second would be just demolished by other bot.
        //100 was the api limit
        //with 3 accounts it got 300 api limit so i could do scanning much faster
        //Also it was checking schedule because it was sending into particular slots only.
        //if it would just spam into all of the slots it would became quickly rate limited. and your account would be temp banned.
        /*

        while(true){
            for(container kontener : konenery){
                executor.submit(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            kontener.scheduleInstance.checkSchedule();
                        } catch (Exception e){

                        }
                    }
                });
                try {
                    Thread.sleep(100);
                } catch (Exception e){

                }
            }

        }

        //some debug version of printing current schedule
        i1.zalogujInstance.zaloguj();
        ArrayList<String> ID = new ArrayList<>();

        try{
            reponse ri1 = i1.urlherlper.reqBezwejscia(urlhelper.glovoURL.CALENDAR.gettype(), urlhelper.requestTYP.GET.getUrl(), true, true);

            Any any  = JsonIterator.deserialize(ri1.getString());
            for(int i = 0; i < 1; i++){
                Any DAY = any.get("days").get(i);

                for(int j = 12; j < 23; j++){
                    ID.add(DAY.get("slots").get(j).get("id").toString());
                }
            }
            3 - czw;
            4 - pt;
            5 - sob;
            6 - ndz;

        } catch (Exception e){

        }
        System.out.println(ID);

        //crappy attempt at console gui
        while(true){
            Scanner input = new Scanner(System.in);
            String parsedInput = input.next();
            if(parsedInput.equalsIgnoreCase("quit")){
                System.out.println("Qutting");
                break;
            }

            if(parsedInput.equalsIgnoreCase("logs")){
                for(String log : i1.getLogger()){
                    System.out.println(log);
                }

            }
        }
        */
    //overall i was happy with this program i did it over weekend please dont judge :)
    }
}

