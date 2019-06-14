package com.capricot.fieldprocustomer;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ForgotPassword extends AppCompatActivity {

    private EditText regmailid_txt;
    private Button resetpwd_btn;
    private TextView backtologin_txt;
    private TextView headingtext;
    private final String KEY_EMAIL = "email";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpwd);

        regmailid_txt=(EditText)findViewById(R.id.regmailid_txt);
        resetpwd_btn=(Button) findViewById(R.id.resetpwd_btn);
        backtologin_txt=(TextView) findViewById(R.id.backtologin_txt);
        headingtext=(TextView) findViewById(R.id.headingtext);

        headingtext.setText("Forgot Password");

        backtologin_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ForgotPassword.this,Login.class);
                startActivity(intent);
            }
        });

        resetpwd_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = regmailid_txt.getText().toString();

                if (mail.isEmpty()) {
                    regmailid_txt.setError("Please enter email address");
                }
                else if (isValidEmail(mail)) {

                    forgotPasswordSuccessCustomer(mail);

                } else {

                    regmailid_txt.setError("Please enter valid email address");
                }

            }
        });

    }

    private void forgotPasswordSuccessCustomer(final String registeremail) {
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, API.ForgotPassword, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    pDialog.dismiss();

                    JSONObject jobj = new JSONObject(response);
//                    Toast.makeText(ForgotPassword.this, "" + jobj.toString(), Toast.LENGTH_SHORT).show();
                    Log.e("LoginError","LoginError---------> "+ jobj.toString());
                    int st = jobj.getInt("status");

                    try {
                        if (st == 1){
                            final AlertDialog.Builder builder1 = new AlertDialog.Builder(ForgotPassword.this);
                            builder1.setTitle("Success");
                            builder1.setMessage("Password sent to your email address");
                            builder1.setCancelable(true);

                            builder1.setPositiveButton(
                                    "ok",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            Intent i = new Intent(ForgotPassword.this, Login.class);
                                            startActivity(i);
                                            finish();
                                        }
                                    });


                            AlertDialog alert11 = builder1.create();
                            alert11.show();

                        }

                        if (st == 0) {
                            final AlertDialog.Builder builder1 = new AlertDialog.Builder(ForgotPassword.this);
                            builder1.setTitle("Failure");
                            builder1.setMessage("No email address found");
                            builder1.setCancelable(true);

                            builder1.setPositiveButton(
                                    "ok",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.dismiss();
                                            regmailid_txt.setText("");
                                        }
                                    });

                            AlertDialog alert11 = builder1.create();
                            alert11.show();
                        }

                    } catch (Exception e){
                        e.printStackTrace();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();


                AlertDialog.Builder builder = new AlertDialog.Builder(ForgotPassword.this);
                builder.setTitle("No internet connection");
                builder.setMessage("You need to have mobile data or wifi to access this application. Press OK to enable");
                builder.setIcon(R.drawable.wifi);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_SETTINGS);
                        startActivity(intent);
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                builder.show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put(KEY_EMAIL, registeremail);
                return map;
            }
        };
        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }


}
