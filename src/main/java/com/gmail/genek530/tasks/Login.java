package com.gmail.genek530.tasks;

import com.gmail.genek530.commons.response;
import com.gmail.genek530.commons.urlHelper;
import com.gmail.genek530.userContainer;
import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;

import java.nio.charset.StandardCharsets;

public class Login {

    userContainer userContainerInstance;
    urlHelper urlhelp;

    public Login(userContainer userContainer, urlHelper urlhelp){
        this.userContainerInstance = userContainer;
        this.urlhelp = urlhelp;
    }

    public boolean login() {
        //each login generates different deviceID
        this.urlhelp.resetdeviceID();
        String jsonToSend = String.format("{\"grantType\":\"password\",\"password\":\"%s\",\"termsAndConditionsChecked\":false,\"userType\":\"courier\",\"username\":\"%s\"}", userContainerInstance.getHaslo(), userContainerInstance.getEmail());
        try{
            response probablyJson = this.urlhelp.requestWithoutOutput(urlHelper.glovoURL.LOGIN.gettype(), urlHelper.requestType.POST.getUrl(), jsonToSend.getBytes(StandardCharsets.UTF_8), true, false);
            if(!probablyJson.isSuccess()){
                System.out.println(String.format("Failed login request for %s, ERROR %s", userContainerInstance.getEmail(), probablyJson.getString()));
                return false;
            }
            Any any  = JsonIterator.deserialize(probablyJson.getString());
            String accessToken =  any.get("accessToken").toString();
            String refreshToken = any.get("refreshToken").toString();
            this.urlhelp.setAuthorization(accessToken);
            this.urlhelp.setRefreshToken(refreshToken);
            System.out.println(String.format("%s logged in -> %s", userContainerInstance.getEmail(), accessToken));
            return true;

        } catch (Exception e){
            System.out.println(String.format("Login for %s did not work", userContainerInstance.getEmail()));
            return false;
        }
    }
}
