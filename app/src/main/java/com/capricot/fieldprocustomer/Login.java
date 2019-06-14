package com.capricot.fieldprocustomer;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.capricot.fieldprocustomer.Firebase.Constants;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {


    private Button login;
    public static int l = 100;
    private TextView signUp;
    private TextView forgot_password;
    private TextView currentyear;
    private TextInputLayout email_layout;
    private TextInputLayout password_layout;
    private TextInputEditText email;
    private TextInputEditText pwd;
    private final String KEY_USERNAME = "username";
    private final String KEY_PASSWORD = "password";
    private final String KEY_REGID = "reg_id";
    private final String KEY_DEVICETOKEN = "device_token";
     private String CustomerID;
    private String company_id;
    private String CustomerName;
    private String CustomerMail;
    private String MobileNumber;
    private String land;
    private String alternatenumber;
    private String Doorno;
    private String Street;
    private String Town;
    public String Landmark;
    private String City,fcmkey;
    private String State;
    private String country;
    private String pincode;
    private String region;
    private RelativeLayout login_main_layout;
    private String msg;
    private static SharedPreferences settings;
    private SharedPreferences.Editor editor;
    private SharedPreferences app_preferences;
    public NotificationManager mNotificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel mChannel = new NotificationChannel(Constants.CHANNEL_ID, Constants.CHANNEL_NAME, importance);
            mChannel.setDescription(Constants.CHANNEL_DESCRIPTION);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);

            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mNotificationManager.createNotificationChannel(mChannel);


            NotificationCompat.Builder builder = new NotificationCompat.Builder(Login.this);
            builder.build();
        }

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                // Do whatever you want with your token now
                // i.e. store it on SharedPreferences or DB
                // or directly send it to server
                fcmkey = instanceIdResult.getToken();
                Log.w("fcmkey", "refreshedToken----->" + fcmkey);
                Log.i("fcmkey", "refreshedToken----->" + fcmkey);
                Log.e("fcmkey", "refreshedToken----->" + fcmkey);
               // Toast.makeText(Login.this, "" + fcmkey, Toast.LENGTH_SHORT).show();
            }
        });


        //initialize UI Components
        initComponents();


        //set Trim Function
        email.setFilters(new InputFilter[]{ignoreFirstWhiteSpace()});
        pwd.setFilters(new InputFilter[]{ignoreFirstWhiteSpace()});

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Registration.class);
                startActivity(intent);
            }
        });

        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_forgotpass = new Intent(Login.this, ForgotPassword.class);
                startActivity(intent_forgotpass);
            }
        });


        //First time lauch app in mobile...................................
        final String PREFS_NAME = "MyPreFile";
        settings = getSharedPreferences(PREFS_NAME, 0);


        if (settings.getBoolean("my_first_time", true)) {
            //the app is being launched for first time, do something
            Log.d("Comments", "First time");
            if (Build.VERSION.SDK_INT >= 23) {
                if (ContextCompat.checkSelfPermission(Login.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(Login.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(Login.this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Login.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.CALL_PHONE},
                            1);
                }
            }


            // first time task
            //**//*  File f = new File(Environment.getExternalStorageDirectory(), "CCD1.xml");*//**//

            /*  enterCCD();*/

            // record the fact that the app has been started at least once
            settings.edit().putBoolean("my_first_time", false).apply();
        }

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                email_layout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        pwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                password_layout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = email.getText().toString();
                String passwd = pwd.getText().toString();

                if (!checkInternetConnection()) {
                    EnableNetwork();
                } else if (validateLogin()) {

                    //call login api
                    loginusingVolley(mail, passwd);
                }
            }
        });
    }

    private void initComponents() {

        app_preferences = PreferenceManager.getDefaultSharedPreferences(Login.this);

        email_layout = (TextInputLayout) findViewById(R.id.emaillayout);
        password_layout = (TextInputLayout) findViewById(R.id.passwordlayout);


        email = (TextInputEditText) findViewById(R.id.editmail_id);
        pwd = (TextInputEditText) findViewById(R.id.editpwd_id);
        login_main_layout = (RelativeLayout) findViewById(R.id.login_main_layout);

        login = (Button) findViewById(R.id.login_button);
        signUp = (TextView) findViewById(R.id.register);
        forgot_password = (TextView) findViewById(R.id.forgot_password);
        currentyear = (TextView) findViewById(R.id.currentyear);

        //to get current year
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);

        currentyear.setText("Copyright © Kaspon Techworks Pvt Ltd " + year);

    }

    private static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    private boolean validateLogin() {
        boolean valid = true;

        String emailtxt = email.getText().toString();
        String pwdtxte = pwd.getText().toString();

        if (emailtxt.isEmpty()) {
            email_layout.setError("Please enter email address");
            valid = false;
        } else {
            email_layout.setError(null);
        }

        if (!isValidEmail(emailtxt)) {
            email_layout.setError("Please enter valid email address");
            valid = false;
        } else {
            email_layout.setError(null);
        }

        if (pwdtxte.isEmpty()) {
            password_layout.setError("Please enter password");
            valid = false;
        } else {
            password_layout.setError(null);
        }
        return valid;
    }


    private void loginusingVolley(final String mail, final String passwd) {


        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, API.LOGIN_URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        pDialog.dismiss();

                        Log.e("LoginError", "LoginError---------> " + response);
                        try {
                            JSONObject jobj = new JSONObject(response);
                            Log.e("LoginError", "LoginError---------> " + jobj.toString());
                            int st = jobj.getInt("status");


                            try {
                                if (st == 1) {


                                    JSONObject j2obj = jobj.getJSONObject("result");

                                    msg = j2obj.getString("msg");
                                    CustomerID = j2obj.getString("cust_id");
                                    CustomerName = j2obj.getString("cust_name");
                                    CustomerMail = j2obj.getString("emailid");
                                    MobileNumber = j2obj.getString("mobile");
                                    company_id = j2obj.getString("company_id");
                                    Doorno = j2obj.getString("door_no");
                                    Street = j2obj.getString("street");
                                    Town = j2obj.getString("town");
                                    land = j2obj.getString("landmark");
                                    City = j2obj.getString("city");
                                    State = j2obj.getString("state");
                                    country = j2obj.getString("country");
                                    pincode = j2obj.getString("pincode");
                                    alternatenumber = j2obj.getString("alternate_number");
                                    region = j2obj.getString("region");


                                    Pref_storage.setDetail(Login.this, "message", msg);
                                    Pref_storage.setDetail(Login.this, "CustID", CustomerID);
                                    Pref_storage.setDetail(Login.this, "CustName", CustomerName);
                                    Pref_storage.setDetail(Login.this, "CustMail", CustomerMail);
                                    Pref_storage.setDetail(Login.this, "Mobile", MobileNumber);
                                    Pref_storage.setDetail(Login.this, "company_id", company_id);
                                    Pref_storage.setDetail(Login.this, "door_no", Doorno);
                                    Pref_storage.setDetail(Login.this, "street", Street);
                                    Pref_storage.setDetail(Login.this, "town", Town);
                                    Pref_storage.setDetail(Login.this, "city", City);
                                    Pref_storage.setDetail(Login.this, "state", State);
                                    Pref_storage.setDetail(Login.this, "country", country);
                                    Pref_storage.setDetail(Login.this, "pincode", pincode);
                                    Pref_storage.setDetail(Login.this, "alternatenumber", alternatenumber);
                                    Pref_storage.setDetail(Login.this, "region", region);

                                    Intent inte = new Intent(getApplicationContext(), Home.class);
                                    editor = app_preferences.edit();
                                    editor.putBoolean("isFirstTime", false);
                                    editor.apply();
                                    startActivity(inte);


                                }
                                if (st == 0) {
                                    Snackbar snackbar = Snackbar.make(login_main_layout, "Please provide correct details!!!", Snackbar.LENGTH_LONG);
                                    snackbar.setActionTextColor(Color.RED);
                                    View view = snackbar.getView();
                                    TextView textview = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
                                    textview.setTextColor(Color.WHITE);
                                    snackbar.show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();


                        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
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
                map.put(KEY_USERNAME, mail);
                map.put(KEY_PASSWORD, passwd);
                map.put(KEY_REGID, "");
                map.put(KEY_DEVICETOKEN, fcmkey);

                Log.e("first", "--->" + map);


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

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setIcon(R.drawable.exit).setTitle("Exit")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();

                    }
                }).setNegativeButton("No", null).show();
    }

    private InputFilter ignoreFirstWhiteSpace() {
        return new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {

                for (int i = start; i < end; i++) {
                    if (Character.isWhitespace(source.charAt(i))) {
                        if (dstart == 0)
                            return "";
                    }
                }
                return null;
            }
        };
    }

    //Network Connection
    @SuppressLint("MissingPermission")
    private boolean checkInternetConnection() {
        ConnectivityManager connec = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        assert connec != null;
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||

                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {


            return true;
        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {

            return false;
        }
        return false;
    }

    //Enable Network

    private void EnableNetwork() {
        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle(R.string.networkerror);
        builder.setMessage(R.string.enablenet);
        builder.setInverseBackgroundForced(true);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        android.support.v7.app.AlertDialog alert = builder.create();
        alert.show();
    }

}