package com.gmail.genek530.tasks;

import com.gmail.genek530.commons.response;
import com.gmail.genek530.commons.urlHelper;
import com.gmail.genek530.userContainer;
import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;

import java.nio.charset.StandardCharsets;

public class deviceID {
    userContainer userContainerInstance;
    urlHelper urlhelp;
    private final String jsonToSend = "{\"androidId\":\"%s\",\"appVersion\":\"2.173.0\",\"gcmToken\":\"%s\",\"gpsAdid\":\"%s\",\"id\":null,\"isFCMEnabled\":true,\"mac\":\"%s\",\"model\":\"google sdk_gphone_x86\",\"notificationsVersion\":2,\"os\":\"ANDROID\",\"osVersion\":\"11\",\"type\":\"Mobile\",\"urn\":\"\"}";
    private String androidToken;
    private String gcmToken;
    private String gpsToken;
    private String mac;

    public deviceID(userContainer userContainer, urlHelper urlhelp, String androidToken, String gcmToken, String gpsToken, String mac){
        this.userContainerInstance = userContainer;
        this.urlhelp = urlhelp;

        this.androidToken = androidToken;
        this.gcmToken = gcmToken;
        this.gpsToken = gpsToken;
        this.mac = mac;
    }

    public boolean deviceIDcon(){
        String jsonToSend = String.format("{\"androidId\":\"%s\",\"appVersion\":\"2.173.0\",\"gcmToken\":\"%s\",\"gpsAdid\":\"%s\",\"id\":null,\"isFCMEnabled\":true,\"mac\":\"%s\",\"model\":\"google sdk_gphone_x86\",\"notificationsVersion\":2,\"os\":\"ANDROID\",\"osVersion\":\"11\",\"type\":\"Mobile\",\"urn\":\"\"}", androidToken, gcmToken, gpsToken, mac);
        try{
            response probablyJson = this.urlhelp.requestWithoutOutput(urlHelper.glovoURL.DEVICEID.gettype(), urlHelper.requestType.POST.getUrl(), jsonToSend.getBytes(StandardCharsets.UTF_8), true, false);
            if(!probablyJson.isSuccess()){
                System.out.println(String.format("Failed getting device ID error: %s", probablyJson.getString()));
                return false;
            }
            Any any  = JsonIterator.deserialize(probablyJson.getString());
            String deviceID = any.get("id").toString();
            this.urlhelp.setDeviceID(deviceID);
            return true;
        } catch (Exception e){
            System.out.println(String.format("Failed getting device ID for %s", userContainerInstance.getEmail()));
            return false;
        }
    }
}
