package com.gmail.genek530;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;

class firstsucess {

    public static void main(String[] args) throws IOException {
        // First test packet that was sucessfully working
        // I left it just for clarity reasons.
        URL url = new URL("https://api.glovoapp.com/v3/scheduling/slots/27093622");
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setRequestMethod("PUT");
        httpConn.setRequestProperty("Host", "api.glovoapp.com");
        httpConn.setRequestProperty("authorization", "eyJraWQiOiJvbGQiLCJhbGciOiJSUzUxMiJ9.eyJpYXQiOjE2ODAxMTE2MzAsImlzcyI6ImJhY2tlbmQiLCJleHAiOjE2ODAxMTI4MzAsInJvbGUiOiJBQ0NFU1MiLCJwYXlsb2FkIjoie1widXNlclJvbGVcIjpcIkNPVVJJRVJcIixcImlzU3RhZmZcIjpmYWxzZSxcInBlcm1pc3Npb25Hcm91cHNcIjpbXSxcImNpdHlHcm91cHNcIjpbXSxcInVzZXJJZFwiOjE0MzU0NDg1NSxcImdyYW50VHlwZVwiOlwiUEFTU1dPUkRcIn0iLCJ2ZXJzaW9uIjoiVjIiLCJqdGkiOiIwNjdkMjdhNC0wYzBhLTQzNWUtOGY2Ny02ODljZjg2Zjc1ZWYifQ.rHIC4YCo8ZQZc4DKWnCI5kKLumKTFWyicH7BFP1mTQBwzNq4BnDrKOWuApHPMmc3ZyL2eK8x5LHKQyqi8S8PM_zchMIpd6yS8UXNMXQp1IXiS3H5KwLEwdYGkQqlsRW-FUGk_tLxOyrx3pst9A_cPPOTWPiBFYhajKyxIKNXp_ABCkAEh0R0eDyRnl95OmU_rJLn3DQkCfvHO1JuRFTvTGhcGpi497DxQaN1e6EktoqyAzO7AEtf6r4MTvGvJRgQkQroes6VXXqSGeydnuFj0fq5RwHrkz6z3seYaTsA3AegqDk1jXCuuoL-9oqj_gYteXtdU4T93bL924t7HZ1-6Q");
        httpConn.setRequestProperty("glovo-app-platform", "Android");
        httpConn.setRequestProperty("glovo-app-version", "2.173.0");
        httpConn.setRequestProperty("glovo-api-version", "8");
        httpConn.setRequestProperty("glovo-app-type", "courier");
        httpConn.setRequestProperty("glovo-app-development-state", "Production");
        httpConn.setRequestProperty("glovo-os-version", "11");
        httpConn.setRequestProperty("glovo-language-code", "en");
        httpConn.setRequestProperty("date", "Wed, 29 Mar 2023 17:52:07 GMT");
        httpConn.setRequestProperty("user-agent", "Glover/2.173.0  (Linux; Android 11; Model sdk_gphone_x86; Manufacturer Google; Brand google; Device generic_x86_arm; Product sdk_gphone_x86) okhttp/4.9.3 SDK/30");
        httpConn.setRequestProperty("glovo-dynamic-session-id", "7d7f69d6-dfa0-4b6a-a62f-5be3c57e3110");
        httpConn.setRequestProperty("glovo-device-id", "1342228490");
        httpConn.setRequestProperty("content-type", "application/json; charset=UTF-8");
        httpConn.setDoOutput(true);
        OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());
        writer.write("{\"booked\":true,\"storeAddressId\":null}");
        writer.flush();
        writer.close();
        httpConn.getOutputStream().close();
        InputStream inputStream = null;
        try {
            if ("gzip".equals(httpConn.getContentEncoding())) {
                inputStream = new GZIPInputStream(httpConn.getErrorStream());
            } else {
                inputStream = httpConn.getErrorStream();
            }

            final byte[] buf = new byte[1024];
            final StringBuilder b = new StringBuilder();
            int read = 0;
            while ((read = inputStream.read(buf)) != -1) {
                b.append(new String(buf, 0, read));
            }
        } catch (Exception e){
            assert inputStream != null;
            inputStream.close();
        }
    }
}