package com.example.MovieVerse;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class WebServiceCall extends Thread {
    String richiesta;
    public void inviaRichiesta (String x){
        this.richiesta = x;
        start();
    }
    @Override
    public void run() {
        super.run();
        try {
            //richiesta = "https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=en-US&page=1&sort_by=popularity.desc";
            URL url = new URL(this.richiesta);
            URLConnection uc = url.openConnection();
            String auth = "Bearer " + new String("eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJlZGM3Nzc1MTU2YzBjM2U2NmE0MTJjNTI3ZGYzNDEzMyIsInN1YiI6IjY2Mjc3MTI1YjI2ODFmMDE3YzczZDM3NyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.yyv-z4Za_38FbEJ4yJVV7MqWRILPIddgQmdJQTuuMVU");
            uc.setRequestProperty("Authorization", auth);

            BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
            String inputLine;
            String response = "";
            while ((inputLine = in.readLine()) != null)
                response += inputLine;
            in.close();


            Log.d("WSP",response);
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println(e.toString());
        }
    }
}