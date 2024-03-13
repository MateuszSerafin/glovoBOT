package com.gmail.genek530.tasks;

import com.gmail.genek530.commons.response;
import com.gmail.genek530.commons.urlHelper;
import com.gmail.genek530.userContainer;

import java.nio.charset.StandardCharsets;

public class revoke {
    // this is actually funny but when you unlogin the token is still valid for like 30 minutes (at least it was like that)
    userContainer userContainerInstance;
    urlHelper urlhelp;

    public revoke(userContainer userContainer, urlHelper urlhelp) {
        this.userContainerInstance = userContainer;
        this.urlhelp = urlhelp;
    }

    public void unlogin(){
        try {
            response probablyaJson = this.urlhelp.requestWithoutOutput(urlHelper.glovoURL.REVOKE.gettype(), urlHelper.requestType.POST.getUrl(), String.format("{\"token\":\"%s\"}", this.urlhelp.getRefreshToken()).getBytes(StandardCharsets.UTF_8), true, false);
            System.out.println(String.format("unlogin output %s", probablyaJson.isSuccess()));
        } catch (Exception e){
            System.out.println(String.format("Exception in unlogin for %s", userContainerInstance.getEmail()));
        }

    }


}
