package com.gmail.genek530.tasks;

import com.gmail.genek530.commons.reponse;
import com.gmail.genek530.commons.urlhelper;
import com.gmail.genek530.container;
import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;

import java.nio.charset.StandardCharsets;

public class revoke {
    // this is actually funny but when you unlogin the token is still valid for like 30 minutes (at least it was like that)
    container containerInstance;
    urlhelper urlhelp;

    public revoke(container container, urlhelper urlhelp) {
        this.containerInstance = container;
        this.urlhelp = urlhelp;
    }

    public void unlogin(){
        try {
            reponse probablyaJson = this.urlhelp.reqZwejsciemiwyjesciem(urlhelper.glovoURL.REVOKE.gettype(), urlhelper.requestTYP.POST.getUrl(), String.format("{\"token\":\"%s\"}", this.urlhelp.getRefreshToken()).getBytes(StandardCharsets.UTF_8), true, false);
            System.out.println(String.format("unlogin output %s", probablyaJson.isSuccess()));
        } catch (Exception e){
            System.out.println(String.format("Exception in unlogin for %s", containerInstance.getEmail()));
        }

    }


}
