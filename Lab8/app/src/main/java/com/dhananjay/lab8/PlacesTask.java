package com.dhananjay.lab8;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by dhananjay on 6/3/18.
 */

public class PlacesTask extends AsyncTask<String, Void, List<Poi>> {

    public static final String REQUEST_METHOD = "GET";
    public static final int READ_TIMEOUT = 15000;
    public static final int CONNECTION_TIMEOUT = 15000;

    @Override
    protected List<Poi> doInBackground(String... strings) {
        String stringUrl = strings[0];
        try {
            URL url = new URL(stringUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(REQUEST_METHOD);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);
            connection.connect();
            String inputLine;
            InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
            BufferedReader reader = new BufferedReader(streamReader);
            StringBuilder stringBuilder = new StringBuilder();
            while ((inputLine = reader.readLine()) != null){
                stringBuilder.append(inputLine);
            }
            reader.close();
            streamReader.close();
            String result = stringBuilder.toString();
            JSONObject resultJson = new JSONObject(result);
            JSONArray placesArray = (JSONArray) resultJson.get("results");
            List<Poi> poiList = new ArrayList<>();
            for(int i=0;i<placesArray.length();i++){
                JSONObject place = (JSONObject) placesArray.get(i);
                Double lat = (Double) ((JSONObject)((JSONObject) place.get("geometry")).get("location")).get("lat");
                Double lng = (Double) ((JSONObject)((JSONObject) place.get("geometry")).get("location")).get("lng");
                String name = place.get("name").toString();
                poiList.add(new Poi(name,lat,lng));
            }
            return poiList;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
