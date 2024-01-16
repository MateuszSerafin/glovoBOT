package com.gmail.genek530.commons;

public class reponse {

    //IF FALSE STRING ERRROR
    //IF TRUE STRING OUTPUT
    private boolean success;
    private String string;



    public reponse(boolean success, String string){
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
