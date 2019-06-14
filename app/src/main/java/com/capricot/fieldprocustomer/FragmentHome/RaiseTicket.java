package com.capricot.fieldprocustomer.FragmentHome;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.capricot.fieldprocustomer.API;
import com.capricot.fieldprocustomer.MyProfile;
import com.capricot.fieldprocustomer.Pref_storage;
import com.capricot.fieldprocustomer.R;
import com.capricot.fieldprocustomer.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by BalajiPrabhu on 9/13/2017.
 */

public class RaiseTicket extends Fragment {

    private Button attach_image;
    private Uri fileUri;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final String IMAGE_DIRECTORY_NAME = "SpotAudit_Images";
    static String filename = "";
    Context context;
    private Spinner product_spinner;
    private Spinner sub_category_spinner;
    private Spinner contract_type_spinner;
    private Spinner call_category_spinner;
    private Spinner work_type_spinner;
    private final String KEY_CompanyID = "company_id";
    private final String KEY_ProductID = "product_id";
    private final String KEY_SubCat = "cat_id";
    private final String KEY_cust_id = "cust_id";
    private final int REQUEST_CAMERA = 0;
    private final int SELECT_FILE = 1;
    private Button riseticket;
    private ImageView ivImage;
    private String userChoosenTask;
    public static String companyID;
    private static String productID;
    private static String subcatID;
    private static String contractID;
    private static String calcatID;
    public static String wortypeID;
    private static String cat_name;
    private static String amc_type;
    private static String model_num;
    public static String worktypeID;
    private static CheckBox location_yes;
    private static CheckBox location_no;
    private Pref_storage pref_storage;
    private static String door_no;
    private static String street;
    private static String town;
    public static String landmark;
    private static String city;
    private static String state;
    private static String country;
    private static String pincode;
    private static String imagepath;
    private static String prob_des;
    private static String region;
    private static String serial_no;
    private TextView date;
    private TextView time;
    private TextView model_number;
    private EditText prob_dec;
    private String sHour1, sMinute1;
    EditText loc_door_no, loc_street, loc_town, loc_landmark, loc_city, loc_state, loc_country, loc_pincode, region_input;
    private String company_id, CustomerID, CustomerName, CustomerMail, MobileNumber, alternatenumber, land;
    private ImageButton img;


    private ArrayList<String> productIDList,
            productNameList, sub_categoryNameList, sub_categoryIDList, contractTypeIDList, contractTypeName, call_categoryNameList, call_categoryIDList, work_typeIDList, work_typeNameList;


    public RaiseTicket() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rise_ticket, container, false);

        attach_image = (Button) view.findViewById(R.id.attach_image_button);
        riseticket = (Button) view.findViewById(R.id.raiseticket);

        imagepath = "";


        model_number = (TextView) view.findViewById(R.id.model_number);
        prob_dec = (EditText) view.findViewById(R.id.problem_description_input);

        product_spinner = (Spinner) view.findViewById(R.id.product_spinner);
        sub_category_spinner = (Spinner) view.findViewById(R.id.sub_category_spinner);
        contract_type_spinner = (Spinner) view.findViewById(R.id.contract_type_spinner);
        call_category_spinner = (Spinner) view.findViewById(R.id.call_category_spinner);
        work_type_spinner = (Spinner) view.findViewById(R.id.work_type_spinner);
        img = (ImageButton) view.findViewById(R.id.img);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyProfile.class);
                startActivity(intent);
            }
        });

        company_id = Pref_storage.getDetail(getContext(), "company_id");
        CustomerID = Pref_storage.getDetail(getContext(), "CustID");
        CustomerName = Pref_storage.getDetail(getContext(), "CustName");
        CustomerMail = Pref_storage.getDetail(getContext(), "CustMail");
        MobileNumber = Pref_storage.getDetail(getContext(), "Mobile");
        alternatenumber = Pref_storage.getDetail(getContext(), "alternatenumber");
        land = Pref_storage.getDetail(getContext(), "street");

        pref_storage = new Pref_storage();

        location_yes = (CheckBox) view.findViewById(R.id.checkbox_yes);
        location_no = (CheckBox) view.findViewById(R.id.checkbox_no);
        ivImage = (ImageView) view.findViewById(R.id.ivImage);

        productIDList = new ArrayList<String>();
        productNameList = new ArrayList<String>();

        sub_categoryIDList = new ArrayList<String>();
        sub_categoryNameList = new ArrayList<String>();


        contractTypeIDList = new ArrayList<String>();
        contractTypeName = new ArrayList<String>();

        call_categoryNameList = new ArrayList<String>();
        call_categoryIDList = new ArrayList<String>();

        work_typeIDList = new ArrayList<String>();
        work_typeNameList = new ArrayList<String>();


        //product api
        getProductRaiseTicket();

        product_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //   Toast.makeText(getActivity(), ""+product_spinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();

                productID = productIDList.get(position);
                //Toast.makeText(getActivity(), "" + productID, Toast.LENGTH_SHORT).show();

                getCategoryRaiseTicket(productID);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sub_category_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                subcatID = sub_categoryIDList.get(position);
                getModelNumber();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //work type api
        getWorkTypeTicket();

        work_type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //  Toast.makeText(getActivity(), ""+parent.getSelectedItem(), Toast.LENGTH_SHORT).show();

                worktypeID = work_typeIDList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        getCallCategoryTicket();

        call_category_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                calcatID = call_categoryIDList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        getContractType();

        contract_type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cat_name = contractTypeName.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        prob_des = prob_dec.getText().toString();

        attach_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                rl_main.setVisibility(View.GONE);
//                rl_image.setVisibility(View.VISIBLE);
                String BX1 = android.os.Build.MANUFACTURER;
                if (BX1.equalsIgnoreCase("samsung")) {

                    selectImage();
                } else if (BX1.equalsIgnoreCase("asus")) {
                    selectImage();
                } else if (BX1.equalsIgnoreCase("OnePlus")) {
                    selectImage();
                } else if (BX1.equalsIgnoreCase("HTC")) {
                    selectImage();
                } else {
                    selectImage();

                }
            }

        });


        riseticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validateRiseTicketsForm()) {

                    registerticket();

                }
            }
        });

        door_no = pref_storage.getDetail(getActivity(), "door_no");
        street = pref_storage.getDetail(getActivity(), "street");
        town = pref_storage.getDetail(getActivity(), "town");
        city = pref_storage.getDetail(getActivity(), "city");
        state = pref_storage.getDetail(getActivity(), "state");
        country = pref_storage.getDetail(getActivity(), "country");
        pincode = pref_storage.getDetail(getActivity(), "pincode");
        region = pref_storage.getDetail(getActivity(), "region");
        //   serial_no = pref_storage.getDetail(getActivity(), "serial_no");

        date = (TextView) view.findViewById(R.id.date_id);
        time = (TextView) view.findViewById(R.id.time_id);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                String month, datee;

                                datee = Integer.toString(dayOfMonth);
                                month = Integer.toString(monthOfYear + 1);

                                if (month.length() == 1) {
                                    month = "0" + (monthOfYear + 1);
                                }
                                if (datee.length() == 1) {

                                    datee = "0" + dayOfMonth;
                                }

                                String frmdate = year + "-" + month + "-" + datee;

                                date.setText(frmdate);


//                                String frmdate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
//
//                                date.setText(frmdate);
//                                date.setError(null);
//                                Log.e("frmdate", "frmdate" + frmdate);


                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar mcurrentTime = Calendar.getInstance();
                final int[] hour = {mcurrentTime.get(Calendar.HOUR_OF_DAY)};
                int minute = mcurrentTime.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;

                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        String shour1 = String.valueOf(selectedHour);
                        String sminute1 = String.valueOf(selectedMinute);

                        if ((shour1.length() == 1) && (sminute1.length() == 1)) {
                            sHour1 = "0" + shour1;
                            sMinute1 = "0" + sminute1;
                        } else if ((shour1.length() == 2) && (sminute1.length() == 1)) {
                            sHour1 = shour1;
                            sMinute1 = "0" + sminute1;
                        } else if ((shour1.length() == 1) && (sminute1.length() == 2)) {
                            sHour1 = "0" + shour1;
                            sMinute1 = sminute1;
                        } else {
                            sHour1 = shour1;
                            sMinute1 = sminute1;
                        }

//                        String AM_PM;
//                        if (selectedHour < 12) {
//                            AM_PM = "AM";
//                        } else {
//                            AM_PM = "PM";
//                        }

                        String timew = sHour1 + ":" + sMinute1;

                        time.setText(timew);
                        time.setError(null);

                    }
                }, hour[0], minute, true);//Yes 24 hour time

                mTimePicker.show();


//                Calendar mcurrentTime = Calendar.getInstance();
//                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
//                int minute = mcurrentTime.get(Calendar.MINUTE);
//                TimePickerDialog mTimePicker;
//                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
//                    @Override
//                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
//                        time.setText(selectedHour + ":" + selectedMinute);
//                        time.setError(null);
//                    }
//                }, hour, minute, true);//Yes 24 hour time
//                mTimePicker.setTitle("Select Time");
//                mTimePicker.show();

            }

        });

// builderSingle.show().getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);

// }
//
// });


        return view;

    }

    private boolean validateRiseTicketsForm() {

        boolean valid = true;


        prob_des = prob_dec.getText().toString();

        if (prob_des.isEmpty()) {
            prob_dec.setError("Please enter problem description");
            valid = false;
        } else {
            prob_dec.setError(null);
        }

        if ((date.getText().toString()).isEmpty()) {
            date.setError("Please select date");
            valid = false;
        } else {
            date.setError(null);
        }

        if ((time.getText().toString()).isEmpty()) {
            time.setError("Please select time");
            valid = false;
        } else {
            time.setError(null);
        }

        if (call_category_spinner.getSelectedItem().toString().equals("Select Call Category")) {
            Toast.makeText(getActivity(), "Please select Call Category", Toast.LENGTH_SHORT).show();
            valid = false;
        }
        if (work_type_spinner.getSelectedItem().toString().equals("")) {
            Toast.makeText(getActivity(), "Please select Work Type", Toast.LENGTH_SHORT).show();
            valid = false;
        }


        return valid;

    }


    private void registerticket() {

        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, API.RaiseTicket,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            pDialog.dismiss();

                            JSONObject jobj = new JSONObject(response);
                            Log.d("zfnsdkfd", "sdkfhsdkl" + response);
                            //  Toast.makeText(context, "response"+response, Toast.LENGTH_SHORT).show();
                            int status = jobj.getInt("status");

                            if (status == 1) {

                                String message = jobj.getString("msg");
                                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setTitle("Success");
                                builder.setMessage("Ticket " + message);
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        date.setText("");
                                        time.setText("");
                                        prob_dec.setText("");
                                        ivImage.setImageBitmap(null);
                                        //viji
                                      /*  product_spinner.setSelection(0);
                                        sub_category_spinner.setSelection(0);
                                        contract_type_spinner.setSelection(0);
                                        call_category_spinner.setSelection(0);
                                        work_type_spinner.setSelection(0);*/
                                        dialogInterface.dismiss();
                                    }
                                });
                                AlertDialog alt = builder.create();
                                alt.show();

                            } else {
                                String msg = jobj.getString("msg");
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setTitle("Failure");
                                builder.setMessage(msg);
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        dialogInterface.dismiss();
                                    }
                                });
                                AlertDialog alt = builder.create();
                                alt.show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

//                        Toast.makeText(getActivity(), "ticket" + error, Toast.LENGTH_SHORT).show();

                        final AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                        builder1.setTitle("Failure");
                        builder1.setMessage("Please make sure that all the fields are filled");
                        builder1.setCancelable(true);

                        builder1.setPositiveButton(
                                "ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        pDialog.dismiss();

                                    }
                                });


                        AlertDialog alert11 = builder1.create();
                        alert11.show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put(KEY_CompanyID, company_id);
                map.put(KEY_ProductID, productID);
                map.put(KEY_cust_id, CustomerID);
                map.put("name", CustomerName);
                map.put("emailid", CustomerMail);
                map.put("contact_no", MobileNumber);
                map.put("alt_no", alternatenumber);
                map.put("door_no", door_no);
                map.put("region", "south");
                map.put("street", street);
                map.put("town", town);
                map.put("landmark", land);
                map.put("city", city);
                map.put("state", state);
                map.put("country", country);
                map.put("pincode", pincode);
                map.put("cat_id", subcatID);
                map.put("contract_type", cat_name);
                map.put("pref_date", date.getText().toString());
                map.put("pref_time", time.getText().toString());
                map.put("call_tag", calcatID);
                map.put("ticket_image", imagepath);
                map.put("prob_desc", prob_des);
                map.put("model_no", model_num);
                map.put("serial_no", serial_no);
                // map.put("work_type", String.valueOf(work_type_spinner.getSelectedItem().toString()));
                map.put("work_type", worktypeID);

                Log.e("testing", "test->" + map);
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
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);


    }


    private void getWorkTypeTicket() {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, API.WorkType,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject jobj = new JSONObject(response);
                            int status = jobj.getInt("status");

                            work_typeNameList.clear();
                            work_typeIDList.clear();

                            //  work_typeIDList.add("Select Work Type");
                            //  work_typeNameList.add("Select Work Type");

                            if (status == 1) {

                                JSONArray jaryproduct = jobj.getJSONArray("result");

                                for (int i = 0; i < jaryproduct.length(); i++) {
                                    JSONObject restatusobj = jaryproduct.getJSONObject(i);
                                    String call = restatusobj.getString("service_group");
                                    String id = restatusobj.getString("service_group_id");
                                    work_typeIDList.add(id);
                                    work_typeNameList.add(call);
                                }
                                if (getActivity() != null) {
                                    ArrayAdapter<String> adp2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, work_typeNameList);
                                    adp2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    work_type_spinner.setAdapter(adp2);
                                }

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

                        Toast.makeText(getActivity(), error + "Something went wrong please try again later ! ", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("comp_id", company_id);

                Log.e("nowtest", "calcat--->" + map);

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
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);


    }

    private void getModelNumber() {
//        final ProgressDialog pDialog = new ProgressDialog(getActivity());
//        pDialog.setMessage("Loading...");
//        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, API.ModelNumber,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        /* Toast.makeText(getActivity(), "asc"+response, Toast.LENGTH_SHORT).show();*/

                        try {

                            Log.e("serialnumber", "onResponse: " + response);
//                            pDialog.dismiss();

                            JSONObject jobj = new JSONObject(response);
                            int status = jobj.getInt("status");

                            //productNameList.clear();
                            //sub_categoryNameList.clear();

                            if (status == 1) {

                                JSONArray jaryproduct = jobj.getJSONArray("result");

                                for (int i = 0; i < jaryproduct.length(); i++) {

                                    JSONObject restatusobj = jaryproduct.getJSONObject(i);
                                    model_num = restatusobj.getString("model_no");
                                    serial_no = restatusobj.getString("serial_no");
                                    model_number.setText(model_num);

                                }

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

                        Toast.makeText(getActivity(), error + "Something went wrong please try again later ! ", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<String, String>();
                map.put(KEY_CompanyID, company_id);
                map.put(KEY_cust_id, CustomerID);
                map.put(KEY_ProductID, productID);
                map.put(KEY_SubCat, subcatID);

                Log.e("nowtest", "modelno---->" + map);
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
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);


    }


    private void getProductRaiseTicket() {

        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, API.RaiseTicket_Product,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(getActivity(), "asc"+response, Toast.LENGTH_SHORT).show();

                        try {

                            pDialog.dismiss();

                            JSONObject jobj = new JSONObject(response);
                            int status = jobj.getInt("status");

                            if (status == 1) {

                                JSONArray jaryproduct = jobj.getJSONArray("result");
                                productNameList.clear();
                                for (int i = 0; i < jaryproduct.length(); i++) {
                                    JSONObject restatusobj = jaryproduct.getJSONObject(i);
                                    String product_id = restatusobj.getString("product_id");
                                    String product_name = restatusobj.getString("product_name");
                                    productIDList.add(product_id);
                                    productNameList.add(product_name);


                                }

                                ArrayAdapter<String> adp2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, productNameList);
                                /* Toast.makeText(context, "product"+productNameList, Toast.LENGTH_SHORT).show();*/
                                adp2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                product_spinner.setAdapter(adp2);


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

                        Toast.makeText(getActivity(), error + "Something went wrong please try again later ! ", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put(KEY_CompanyID, company_id);
                map.put(KEY_cust_id, CustomerID);

                Log.e("nowtest", "product---->" + map);
//                Log.e("svsgv","wsfe"+map);
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
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }


    private void getCategoryRaiseTicket(final String productID) {

//        final ProgressDialog pDialog = new ProgressDialog(getActivity());
//        pDialog.setMessage("Loading...");
//        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, API.RaiseTicket_Category,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

//                            pDialog.dismiss();

                            JSONObject jobj = new JSONObject(response);
                            int status = jobj.getInt("status");

                            sub_categoryNameList.clear();
                            sub_categoryIDList.clear();

                            if (status == 1) {

                                JSONArray jaryproduct = jobj.getJSONArray("result");

                                for (int i = 0; i < jaryproduct.length(); i++) {
                                    JSONObject restatusobj = jaryproduct.getJSONObject(i);
                                    String sub_cat_id = restatusobj.getString("cat_id");
                                    String sub_cat_name = restatusobj.getString("cat_name");
                                    sub_categoryIDList.add(sub_cat_id);
                                    sub_categoryNameList.add(sub_cat_name);


                                }

                                ArrayAdapter<String> adp2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, sub_categoryNameList);
                                adp2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                sub_category_spinner.setAdapter(adp2);

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

                        Toast.makeText(getActivity(), error + "Something went wrong please try again later ! ", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put(KEY_CompanyID, company_id);
                map.put(KEY_ProductID, productID);
                map.put(KEY_cust_id, CustomerID);
                Log.e("nowtest", "raisetkt---->" + map);
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
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);


    }

    private void getContractType() {

        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
//        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, API.ContractType,
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

                                if (getActivity() != null) {
                                    ArrayAdapter<String> adp2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, contractTypeName);
                                    adp2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    contract_type_spinner.setAdapter(adp2);
                                }


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

                        Toast.makeText(getActivity(), error + "Something went wrong please try again later ! ", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put(KEY_cust_id, CustomerID);
                map.put("company_id", company_id);

                Log.e("nowtest", "contracttype--->" + map);

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
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }


    private void getCallCategoryTicket() {

//        final ProgressDialog pDialog = new ProgressDialog(getActivity());
//        pDialog.setMessage("Loading...");
//        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, API.RaiseTicket_CallCategory,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

//                            Toast.makeText(getActivity(), "cattype"+response, Toast.LENGTH_SHORT).show();
//                            pDialog.dismiss();

                            JSONObject jobj = new JSONObject(response);
                            int status = jobj.getInt("status");

                            call_categoryNameList.clear();
                            call_categoryIDList.clear();


                            call_categoryIDList.add("Select Call Category");
                            call_categoryNameList.add("Select Call Category");

                            if (status == 1) {

                                JSONArray jaryproduct = jobj.getJSONArray("result");
                                for (int i = 0; i < jaryproduct.length(); i++) {
                                    JSONObject restatusobj = jaryproduct.getJSONObject(i);
                                    String call = restatusobj.getString("call");
                                    String id = restatusobj.getString("id");
                                    call_categoryIDList.add(id);
                                    call_categoryNameList.add(call);
                                }
                                if (getActivity() != null) {
                                    ArrayAdapter<String> adp2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, call_categoryNameList);
                                    adp2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    call_category_spinner.setAdapter(adp2);
                                }


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

                        Toast.makeText(getActivity(), error + "Something went wrong please try again later ! ", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("comp_id", company_id);

                Log.e("nowtest", "calcat--->" + map);

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
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if (userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
        builder.setTitle("Add photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(getActivity());

                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        imagepath = String.valueOf(destination);
        ivImage.setImageBitmap(thumbnail);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Uri selectedImage = data.getData();
        String[] filePath = {MediaStore.Images.Media.DATA};
        Cursor c = getActivity().getContentResolver().query(selectedImage, filePath, null, null, null);

        c.moveToFirst();
        int columnIndex = c.getColumnIndex(filePath[0]);
        String picturePath = c.getString(columnIndex);
        c.close();
        Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
        Log.w("path", picturePath + "" + thumbnail);

        Log.e("path of .", "pathfile" + picturePath);
        // viewImage.setImageBitmap(thumbnail);
        imagepath = picturePath;

        ivImage.setImageBitmap(thumbnail);
    }


}

