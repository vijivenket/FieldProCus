package com.capricot.fieldprocustomer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MyProfile extends AppCompatActivity {

    private TextView user_name;
    private TextView mail_id;
    private TextView mobile_number;
    private TextView location;
    private TextView oldpasswordshow;
    private TextView newpasswordshow;
    private TextView confirmpasswordshow;
    private TextView oldpasswordhide;
    private TextView newpasswordhide;
    private TextView confirmpasswordhide;
    private String username;
    private String mail;
    private String mobile;
    private String loc;
    private ImageView logout_profile;
    private RelativeLayout newpwdlayout;
    private RelativeLayout form_box;
    private Button changepasswordbtn;
    private Button updatepasswordbtn;
    private EditText oldpassswordedittxt;
    private EditText newpassswordedittxt;
    private EditText confirmpassswordedittxt;
    private final String KEY_EMAIL = "email";
    private final String KEY_OLDPASSWORD = "oldpass";
    private final String KEY_NEWPASSWORD = "newpass";

    private SharedPreferences.Editor editor;
    private SharedPreferences app_preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().hide();
        setContentView(R.layout.activity_my_profile);


        app_preferences = PreferenceManager.getDefaultSharedPreferences(MyProfile.this);

        user_name = (TextView) findViewById(R.id.user_name);
        mail_id = (TextView) findViewById(R.id.mail_id);
        mobile_number = (TextView) findViewById(R.id.mobile_number);
        location = (TextView) findViewById(R.id.location);
        form_box = (RelativeLayout) findViewById(R.id.form_box);
        newpwdlayout = (RelativeLayout) findViewById(R.id.newpwdlayout);
        changepasswordbtn = (Button) findViewById(R.id.changepasswordbtn);
        updatepasswordbtn = (Button) findViewById(R.id.updatepasswordbtn);
        oldpassswordedittxt = (EditText) findViewById(R.id.oldpassswordedittxt);
        newpassswordedittxt = (EditText) findViewById(R.id.newpasswordedittxt);
        confirmpassswordedittxt = (EditText) findViewById(R.id.confirmpasswordedittext);
        oldpasswordshow = (TextView) findViewById(R.id.oldpasswordshow);
        newpasswordshow = (TextView) findViewById(R.id.newpasswordshow);
        confirmpasswordshow = (TextView) findViewById(R.id.confirmpasswordshow);
        oldpasswordhide = (TextView) findViewById(R.id.oldpasswordhide);
        newpasswordhide = (TextView) findViewById(R.id.newpasswordhide);
        confirmpasswordhide = (TextView) findViewById(R.id.confirmpasswordhide);
        logout_profile = (ImageView) findViewById(R.id.logout_profile);

        oldpassswordedittxt.setFilters(new InputFilter[]{ignoreFirstWhiteSpace()});
        newpassswordedittxt.setFilters(new InputFilter[]{ignoreFirstWhiteSpace()});
        confirmpassswordedittxt.setFilters(new InputFilter[]{ignoreFirstWhiteSpace()});

        username = Pref_storage.getDetail(MyProfile.this, "CustName");
        mail = Pref_storage.getDetail(MyProfile.this, "CustMail");
        mobile = Pref_storage.getDetail(MyProfile.this, "Mobile");
        loc = Pref_storage.getDetail(MyProfile.this, "town");
        Log.e("username", "username------>" + username);
        Log.e("username", "username------>" + mail);
        Log.e("username", "username------>" + mobile);

        user_name.setText(username);
        mail_id.setText(mail);
        mobile_number.setText(mobile);
        location.setText(loc);


        changepasswordbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                form_box.setVisibility(View.GONE);
                newpwdlayout.setVisibility(View.VISIBLE);
            }
        });

        oldpassswordedittxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (oldpassswordedittxt.getText().toString().length() == 0) {
                    oldpasswordshow.setVisibility(View.INVISIBLE);
                } else {
                    oldpasswordshow.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        newpassswordedittxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (newpassswordedittxt.getText().toString().length() == 0) {
                    newpasswordshow.setVisibility(View.INVISIBLE);
                } else {
                    newpasswordshow.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        confirmpassswordedittxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (confirmpassswordedittxt.getText().toString().length() == 0) {
                    confirmpasswordshow.setVisibility(View.INVISIBLE);
                } else {
                    confirmpasswordshow.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        oldpasswordshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldpassswordedittxt.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                oldpassswordedittxt.setSelection(oldpassswordedittxt.getText().toString().length());
                oldpasswordshow.setVisibility(View.GONE);
                oldpasswordhide.setVisibility(View.VISIBLE);
            }
        });

        oldpasswordhide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldpassswordedittxt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                oldpassswordedittxt.setSelection(oldpassswordedittxt.getText().toString().length());
                oldpasswordhide.setVisibility(View.GONE);
                oldpasswordshow.setVisibility(View.VISIBLE);
            }
        });

        newpasswordshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newpassswordedittxt.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                newpassswordedittxt.setSelection(newpassswordedittxt.getText().toString().length());
                newpasswordshow.setVisibility(View.GONE);
                newpasswordhide.setVisibility(View.VISIBLE);
            }
        });

        newpasswordhide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newpassswordedittxt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                newpassswordedittxt.setSelection(newpassswordedittxt.getText().toString().length());
                newpasswordshow.setVisibility(View.VISIBLE);
                newpasswordhide.setVisibility(View.GONE);
            }
        });

        confirmpasswordshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmpassswordedittxt.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                confirmpassswordedittxt.setSelection(confirmpassswordedittxt.getText().toString().length());
                confirmpasswordshow.setVisibility(View.GONE);
                confirmpasswordhide.setVisibility(View.VISIBLE);
            }
        });

        confirmpasswordhide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmpassswordedittxt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                confirmpassswordedittxt.setSelection(confirmpassswordedittxt.getText().toString().length());
                confirmpasswordshow.setVisibility(View.VISIBLE);
                confirmpasswordhide.setVisibility(View.GONE);
            }
        });

        logout_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(MyProfile.this).setIcon(R.drawable.exit).setTitle("Logout")
                        .setMessage("Are you sure you want to Logout?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                editor = app_preferences.edit();
                                editor.putBoolean("isFirstTime", true);
                                editor.apply();

                                Intent intent = new Intent(getApplicationContext(), Login.class);
                                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();

                            }
                        }).setNegativeButton("No", null).show();
            }
        });

        updatepasswordbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String customeremail = mail_id.getText().toString();
                String oldpasswordtxt = oldpassswordedittxt.getText().toString();
                String newpasswordtxt = newpassswordedittxt.getText().toString();
                String confirmpasswordtxt = confirmpassswordedittxt.getText().toString();

                if (!isNetworkAvailable()) {
                    EnableNetwork();
                } else if (validatechangepwd(oldpasswordtxt, newpasswordtxt, confirmpasswordtxt)) {

                    customerChangePasswordSuccess(customeremail, oldpasswordtxt, newpasswordtxt);

                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MyProfile.this, Home.class);
        intent.putExtra("","");
        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }


    //Network Connection
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    //Enable Network
    private void EnableNetwork() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MyProfile.this);
        builder.setCancelable(false);
        builder.setTitle(R.string.networkerror);
        builder.setMessage(R.string.enablenet);
        builder.setInverseBackgroundForced(true);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                startActivity(intent);
            }
        });
        android.app.AlertDialog alert = builder.create();
        alert.show();
    }

    private boolean validatechangepwd(String username, String old_password, String new_password) {
        boolean valid = true;

        String old_pwd = oldpassswordedittxt.getText().toString();
        String new_pwd = newpassswordedittxt.getText().toString();
        String confirm_pwd = confirmpassswordedittxt.getText().toString();

        if (old_pwd.isEmpty()) {
            oldpassswordedittxt.setError(getText(R.string.oldpwderror));
            valid = false;
        } else {
            oldpassswordedittxt.setError(null);
        }

        if (new_pwd.isEmpty()) {
            newpassswordedittxt.setError(getText(R.string.newpwderror));
            valid = false;
        } else {
            newpassswordedittxt.setError(null);
        }

        if (confirm_pwd.isEmpty()) {
            confirmpassswordedittxt.setError(getText(R.string.confirmpwdvalid));
            valid = false;
        } else {
            confirmpassswordedittxt.setError(null);
        }

        if (!Objects.equals(new_pwd, confirm_pwd)) {
            confirmpassswordedittxt.setError(getText(R.string.matchvalid));
            valid = false;
        }
        return valid;
    }

    private void customerChangePasswordSuccess(final String customeremail, final String oldpasswordtxt, final String newpasswordtxt) {
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, API.ChangePassword, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    pDialog.dismiss();

                    JSONObject jobj = new JSONObject(response);
                    //Toast.makeText(MyProfile.this, "" + jobj.toString(), Toast.LENGTH_SHORT).show();
                    Log.e("LoginError", "LoginError---------> " + jobj.toString());
                    int st = jobj.getInt("status");

                    try {
                        if (st == 1) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(MyProfile.this);
                            builder.setTitle("Success");
                            builder.setMessage("Password changed successfully");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                    Intent intent=new Intent(MyProfile.this,Login.class);
                                    startActivity(intent);
                                }
                            });
                            AlertDialog alt = builder.create();
                            alt.show();

                        }
                        if (st == 0) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(MyProfile.this);
                            builder.setTitle("Failure");
                            builder.setMessage("Old password wrong");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();

                                }
                            });
                            AlertDialog alt = builder.create();
                            alt.show();

                        }
                    } catch (Exception e) {
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


                AlertDialog.Builder builder = new AlertDialog.Builder(MyProfile.this);
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
                map.put(KEY_EMAIL, customeremail);
                map.put(KEY_OLDPASSWORD, oldpasswordtxt);
                map.put(KEY_NEWPASSWORD, newpasswordtxt);
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
}