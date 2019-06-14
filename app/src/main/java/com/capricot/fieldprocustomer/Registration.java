package com.capricot.fieldprocustomer;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity implements Spinner.OnItemSelectedListener ,View.OnClickListener {

    private EditText name;
    private EditText email;
    private EditText contact_num;
    private EditText alt_contact_num;
    private EditText model_num;
    private EditText serial_num;
    private EditText door_no;
    private EditText street;
    private EditText town;
    private EditText landmark;
    private EditText city;
    private EditText state;
    private EditText country;
    private EditText pincode;
    private EditText region_input;
    private EditText contractduration;
    private Spinner company, product, category,spinner_typeofcontract;
    private ArrayList<String> companyNameList, companyIDList, productIDList, productNameList, categoryNameList, categoryIDList;
    private final String KEY_CompanyID = "company_id";
    private final String KEY_ProductID = "product_id";
    private final String KEY_CategoryID = "cat_id";
    private Button register;
    private TextView headingtext;
    private TextView startdate_text;
    private static String companyID;
    private static String productID;
    public static String categoryID;
    private static String cate_ID;
    private static String username;
    private static String email_id;
    private static String contact_Num;
    private static String alt_num;
    private static String model_Num;
    private static String serial_Num;
    private static String door;
    private static String streetValue;
    private static String townValue;
    private static String landValue;
    private static String cityValue;
    private static String stateValue;
    private static String countryValue;
    private static String pincodeValue;
    private static String regionvalue;
    private static String startdate;
    private static String contractdur;
    private static String typeofcontract;
    private final ArrayList<String> contractTypeName=new ArrayList<>();
    private final ArrayList<String> contractTypeIDList=new ArrayList<>();
    private String amc_type;
    private String contractID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        companyNameList = new ArrayList<>();
        companyIDList = new ArrayList<>();

        productIDList = new ArrayList<>();
        productNameList = new ArrayList<>();

        categoryIDList = new ArrayList<>();
        categoryNameList = new ArrayList<>();


        name = (EditText) findViewById(R.id.full_name_input);
        email = (EditText) findViewById(R.id.email_input);
        contact_num = (EditText) findViewById(R.id.contact_input);
        alt_contact_num = (EditText) findViewById(R.id.atl_contact_input);
        model_num = (EditText) findViewById(R.id.model_number_input);
        serial_num = (EditText) findViewById(R.id.serial_number_input);
        door_no = (EditText) findViewById(R.id.door_no_input);
        street = (EditText) findViewById(R.id.street_input);
        town = (EditText) findViewById(R.id.town_input);
        landmark = (EditText) findViewById(R.id.landmark_input);
        city = (EditText) findViewById(R.id.city_input);
        state = (EditText) findViewById(R.id.state_input);
        region_input = (EditText) findViewById(R.id.region_input);
        country = (EditText) findViewById(R.id.country_input);
        pincode = (EditText) findViewById(R.id.pincode_input);
        register = (Button) findViewById(R.id.register);
        company = (Spinner) findViewById(R.id.company_spinner);
        product = (Spinner) findViewById(R.id.product_spinner);
        category = (Spinner) findViewById(R.id.category_spinner);
        headingtext = (TextView) findViewById(R.id.headingtext);
        contractduration = (EditText) findViewById(R.id.contractduration);
        startdate_text = (TextView) findViewById(R.id.startdate_text);
        spinner_typeofcontract = (Spinner) findViewById(R.id.spinner_typeofcontract);

        headingtext.setText("Sign Up");


        startdate_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(Registration.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                String frmdate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;

                                startdate_text.setText(frmdate);
                                startdate_text.setError(null);

                                Log.e("frmdate", "frmdate" + frmdate);


                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validate()) {
                    Register();
                }

            }
        });

        company.setOnItemSelectedListener(this);
        product.setOnItemSelectedListener(this);
        category.setOnItemSelectedListener(this);

        getCompany();





    }
    private void getContractType() {

        final ProgressDialog pDialog = new ProgressDialog(getApplicationContext());
        pDialog.setMessage("Loading...");
//        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, API.ContractTyperegistration,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

//                            pDialog.dismiss();

//                            Toast.makeText(getActivity(), "cattype"+response, Toast.LENGTH_SHORT).show();


                            JSONObject jobj = new JSONObject(response);
                            int status = jobj.getInt("status");

                            if (status == 1) {

                                JSONArray jaryproduct = jobj.getJSONArray("result");
                                contractTypeName.clear();

                                contractTypeName.add("Select Contract type");
                                contractTypeIDList.add("Select Contract type");

                                for (int i = 0; i < jaryproduct.length(); i++) {
                                    JSONObject restatusobj = jaryproduct.getJSONObject(i);
                                    amc_type = restatusobj.getString("amc_type");
                                    contractID = restatusobj.getString("id");
                                    contractTypeIDList.add(contractID);
                                    contractTypeName.add(amc_type);
                                }
                                if (contractTypeName.isEmpty()) {
                                    contractTypeName.add("On-Demand");
                                }

                                if (getApplicationContext() != null) {
                                    ArrayAdapter<String> adp2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, contractTypeName);
                                    adp2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spinner_typeofcontract.setAdapter(adp2);
                                }
                                spinner_typeofcontract.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                       if(spinner_typeofcontract.getSelectedItem().toString().equals("Select Contract type"))
                                       {

                                       }else {
                                           typeofcontract = contractTypeName.get(position);
//                                            Toast.makeText(Registration.this, ""+typeofcontract, Toast.LENGTH_SHORT).show();

                                       }
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });

                            } else {

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext(), error + "Something went wrong please try again later ! ", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                 map.put("comp_id", companyID);

                Log.e("nowtest","contracttype--->"+map);

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
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


        Spinner spinner = (Spinner) adapterView;

        switch (spinner.getId()) {

            /*case R.id.company_spinner:
                companyID = companyIDList.get(i);
                getProduct(companyID);
                getContractType();
                break;
            case R.id.product_spinner:
                productID = productIDList.get(i);
                getCategory(productID, companyID);
                break;
            case R.id.category_spinner:
                //    categoryID = categoryIDList.get(i);
                break;*/

        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    /*-----------------------------------------Company API-------------------------------------------------------------------------------------------------------------*/
    private void getCompany() {

     /*   final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();*/

        StringRequest stringRequest = new StringRequest(Request.Method.POST, API.LoadCompany_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                          //  pDialog.dismiss();

                            JSONArray jary = new JSONArray(response);


//                            companyNameList.add("Select Company Name");
//                            companyIDList.add("Select Company Name");
                            for (int i = 0; i < jary.length(); i++) {
                                JSONObject restatusobj = jary.getJSONObject(i);
                                String companyID = restatusobj.getString("company_id");
                                String companyName = restatusobj.getString("company_name");
                                companyNameList.add(companyName);
                                companyIDList.add(companyID);
                            }

                            ArrayAdapter<String> adp1 = new ArrayAdapter<>(Registration.this, android.R.layout.simple_spinner_dropdown_item, companyNameList);
                            adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            company.setAdapter(adp1);

                            company.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

//                                    if(company.getSelectedItem().equals("Select Company Name"))
//                                    {
//
//                                       // Toast.makeText(Registration.this, "Select Company", Toast.LENGTH_SHORT).show();
//                                    }
//                                    else {
//                                        companyID = companyIDList.get(position);
//                                        //call product api
//                                        getProduct(companyID);
//                                    }

                                    companyID = companyIDList.get(position);
                                       getProduct(companyID);
                                    getContractType();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(Registration.this, error + "Something went wrong please try again later ! ", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
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




    /*-----------------------------------------Product API-------------------------------------------------------------------------------------------------------------*/


    private void getProduct(final String companyID) {

       /* final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();*/

        StringRequest sr = new StringRequest(Request.Method.POST, API.LoadProduct_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        String productName, productIDD;

                        try {

                          //  pDialog.dismiss();

                            JSONObject jobj = new JSONObject(response);
                            int status = jobj.getInt("status");

                            if (status == 1) {


//                                Toast.makeText(Registration.this, "product"+ response, Toast.LENGTH_LONG).show();
                                productNameList.clear();
                                productNameList.add("Select Product");
                                productIDList.add("Select Product");

                                JSONArray jaryproduct = jobj.getJSONArray("result");
                                for (int i = 0; i < jaryproduct.length(); i++) {
                                    JSONObject jproduct = jaryproduct.getJSONObject(i);
                                    productName = jproduct.getString("product_name");
                                    productIDD = jproduct.getString("product_id");
                                    productNameList.add(productName);
                                    productIDList.add(productIDD);

                                }

                                ArrayAdapter<String> adp2 = new ArrayAdapter<>(Registration.this, android.R.layout.simple_spinner_dropdown_item, productNameList);
                                adp2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                product.setAdapter(adp2);

                                product.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                        if(product.getSelectedItem().toString().equals("Select Product"))
                                        {
                                         //   Toast.makeText(Registration.this, "Select product", Toast.LENGTH_SHORT).show();
                                        }else
                                        {
                                            productID = productIDList.get(position);

                                            //call category api
                                            getCategory(productID, companyID);

                                        }


                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });

                            } else {

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(Registration.this, error + "Something went wrong please try again later ! ", Toast.LENGTH_SHORT).show();


                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put(KEY_CompanyID, companyID);
                return map;
            }
        };

        sr.setRetryPolicy(new RetryPolicy() {
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
        requestQueue.add(sr);

    }





    /*-----------------------------------------Category API-------------------------------------------------------------------------------------------------------------*/


    private void getCategory(final String productID, final String companyID) {

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        StringRequest sr = new StringRequest(Request.Method.POST, API.LoadCategory_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        String categoryName, categoryID;

                        try {

                            pDialog.dismiss();

                            JSONObject jobj1 = new JSONObject(response);
                            int status = jobj1.getInt("status");
                            categoryNameList.clear();
                            categoryIDList.clear();

                            categoryNameList.add("Select Category");
                            categoryIDList.add("Select Category");

                            if (status == 1) {

                                JSONArray jarycat = jobj1.getJSONArray("result");
                                for (int i = 0; i < jarycat.length(); i++) {
                                    JSONObject jcat = jarycat.getJSONObject(i);
                                    categoryName = jcat.getString("cat_name");
                                    categoryID = jcat.getString("cat_id");
                                    categoryNameList.add(categoryName);


                                    categoryIDList.add(categoryID);
                                    Log.e("cat", "--->" + categoryIDList);
                                    Log.e("cat", "--->" + categoryIDList);

                                }


                                ArrayAdapter<String> adp3 = new ArrayAdapter<>(Registration.this, android.R.layout.simple_spinner_dropdown_item, categoryNameList);
                                /*Toast.makeText(Registration.this, "category" + categoryNameList, Toast.LENGTH_LONG).show();*/
                                adp3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                category.setAdapter(adp3);

                                category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                            @Override
                                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                               // Toast.makeText(Registration.this, "" + categoryIDList.get(position), Toast.LENGTH_SHORT).show();
                                               // Toast.makeText(Registration.this, "" + parent.getSelectedItem(), Toast.LENGTH_SHORT).show();

                                                if (category.getSelectedItem().toString().equals("Select Category")) {
                                                   // Toast.makeText(Registration.this, "Select Category", Toast.LENGTH_SHORT).show();

                                                } else {
                                                    cate_ID = categoryIDList.get(position);


                                                }
                                            }

                                            @Override
                                            public void onNothingSelected(AdapterView<?> parent) {

                                            }
                                        });

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });

                            } else {

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(Registration.this, error + "Something went wrong please try again later ! ", Toast.LENGTH_SHORT).show();


                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put(KEY_CompanyID, companyID);
                map.put(KEY_ProductID, productID);
                Log.e("svsgv", "wsfe" + map);
                return map;
            }
        };

        sr.setRetryPolicy(new RetryPolicy() {
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
        requestQueue.add(sr);

    }





    /*-----------------------------------------Register API-------------------------------------------------------------------------------------------------------------*/


    private void Register() {
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, API.Register_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            pDialog.dismiss();


                            JSONObject jobj = new JSONObject(response);
                            int st = jobj.getInt("status");
                            String msg = jobj.getString("msg");

                            if (st == 1) {
                                final AlertDialog.Builder builder1 = new AlertDialog.Builder(Registration.this);
                                builder1.setTitle("Success");
                                builder1.setMessage("Registered successfully");
                                builder1.setCancelable(true);

                                builder1.setPositiveButton(
                                        "ok",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                Intent i = new Intent(Registration.this, Login.class);
                                                startActivity(i);
                                                finish();
                                            }
                                        });


                                AlertDialog alert11 = builder1.create();
                                alert11.show();
                            }
                            if (st == 0) {
                                final AlertDialog.Builder builder1 = new AlertDialog.Builder(Registration.this);
                                builder1.setTitle("Failure");
                                builder1.setMessage(msg);
                                builder1.setCancelable(true);

                                builder1.setPositiveButton(
                                        "ok",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.dismiss();
                                            }
                                        });


                                AlertDialog alert11 = builder1.create();
                                alert11.show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(Registration.this, error + "Something went wrong please try again later ! ", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put(KEY_CompanyID, companyID);
                map.put(KEY_ProductID, productID);
                map.put(KEY_CategoryID, cate_ID);
                map.put("name", username);
                map.put("emailid", email.getText().toString());
                map.put("contact_no", contact_Num);
                map.put("alt_no", alt_num);
                map.put("door_no", door);
                map.put("street", streetValue);
                map.put("region", "");
                map.put("town", townValue);
                map.put("landmark", landValue);
                map.put("city", cityValue);
                map.put("state", stateValue);
                map.put("country", countryValue);
                map.put("pincode", pincodeValue);
                map.put("model_no", model_Num);
                map.put("serial_no", serial_Num);
                map.put("start_date", startdate);
                map.put("type_of_contract", typeofcontract);
                map.put("contract_duration", contractdur);
                Log.e("testr", "testr" + map);
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


    private boolean validate() {


        boolean valid = true;


        username = name.getText().toString();
        email_id = email.getText().toString();
        contact_Num = contact_num.getText().toString();
        alt_num = alt_contact_num.getText().toString();
        model_Num = model_num.getText().toString();
        serial_Num = serial_num.getText().toString();
        door = door_no.getText().toString();
        streetValue = street.getText().toString();
        townValue = town.getText().toString();
        landValue = landmark.getText().toString();
        cityValue = city.getText().toString();
        stateValue = state.getText().toString();
        countryValue = country.getText().toString();
        pincodeValue = pincode.getText().toString();
        regionvalue = region_input.getText().toString();
        startdate = startdate_text.getText().toString();
        contractdur = contractduration.getText().toString();




        if (alt_num == contact_Num) {
            alt_contact_num.setError("Contact number and alternate number should not be same");
            valid = false;
        } else {
            alt_contact_num.setError(null);
        }


        if (username.isEmpty()) {
            name.setError("Enter name");
            valid = false;
        } else {
            name.setError(null);
        }


        if (email_id.isEmpty()) {
            email.setError("Enter email address");
            valid = false;
        } else {
            email.setError(null);
        }

        if (!isValidEmail(email_id)) {
            email.setError("Enter valid email address");
            valid = false;
        } else {
            email.setError(null);
        }


        if (door.isEmpty()) {
            door_no.setError("Enter door number");
            valid = false;
        } else {
            door_no.setError(null);
        }

        if (contact_Num.isEmpty()) {
            contact_num.setError("Enter contact number");
            valid = false;
        } else {
            contact_num.setError(null);
        }

        if (contact_Num.length() < 10) {
            contact_num.setError("Enter valid contact number");
            valid = false;
        }


      /*  if (landValue.isEmpty()) {
            landmark.setError("Enter land mark");
            valid = false;
        } else {
            landmark.setError(null);
        }*/


      if(startdate.isEmpty())
      {
          startdate_text.setError("Enter Startdate");
          valid = false;

      }else
      {
          startdate_text.setError(null);
      }


      if(contractdur.isEmpty())
      {
          contractduration.setError("Enter Contract duration");
          valid = false;

      }else
      {
          contractduration.setError(null);
      }


        if (streetValue.isEmpty()) {
            street.setError("Enter street name");
            valid = false;
        } else {
            street.setError(null);
        }

        if (townValue.isEmpty()) {
            town.setError("Enter town name");
            valid = false;
        } else {
            town.setError(null);
        }


        if (cityValue.isEmpty()) {
            city.setError("Enter city name");
            valid = false;
        } else {
            city.setError(null);
        }

        if (stateValue.isEmpty()) {
            state.setError("Enter state name");
            valid = false;
        } else {
            state.setError(null);
        }


        if (pincodeValue.isEmpty()) {
            pincode.setError("Enter pin code");
            valid = false;
        } else {
            pincode.setError(null);
        }

        if (pincodeValue.length() < 6) {
            pincode.setError("Enter valid pin code");
            valid = false;
        } else {
            pincode.setError(null);

        }


        if (countryValue.isEmpty()) {
            country.setError("Enter country name");
            valid = false;
        } else {
            country.setError(null);
        }

        if (model_Num.isEmpty()) {
            model_num.setError("Enter model no");
            valid = false;
        } else {
            model_num.setError(null);
        }

        if (serial_Num.isEmpty()) {
            serial_num.setError("Enter serial no");
            valid = false;
        } else {
            serial_num.setError(null);
        }

        return valid;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {



        }

    }
}
