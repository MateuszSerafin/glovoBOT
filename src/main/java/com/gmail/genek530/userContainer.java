package com.gmail.genek530;

import com.gmail.genek530.commons.urlHelper;
import com.gmail.genek530.tasks.deviceID;
import com.gmail.genek530.tasks.revoke;
import com.gmail.genek530.tasks.schedule;
import com.gmail.genek530.tasks.Login;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

import static com.gmail.genek530.commons.utils.returnGMDtime;

public class userContainer {
    //container contains functions for each user
    //Let's say you specify

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

    public urlHelper urlherlper;

    Login loginInstance;
    deviceID deviceIDInstance;

    schedule scheduleInstance;

    revoke revokeInstance;

    private ExecutorService executorService;

    public userContainer(ExecutorService executorService, String email, String haslo, String dynamicSessionId, String androidToken, String gcmToken, String gpsToken, String mac, ArrayList<userContainer> kontenery){
        this.executorService = executorService;
        this.urlherlper = new urlHelper(this, dynamicSessionId);
        this.email = email;
        this.haslo = haslo;

        this.loginInstance = new Login(this, this.urlherlper);
        this.deviceIDInstance = new deviceID(this, this.urlherlper, androidToken, gcmToken, gpsToken, mac);
        this.scheduleInstance = new schedule(this, this.urlherlper, executorService, kontenery);
        this.revokeInstance = new revoke(this, this.urlherlper);
    }
}
