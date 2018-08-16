package com.mannan.translateapi;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import static android.content.ContentValues.TAG;

/**
 * Created by hello on 16-Aug-18.
 */

public class TranslateAPI {

    String resp = null;
    String url = null;
    String langFrom = null;
    String langTo = null;
    String word = null;

    public TranslateAPI(String langFrom, String langTo, String text){
        this.langFrom=langFrom;
        this.langTo=langTo;
        this.word=text;

        Async async = new Async();
        async.execute();
    }


    class Async extends AsyncTask<String,String,String>{
        @Override
        protected String doInBackground(String... strings) {
            try {
                url = "https://translate.googleapis.com/translate_a/single?"+"client=gtx&"+"sl="+
                        langFrom +"&tl=" + langTo +"&dt=t&q=" + URLEncoder.encode(word, "UTF-8");
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestProperty("User-Agent", "Mozilla/5.0");
                BufferedReader in = new BufferedReader(     new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null)
                {    response.append(inputLine);   }
                in.close();
                resp = response.toString();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            String temp = "";

            if(resp==null){listener.onFailure("Network Error");}else {
            try {
                JSONArray main = new JSONArray(resp);
                JSONArray total = (JSONArray) main.get(0);
                for (int i = 0; i < total.length(); i++) {
                    JSONArray currentLine = (JSONArray) total.get(i);
                    temp = temp + currentLine.get(0).toString();
                }
                Log.d(TAG, "onPostExecute: "+temp);

                if(temp.length()>2)
                {
                    listener.onSuccess(temp);
                }else {listener.onFailure("Invalid Input String");}
            } catch (JSONException e) {
                listener.onFailure(e.getLocalizedMessage());
                e.printStackTrace();
            }}
            super.onPostExecute(s);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
        }
    }

    private TranslateListener listener;

    public void setTranslateListener(TranslateListener listener)
    {
        this.listener=listener;
    }

    public interface TranslateListener
    {
        public void onSuccess(String translatedText);

        public void onFailure(String ErrorText);
    }

}


