package com.ascalonic.vigr;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    EditText txtPhone;
    Button btnConfirm;

    TextView labMain;

    ProgressDialog progressDialog;

    public static final String OTP_REGEX = "[0-9]{5}";

    public static final String BASE_DOMAIN = "http://mitscse.acm.org/vigr/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Vigr - New Account");

        txtPhone=(EditText)findViewById(R.id.editPhone);
        btnConfirm=(Button) findViewById(R.id.cmdConfirm);
        labMain=(TextView) findViewById(R.id.textMainLabel);


    }

    private void showPhoneNumAuth()
    {

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Please wait...");
        progressDialog.setMessage("Sending your phone number");
        progressDialog.show();
    }

    private void showOTPConfProgress()
    {

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Please wait...");
        progressDialog.setMessage("Verifying OTP...");
        progressDialog.show();
    }

    private void showAuthProgress()
    {

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Please wait...");
        progressDialog.setMessage("We are getting ready...");
        progressDialog.show();
    }

    private void showPhoneNumAuthFailure()
    {
        android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle("Error Occured");

        // Setting Dialog Message
        alertDialog.setMessage("There was an error sending your phone number to the server");

        // On pressing Settings button
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                //Do Nothing
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    private void showOTPConfirmFailure(String msg)
    {
        android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle("Confirmation Error");

        // Setting Dialog Message
        alertDialog.setMessage(msg);

        // On pressing Settings button
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                //Do Nothing
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    @Override
    public void onStart()
    {
        super.onStart();

        AccessManager am=new AccessManager(this,getString(R.string.preference_file));
        String token = am.getAccessToken();
        String phone = am.getPhone();

        Log.d("AUTH_TOKEN","Token:" + token);
        Log.d("AUTH_TOKEN","Phone:" + phone);

        showAuthProgress();

        AuthServerInterface LSI=new AuthServerInterface(getBaseContext(), new AsyncResponse() {
            @Override
            public void processFinish(String output) {

                Log.d("AUTH_TOKEN","out:" + output);

                progressDialog.dismiss();

                try {
                    JSONObject res = (new JSONObject(output));
                    Boolean success = res.getBoolean("success");

                    if(success)
                    {
                        Intent i=new Intent(getBaseContext(),VigrConsole.class);
                        startActivity(i);

                        finish();
                    }
                    else
                    {
                        //Do nothing. Proceed with OTP confirmation
                    }

                }
                catch(JSONException ex)
                { }
            }

        },2);

        LSI.execute(phone, token);
    }

    public void confirmPhoneNum(View v)
    {
        showPhoneNumAuth();

        AuthServerInterface ASI=new AuthServerInterface(getBaseContext(), new AsyncResponse() {
            @Override
            public void processFinish(String output) {

                progressDialog.dismiss();


                try
                {
                    JSONObject res=(new JSONObject(output));
                    Boolean success  =res.getBoolean("success");

                    if(success)
                    {
                        //Wait for sms
                        btnConfirm.setText("Change");
                        txtPhone.setEnabled(false);
                        labMain.setText("Waiting for OTP...");


                        SmsReceiver.bindListener(new SmsListener() {
                            @Override
                            public void messageReceived(String messageText) {

                                //From the received text string you may do string operations to get the required OTP
                                //It depends on your SMS format
                                Toast.makeText(MainActivity.this,"Message: "+messageText,Toast.LENGTH_LONG).show();

                                // If your OTP is six digits number, you may use the below code

                                Pattern pattern = Pattern.compile(OTP_REGEX);
                                Matcher matcher = pattern.matcher(messageText);
                                String otp="";
                                while (matcher.find())
                                {
                                    otp = matcher.group();
                                }

                                Toast.makeText(MainActivity.this,"OTP: "+ otp ,Toast.LENGTH_LONG).show();

                                final String phone_num = txtPhone.getText().toString();

                                labMain.setText("OTP Recieved. Confirming...");
                                txtPhone.setText(otp);
                                btnConfirm.setText("Wait...");
                                btnConfirm.setEnabled(false);

                                showOTPConfProgress();

                                AuthServerInterface OTPSI=new AuthServerInterface(getBaseContext(), new AsyncResponse() {
                                    @Override
                                    public void processFinish(String output) {
                                        progressDialog.dismiss();
                                        Log.d("SMSTEST",output);

                                        try {
                                            JSONObject res = (new JSONObject(output));
                                            Boolean success = res.getBoolean("success");
                                            String message = res.getString("message");

                                            if(success)
                                            {

                                                AccessManager am=new AccessManager(MainActivity.this,getString(R.string.preference_file));
                                                am.setAccessToken(message);
                                                am.setPhone(phone_num);

                                                Intent i=new Intent(getBaseContext(),VigrConsole.class);
                                                startActivity(i);

                                                finish();
                                            }
                                            else
                                            {
                                                showOTPConfirmFailure(message);
                                            }

                                        }
                                        catch(JSONException ex)
                                        { }
                                    }

                                },1);

                                OTPSI.execute(phone_num,otp);

                            }
                        });
                    }
                    else
                    {
                        showPhoneNumAuthFailure();
                    }

                }
                catch(JSONException ex)
                {

                }

            }
        },0);

        ASI.execute(txtPhone.getText().toString());
    }

    public void testDatabase(View v)
    {
        AccessManager am=new AccessManager(this,getString(R.string.preference_file));
        am.setAccessToken("new access token");
        Log.d("AUTH_TOKEN",am.getAccessToken());
    }
}
