package com.gmail.genek530.tasks;

import com.gmail.genek530.commons.reponse;
import com.gmail.genek530.commons.urlhelper;
import com.gmail.genek530.container;
import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;

import javax.net.ssl.HttpsURLConnection;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;

public class zaloguj {

    container containerInstance;
    urlhelper urlhelp;

    public zaloguj(container container, urlhelper urlhelp){
        this.containerInstance = container;
        this.urlhelp = urlhelp;
    }

    private final String jsonToSend = "{\"grantType\":\"password\",\"password\":\"%s\",\"termsAndConditionsChecked\":false,\"userType\":\"courier\",\"username\":\"%s\"}";

    public boolean zaloguj() {
        //kazdy login generuje inny device ID
        this.urlhelp.resetdeviceID();
        String jsonToSend = String.format("{\"grantType\":\"password\",\"password\":\"%s\",\"termsAndConditionsChecked\":false,\"userType\":\"courier\",\"username\":\"%s\"}", containerInstance.getHaslo(), containerInstance.getEmail());
        try{
            reponse probablyJson = this.urlhelp.reqZwejsciemiwyjesciem(urlhelper.glovoURL.LOGIN.gettype(), urlhelper.requestTYP.POST.getUrl(), jsonToSend.getBytes(StandardCharsets.UTF_8), true, false);
            if(!probablyJson.isSuccess()){
                System.out.println(String.format("Failed zaloguj dla %s, ERROR %s", containerInstance.getEmail(), probablyJson.getString()));
                return false;
            }
            Any any  = JsonIterator.deserialize(probablyJson.getString());
            String accessToken =  any.get("accessToken").toString();
            String refreshToken = any.get("refreshToken").toString();
            this.urlhelp.setAuthorization(accessToken);
            this.urlhelp.setRefreshToken(refreshToken);
            System.out.println(String.format("%s sie zalogowal -> %s", containerInstance.getEmail(), accessToken));
            return true;

        } catch (Exception e){
            System.out.println(String.format("Logowanie dla %s umrarlo", containerInstance.getEmail()));
            return false;
        }
    }
}
