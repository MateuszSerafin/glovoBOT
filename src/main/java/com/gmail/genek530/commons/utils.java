package com.gmail.genek530.commons;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class utils {
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, d M y H:m:s").withZone(ZoneId.of("Europe/London"));
    public static String returnGMDtime(){
        return formatter.format(Instant.now()) + " GMT";
    }
}
