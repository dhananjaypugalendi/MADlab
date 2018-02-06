package com.dhananjay.lab5;

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
 * Created by dhananjay on 6/2/18.
 */

public class HttpTask extends AsyncTask<String, Void, List<Item>> {

    public static final String REQUEST_METHOD = "GET";
    public static final int READ_TIMEOUT = 15000;
    public static final int CONNECTION_TIMEOUT = 15000;

    @Override
    protected List<Item> doInBackground(String... strings) {
        String stringUrl = strings[0];
        List<Item> itemList = new ArrayList<>();

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

            /*{ "json" :[
               {
                 "id": 1,
                 "text": "How do I read JSON on Android?"
               },
               {
                 "id": 2,
                 "text": "@android_newb"
               },
                .....
               {
                 "id": 11,
                 "text": "@android_newb"
               }
             ]}*/

            JSONObject json = new JSONObject(result);
            JSONArray jsonArray = (JSONArray) json.get("json");
            for(int i=0; i<jsonArray.length();i++){
                String item1 = ((JSONObject)jsonArray.get(i)).get("id").toString();
                String item2 = ((JSONObject)jsonArray.get(i)).get("text").toString();
                itemList.add(new Item(item1, item2));
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return itemList;
    }

    @Override
    protected void onPostExecute(List<Item> itemList) {
        super.onPostExecute(itemList);
    }
}
