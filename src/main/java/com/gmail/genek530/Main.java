package com.gmail.genek530;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main{

    public static void main(String[] args) {
        //This executor is responsbile for sending requests for each user
        ExecutorService executor = Executors.newFixedThreadPool(100);
        //My bot works better with multiple users as i can scan more often for available hours
        ArrayList<userContainer> users = new ArrayList<>();

        // All the parameters except email and password are just same length as from original requests. But i just manually changed them.
        //container i3 = new container(executor, "*************@****.com", "*************", "4d17cea3-fc33-4b8e-ad04-834eeba1baa8", "b11941cb25f28a2a", "daAR5GOXSti_fjNXo-rdax:APC91bHs9gjoeQjzAWZObPs5qbSnVGGnhUzZq_0gWn0A5nADMo14aNh6_75xSwFOq0G0C9iO04Ysf47LjbE3LSq0kEeHyBpW11LBHDrD5Jd-VzDIFkudBgx8mj1tghB7Q5r4uaKfvqaN", "dbBa3GOXsti_fjNXo-rdXx", "dbRA5gOXSti_fjNXo-raxx", users);
        //i3.zalogujInstance.login();
        //i3.deviceIDInstance.deviceIDcon();
        //konenery.add(i3);

        userContainer i4 = new userContainer(executor, "*************@****.com", "*************","4d17cee4-fc22-4a8e-ad04-834eaba1bcc8", "b12941cb25f28b9d", "dbAR5GOXSti_fjNXo-rdXx:APA91bHs9gjoaQjzAWZObPs5qbSnVGGnhUnZq_0gWn0A5nDDMo14aNh6_75xSwfOQ0G0C8iO04Ysf47LjbE3LSq0kEeHyBpW11LBHDrD5Jd-VzDIFkudagx8mj1tghB7Q5r4uaKfvqaN", "dbAR5GOAsti_fjNXo-rdXx", "dbAa5GOXSti_fjNXo-rdXx",  users);
        i4.loginInstance.login();
        i4.deviceIDInstance.deviceIDcon();
        users.add(i4);

        //This yielded best results. Made each account scann and if it found a slot it made other accounts try taking the slot.
        //Problem is sometimes they release hours in different times.
        //So one account lets say scanning at 1 second would be just demolished by other bot.
        //100 was the api limit
        //with 3 accounts it got 300 api limit so i could do scanning much faster
        //Also it was checking schedule because it was sending into particular slots only.
        //if it would just spam into all of the slots it would became quickly rate limited. and your account would be temp banned.
        while(true){
            for(userContainer kontener : users){
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
        //overall i was happy with this program i did it over weekend please dont judge <- i just cleaned up it a little bit
        //it's lacking lot's of potential functions like changing api limit rate somewhere, and features however it was doing it's job
    }
}

