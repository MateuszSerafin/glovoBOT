package com.gmail.genek530.commons;

import com.gmail.genek530.userContainer;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.GZIPInputStream;

import static com.gmail.genek530.commons.utils.returnGMDtime;

public class urlHelper {
    //responsible for handling sending and receiving requests

    public enum glovoURL {
        LOGIN("https://api.glovoapp.com/oauth/token"),
        DEVICEID("https://api.glovoapp.com/v3/devices"),
        CALENDAR("https://api.glovoapp.com/v3/scheduling/calendar"),
        SLOT("https://api.glovoapp.com/v3/scheduling/slots/"),
        REVOKE("https://api.glovoapp.com/oauth/revoke");

        private String url;

        glovoURL(String envUrl) {
            this.url = envUrl;
        }

        public String gettype() {
            return url;
        }
        
        //Slots require ID at the end, /slot/12312 as example, so it just appends to it.
        public String getSlot(String ID){
            return url + ID;
        }

    }
    public enum requestType {
        GET("GET"),
        POST("POST"),
        PUT("PUT");

        private String url;

        requestType(String envUrl) {
            this.url = envUrl;
        }

        public String getUrl() {
            return url;
        }
    }

    private String dynamicSessionId;
    private String deviceID = "";

    //every relogin deviceID was empty. 
    public void resetdeviceID(){deviceID = "";};

    public void setDeviceID(String deviceID){
        this.deviceID = deviceID;
    }
    
    //those tokens are dynamic and change sometimes 
    private String authorization;
    private String refreshToken;


    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    userContainer userContainer;

    public urlHelper(userContainer userContainer, String dynamicSessionId){
        this.userContainer = userContainer;
        this.dynamicSessionId = dynamicSessionId;
    }
    //all headers that are static
    private final Map<String,String> reusableHeader = new HashMap<String, String>() {{
        //Those nulls bellow needs setting later on, filled header parameters that i could
        put("Host","api.glovoapp.com");
        put("glovo-app-platform","Android");
        put("glovo-app-version", "2.173.0");
        put("glovo-api-version", "8");
        put("glovo-app-type", "courier");
        put("glovo-app-development-state", "Production");
        put("glovo-os-version", "11");
        put("glovo-language-code","pl");
        //put("date", null);
        put("user-agent","Glover/2.173.0  (Linux; Android 11; Model sdk_gphone_x86; Manufacturer Google; Brand google; Device generic_x86_arm; Product sdk_gphone_x86) okhttp/4.9.3 SDK/30");
        //put("glovo-dynamic-session-id", null);
        //put("glovo-device-id", null);
        put("content-type", "application/json; charset=UTF-8");
        put("accept-encoding", "gzip");
    }};
    private void buildHeader(HttpsURLConnection beforeAddingheaders, boolean authorization, boolean modifiedTime) {
        for (Map.Entry<String, String> entry : reusableHeader.entrySet()) {
            beforeAddingheaders.setRequestProperty(entry.getKey(), entry.getValue());
        }
        String czas = returnGMDtime();
        beforeAddingheaders.setRequestProperty("date", czas);
        beforeAddingheaders.setRequestProperty("glovo-dynamic-session-id", dynamicSessionId);
        beforeAddingheaders.setRequestProperty("glovo-device-id", deviceID);

        if (authorization) {
            beforeAddingheaders.setRequestProperty("authorization", this.authorization);
        }

        if (modifiedTime) {
            beforeAddingheaders.setRequestProperty("if-modified-since", czas); //without adding if-modified since only today times are shown rather whole week.
        }
    }
    
    private AtomicInteger requestLIMIT = new AtomicInteger(0);
    private HttpsURLConnection firstStage(String glovoUrl, String requestTYPE, boolean authorization, boolean modifiedTime) throws Exception {
        if(requestLIMIT.get() > 100){
            throw new Exception("API ");
        }
        System.out.println(requestLIMIT);
        requestLIMIT.addAndGet(1);

        URL url = new URL(glovoUrl);
        HttpsURLConnection c = (HttpsURLConnection) url.openConnection();
        c.setRequestMethod(requestTYPE);
        buildHeader(c,authorization,modifiedTime);
        return c;
    }
    private void handleInput(HttpsURLConnection connection, byte[] input) throws IOException {
        try(OutputStream os = connection.getOutputStream()) {
            os.write(input, 0, input.length);
        }
    }
    private InputStream handleGzip(InputStream inputStream, boolean gzip) throws IOException {
        if(gzip){
            return new GZIPInputStream(inputStream);
        }
        return inputStream;
    }
    private response handleOutput(HttpsURLConnection connection) throws IOException {
        int response = connection.getResponseCode();
        boolean responseBool = false;
        boolean gzip = false;
        if ("gzip".equals(connection.getContentEncoding())) {
            gzip = true;
        }
        InputStream inputStream = null;
        if(response / 100 == 2){
            responseBool = true;
            inputStream = handleGzip(connection.getInputStream(), gzip);
        } else {
            inputStream =  handleGzip(connection.getErrorStream(), gzip);
        }
        final byte[] buf = new byte[1024];
        final StringBuilder b = new StringBuilder();
        int read = 0;
        while ((read = inputStream.read(buf)) != -1) {
            b.append(new String(buf, 0, read));
        }
        return new response(responseBool, b.toString());
    }

    public response requestWithoutOutput(String glovoUrl, String requestTYPE, byte[] json, boolean authorization, boolean modifiedTime) throws Exception {
        HttpsURLConnection connection = firstStage(glovoUrl, requestTYPE, authorization, modifiedTime);
        connection.setDoOutput(true);
        connection.setDoInput(true);
        handleInput(connection, json);
        return handleOutput(connection);
    }

    public response requestWithInput(String glovoUrl, String requestTYPE, boolean authorization, boolean modifiedTime) throws Exception {
        HttpsURLConnection connection = firstStage(glovoUrl, requestTYPE, authorization, modifiedTime);
        connection.setDoOutput(true);
        connection.setDoInput(true);
        return handleOutput(connection);
    }
}
