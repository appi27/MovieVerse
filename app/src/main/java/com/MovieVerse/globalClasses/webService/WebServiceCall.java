package com.MovieVerse.globalClasses.webService;

import android.util.Log;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class WebServiceCall extends Thread {
    private String richiesta;
    private JSONObject rispostaJson;
    public void sedRequest (String x){
        this.richiesta = x;
        this.start();
    }
    public JSONObject getRispostaJson() {
        return rispostaJson;
    }
    @Override
    public void run() {
        super.run();
        try {
            URL url = new URL(this.richiesta);
            URLConnection uc = url.openConnection();
            String auth = "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJlZGM3Nzc1MTU2YzBjM2U2NmE0MTJjNTI3ZGYzNDEzMyIsInN1YiI6IjY2Mjc3MTI1YjI2ODFmMDE3YzczZDM3NyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.yyv-z4Za_38FbEJ4yJVV7MqWRILPIddgQmdJQTuuMVU";
            uc.setRequestProperty("Authorization", auth);

            BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
            String inputLine;
            StringBuilder response  = new StringBuilder();
            while ((inputLine = in.readLine()) != null)
                response.append(inputLine);
            in.close();

            rispostaJson = new JSONObject(response.toString());
            Log.d("WSR_rispostaJson", rispostaJson.toString());
        }
        catch (Exception e){
            Log.e("WSR_error", e.toString());
        }
    }
}