package com.gmail.genek530;

import com.gmail.genek530.commons.urlhelper;
import com.gmail.genek530.tasks.deviceID;
import com.gmail.genek530.tasks.revoke;
import com.gmail.genek530.tasks.schedule;
import com.gmail.genek530.tasks.zaloguj;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

import static com.gmail.genek530.commons.utils.returnGMDtime;

public class container implements Runnable {

    private ArrayList<String> logger = new ArrayList<String>();
    public void log(String message){
        logger.add(returnGMDtime() + String.format("-> %s", message));
    }

    public ArrayList<String> getLogger() {
        return logger;
    }

    private String email;

    public String getEmail() {
        return email;
    }

    private String haslo;

    public String getHaslo() {
        return haslo;
    }

    public urlhelper urlherlper;

    //Taski
    zaloguj zalogujInstance;
    deviceID deviceIDInstance;

    schedule scheduleInstance;

    revoke revokeInstance;

    private ExecutorService executorService;

    public container(ExecutorService executorService, String email, String haslo, String dynamicSessionId, String androidToken, String gcmToken, String gpsToken, String mac, ArrayList<container> kontenery){
        this.executorService = executorService;
        this.urlherlper = new urlhelper(this, dynamicSessionId);
        this.email = email;
        this.haslo = haslo;

        this.zalogujInstance = new zaloguj(this, this.urlherlper);
        this.deviceIDInstance = new deviceID(this, this.urlherlper, androidToken, gcmToken, gpsToken, mac);
        this.scheduleInstance = new schedule(this, this.urlherlper, executorService, kontenery);
        this.revokeInstance = new revoke(this, this.urlherlper);
    }



    // container was supposed to be called by thread and scan on each own
    //unforuntately using multiple accounts to perform scannign yielded  better results
    //this is leftover that i leave.
    @Override
    public void run() {
        /*
        if(!this.zalogujInstance.zaloguj()) {
            System.out.println(this.email + " Failed to login ");
            return;
        }
        if(!this.deviceIDInstance.deviceIDcon()){
            System.out.println("Failed to get proper device ID");
            return;
        };
        int among = 0;

        while(true){
            if(!this.scheduleInstance.checkSchedule()) {
                System.out.println("check sechdule resulted in exception");
                zalogujInstance.zaloguj();
            };
            System.out.println(returnGMDtime() + "-> lol");
        }

         */
    }
}
