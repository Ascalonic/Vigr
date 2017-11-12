package com.ascalonic.vigr;

/**
 * Created by HP on 11-11-2017.
 */


import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


interface AsyncResponse {
    void processFinish(String output);
}

public class AuthServerInterface extends AsyncTask<String, Void, String> {


    String resp;
    private Context cont;
    private int myflag;

    public AsyncResponse del;

    public AuthServerInterface(Context context,AsyncResponse mydel,int flag) {
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
                String phone_num = (String) arg0[0];

                String link = MainActivity.BASE_DOMAIN + "phauth.php";

                String data = URLEncoder.encode("phno", "UTF-8") + "=" +
                        URLEncoder.encode(phone_num, "UTF-8");

                //Log.d(data,"CONN_TEST");

                URL url = new URL(link);

                HttpURLConnection urlConnection =
                        (HttpURLConnection) url.openConnection();

                ///////////////////
                urlConnection.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());

                wr.write(data);
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
                String phone_num = (String) arg0[0];
                String ccode = (String) arg0[1];

                String link = MainActivity.BASE_DOMAIN + "phverif.php";

                String data = URLEncoder.encode("phno", "UTF-8") + "=" +
                        URLEncoder.encode(phone_num, "UTF-8");
                data += "&" + URLEncoder.encode("ccode", "UTF-8") + "=" +
                        URLEncoder.encode(ccode, "UTF-8");

                //Log.d(data,"CONN_TEST");

                URL url = new URL(link);

                HttpURLConnection urlConnection =
                        (HttpURLConnection) url.openConnection();

                ///////////////////
                urlConnection.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());

                wr.write(data);
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
        else if(myflag==2) {
            try {
                String phone_num = (String) arg0[0];
                String token = (String) arg0[1];

                String link = MainActivity.BASE_DOMAIN + "tokauth.php";

                String data = URLEncoder.encode("phno", "UTF-8") + "=" +
                        URLEncoder.encode(phone_num, "UTF-8");
                data += "&" + URLEncoder.encode("token", "UTF-8") + "=" +
                        URLEncoder.encode(token, "UTF-8");

                //Log.d(data,"CONN_TEST");

                URL url = new URL(link);

                HttpURLConnection urlConnection =
                        (HttpURLConnection) url.openConnection();

                ///////////////////
                urlConnection.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());

                wr.write(data);
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