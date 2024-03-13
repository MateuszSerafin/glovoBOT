package com.gmail.genek530.commons;

public class response {
    //this class should be returned by urlhelper whenever request is done
    //IF FALSE STRING ERRROR
    //IF TRUE STRING OUTPUT
    private boolean success;
    private String string;

    public response(boolean success, String string){
        this.success = success;
        this.string = string;
    }

    public String getString() {
        return string;
    }

    public boolean isSuccess() {
        return success;
    }
}
