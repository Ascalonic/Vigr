package com.ascalonic.vigr;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by HP on 12-11-2017.
 */

public class SearchServerInterface extends AsyncTask<String, Void, String> {


    String resp;
    private Context cont;
    private int myflag;

    public AsyncResponse del;

    public SearchServerInterface(Context context,AsyncResponse mydel,int flag) {
        this.cont = context;
        this.del=mydel;
        resp=new String("");
        this.myflag=flag;
    }


    protected void onPreExecute(){
    }

    @Override
    protected String doInBackground(String... arg0) {

        String msg="";

        if(myflag==0) {
            try {

                String link = MainActivity.BASE_DOMAIN + "fetchspecs.php";

                //Log.d(data,"CONN_TEST");

                URL url = new URL(link);

                HttpURLConnection urlConnection =
                        (HttpURLConnection) url.openConnection();

                ///////////////////
                urlConnection.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());
                wr.flush();
                /////////////////////

                InputStream in = urlConnection.getInputStream();

                BufferedReader br = new BufferedReader(new InputStreamReader(in));

                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                    break;
                }
                msg = sb.toString();


            } catch (Exception e) {

                msg = new String("Exception: " + e.getMessage());
            }
        }
        else if(myflag==1) {
            try {

                String link = MainActivity.BASE_DOMAIN + "fetchplaces.php";

                //Log.d(data,"CONN_TEST");

                URL url = new URL(link);

                HttpURLConnection urlConnection =
                        (HttpURLConnection) url.openConnection();

                ///////////////////
                urlConnection.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());
                wr.flush();
                /////////////////////

                InputStream in = urlConnection.getInputStream();

                BufferedReader br = new BufferedReader(new InputStreamReader(in));

                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                    break;
                }
                msg = sb.toString();


            } catch (Exception e) {

                msg = new String("Exception: " + e.getMessage());
            }
        }

        return msg;

    }

    @Override
    protected void onPostExecute(String result){
        del.processFinish(result);
    }
}