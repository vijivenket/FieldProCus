package com.capricot.fieldprocustomer.FragmentHome;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.capricot.fieldprocustomer.Pref_storage;
import com.capricot.fieldprocustomer.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by BalajiPrabhu on 9/13/2017.
 */

public class AmcContract extends Fragment implements Spinner.OnItemSelectedListener {

    Button attach_image;
    private Uri fileUri;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final String IMAGE_DIRECTORY_NAME = "SpotAudit_Images";
    static String filename = "";
    Context context;
    private Spinner product_spinner;
    private Spinner sub_category_spinner;
    private Spinner contract_type_spinner;
    Spinner call_category_spinner;
    private final String KEY_CompanyID = "company_id";
    private final String KEY_ProductID = "product_id";
    private final String KEY_cust_id = "cust_id";
    private final String KEY_name = "name";
    private final String KEY_emailid = "emailid";
    private final String KEY_contact_no = "contact_no";
    private final String KEY_alt_no = "alt_no";
    private final String KEY_door_no = "door_no";
    private final String KEY_street = "street";
    private final String KEY_town = "town";
    private final String KEY_landmark = "landmark";
    private final String KEY_city = "city";
    private final String KEY_state = "state";
    private final String KEY_country = "country";
    private final String KEY_pincode = "pincode";
    private final String KEY_product_id = "product_id";
    private final String KEY_cat_id = "cat_id";
    private final String KEY_model_no = "model_no";
    private final String KEY_contract_type = "contract_type";
    private final String KEY_quantity = "quantity";
    private final String KEY_pref_date = "pref_date";
    private final String KEY_pref_time = "pref_time";

    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private Button raisecontract;
    private ImageView ivImage;
    private String userChoosenTask;
    public static String companyID;
    private static String productID;
    private static String subcatID;
    private static String contractID;
    public static String calcatID;
    private static String cat_name;
    private static String amc_type;
    public static String model_num;
    private static CheckBox location_yes;
    private static CheckBox location_no;
    private Pref_storage pref_storage;
    private static String door_no;
    private static String street;
    private static String town;
    private static String landmark;
    private static String city;
    private static String state;
    private static String country;
    private static String pincode;
    public static String imagepath;
    public static String prob_des;
    private static String region;
    private TextView date;
    private TextView time;
    private EditText quantity;
    private EditText model_number;
    private EditText loc_door_no;
    private EditText loc_street;
    private EditText loc_town;
    private EditText loc_landmark;
    private EditText loc_city;
    private EditText loc_state;
    private EditText loc_country;
    private EditText loc_pincode;
    private EditText region_input;
    private String company_id, CustomerID, CustomerName, CustomerMail, MobileNumber, alternatenumber, land;


    private ArrayList<String> productIDList,
            productNameList,sub_categoryNameList,sub_categoryIDList,contractTypeIDList,contractTypeName,call_categoryNameList,call_categoryIDList;



    public AmcContract() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_amc_contract, container, false);


        raisecontract = (Button)view.findViewById(R.id.raisecontract);


        model_number = (EditText) view.findViewById(R.id.model_number);
     //   prob_dec = (EditText)view.findViewById(R.id.problem_description_input);

        product_spinner = (Spinner)view.findViewById(R.id.product_spinner);
        sub_category_spinner = (Spinner)view.findViewById(R.id.sub_category_spinner);
        contract_type_spinner = (Spinner)view.findViewById(R.id.contract_type_spinner);
        quantity = (EditText)view.findViewById(R.id.quantity);
        pref_storage=new Pref_storage();

        location_yes = (CheckBox)view.findViewById(R.id.checkbox_yes);
        location_no = (CheckBox)view.findViewById(R.id.checkbox_no);


        ivImage = (ImageView) view.findViewById(R.id.ivImage);

        company_id = Pref_storage.getDetail(getContext(), "company_id");
        CustomerID = Pref_storage.getDetail(getContext(), "CustID");
        CustomerName = Pref_storage.getDetail(getContext(), "CustName");
        CustomerMail = Pref_storage.getDetail(getContext(), "CustMail");
        MobileNumber = Pref_storage.getDetail(getContext(), "Mobile");
        alternatenumber = Pref_storage.getDetail(getContext(), "alternatenumber");
        land = Pref_storage.getDetail(getContext(), "street");


        productIDList = new ArrayList<String>();
        productNameList = new ArrayList<String>();

        sub_categoryIDList = new ArrayList<String>();
        sub_categoryNameList = new ArrayList<String>();


        contractTypeIDList = new ArrayList<String>();
        contractTypeName = new ArrayList<String>();

        call_categoryNameList = new ArrayList<String>();
        call_categoryIDList = new ArrayList<String>();


        product_spinner.setOnItemSelectedListener(this);
        sub_category_spinner.setOnItemSelectedListener(this);
        contract_type_spinner.setOnItemSelectedListener(this);
     //   call_category_spinner.setOnItemSelectedListener(this);


        getamcProductRaiseTicket();





        raisecontract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validateRiseTicketsForm();

                if(validateRiseTicketsForm()){
                    registerticket();
                }
            }
        });


        location_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(location_yes.isChecked()){

                    door_no     = pref_storage.getDetail(getActivity(),"door_no");
                    street      = pref_storage.getDetail(getActivity(), "street");
                    town        = pref_storage.getDetail(getActivity(), "town");
                    city        = pref_storage.getDetail(getActivity(), "city");
                    state       = pref_storage.getDetail(getActivity(), "state");
                    country     = pref_storage.getDetail(getActivity(), "country");
                    pincode     = pref_storage.getDetail(getActivity(), "pincode");

                }

                location_no.setChecked(false);
                location_yes.setChecked(true);
            }


        });


        location_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.location_alertbox);
                dialog.setTitle("Enter address");



                Button save;

                TextView close;



                loc_door_no             = (EditText)dialog.findViewById(R.id.door_no_input);
                loc_street              = (EditText)dialog.findViewById(R.id.street_input);
                loc_town                = (EditText)dialog.findViewById(R.id.town_input);
                loc_landmark            = (EditText)dialog.findViewById(R.id.landmark_input);
                loc_city                = (EditText)dialog.findViewById(R.id.city_input);
                loc_state               = (EditText)dialog.findViewById(R.id.state_input);
                loc_country             = (EditText)dialog.findViewById(R.id.country_input);
                loc_pincode             = (EditText)dialog.findViewById(R.id.pincode_input);
                region_input             = (EditText)dialog.findViewById(R.id.region_input);



                save = (Button)dialog.findViewById(R.id.register);
                close = (TextView)dialog.findViewById(R.id.close);

                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        location_yes.setChecked(true);
                        location_no.setChecked(false);

                    }
                });


                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        if(validate()){
                            door_no = loc_door_no.getText().toString();
                            street = loc_street.getText().toString();
                            town = loc_town.getText().toString();
                            landmark = loc_landmark.getText().toString();
                            city = loc_city.getText().toString();
                            state = loc_state.getText().toString();
                            country = loc_country.getText().toString();
                            pincode = loc_pincode.getText().toString();
                            region = region_input.getText().toString();

                            door_no = Pref_storage.getDetail(getContext(), "door_no");
                            street = Pref_storage.getDetail(getContext(), "street");
                            town = Pref_storage.getDetail(getContext(), "town");
                            city = Pref_storage.getDetail(getContext(), "city");
                            state = Pref_storage.getDetail(getContext(), "state");
                            country = Pref_storage.getDetail(getContext(), "country");
                            pincode = Pref_storage.getDetail(getContext(), "pincode");
                            region = Pref_storage.getDetail(getContext(),"region");

                            dialog.cancel();
                        }

                    }
                });


                 dialog.show();

                location_no.setChecked(true);
                location_yes.setChecked(false);

            }
        });

        date = (TextView)view.findViewById(R.id.date_id);
        time = (TextView)view.findViewById(R.id.time_id);
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

                                String frmdate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;

                                date.setText(frmdate);

                                Log.e("frmdate", "frmdate" + frmdate);


                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });



        time.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {


                                        Calendar mcurrentTime = Calendar.getInstance();
                                        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                                        int minute = mcurrentTime.get(Calendar.MINUTE);
                                        TimePickerDialog mTimePicker;
                                        mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                                            @Override
                                            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                                time.setText(selectedHour + ":" + selectedMinute);
                                            }
                                        }, hour, minute, true);//Yes 24 hour time
                                        mTimePicker.setTitle("Select time");
                                        mTimePicker.show();

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



        if ((quantity.getText().toString()).isEmpty()){
            quantity.setError("Please enter quantity");
            valid = false;
        }else{
            quantity.setError(null);
        }
        if ((model_number.getText().toString()).isEmpty()){
            model_number.setError("Please enter quantity");
            valid = false;
        }else{
            model_number.setError(null);
        }

        if ((date.getText().toString()).isEmpty()){
            date.setError("Please select date");
            valid = false;
        }else{
            date.setError(null);
        }

        if ((time.getText().toString()).isEmpty()){
            time.setError("Please select time");
            valid = false;
        }else{
            time.setError(null);
        }



        if(location_no.isChecked() || location_yes.isChecked() ){
        }else{
            valid = false;
        }


        return valid;

    }

    private boolean validate() {
        boolean valid = true;


        if (loc_door_no.getText().toString().isEmpty() ) {
            loc_door_no.setError("Enter door number");
            valid = false;
        } else {
            loc_door_no.setError(null);
        }


        if (loc_landmark.getText().toString().isEmpty()) {
            loc_landmark.setError("Enter land mark");
            valid = false;
        } else {
            loc_landmark.setError(null);
        }

        if (loc_street.getText().toString().isEmpty() ) {
            loc_street.setError("Enter street name");
            valid = false;
        } else {
            loc_street.setError(null);
        }

        if (loc_town.getText().toString().isEmpty() ) {
            loc_town.setError("Enter town name");
            valid = false;
        } else {
            loc_town.setError(null);
        }


        if (loc_city.getText().toString().isEmpty() ) {
            loc_city.setError("Enter city name");
            valid = false;
        } else {
            loc_city.setError(null);
        }

        if (loc_state.getText().toString().isEmpty() ) {
            loc_state.setError("Enter state name");
            valid = false;
        } else {
            loc_state.setError(null);
        }


        if (loc_pincode.getText().toString().isEmpty() ) {
            loc_pincode.setError("Enter pin code");
            valid = false;
        } else {
            loc_pincode.setError(null);
        }

        if (loc_pincode.length()<6) {
            loc_pincode.setError("Enter valid pin code");
            valid = false;
        } else {
            loc_pincode.setError(null);
        }



        if (loc_country.getText().toString().isEmpty() ) {
            loc_country.setError("Enter country name");
            valid = false;
        } else {
            loc_country.setError(null);
        }


        return valid;
    }

    private void registerticket() {
        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.show();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, API.AMCcontract,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            pDialog.dismiss();


                          /*  final AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                            builder1.setMessage("Raised Successfully");
                            builder1.setCancelable(true);

                            builder1.setPositiveButton(
                                    "ok",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                            date.setText("");
                                            time.setText("");
                                            location_yes.setChecked(false);
                                            location_no.setChecked(false);
                                            ivImage.setImageResource(0);

                                        }
                                    });


                            AlertDialog alert11 = builder1.create();
                            alert11.show();*/
                            JSONObject jobj = new JSONObject(response);
                            Log.d("zfnsdkfd","sdkfhsdkl"+response);
                          //  Toast.makeText(context, "response"+response, Toast.LENGTH_SHORT).show();
                            int status = jobj.getInt("status");

                            if(status == 1){

                                String message = jobj.getString("msg");
                                AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                                builder.setTitle("Success");
                                builder.setMessage(message);
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        date.setText("");
                                        time.setText("");
                                        quantity.setText("");
                                        model_number.setText("");
                                        location_yes.setChecked(false);
                                        location_no.setChecked(false);
                                      dialogInterface.dismiss();
                                    }
                                });
                                AlertDialog alt=builder.create();
                                alt.show();

                                 } else{
                                String msg = jobj.getString("msg");
                                AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                                builder.setTitle("Failure");
                                builder.setMessage(msg);
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        dialogInterface.dismiss();
                                    }
                                });
                                AlertDialog alt=builder.create();
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

                                    }
                                });


                        AlertDialog alert11 = builder1.create();
                        alert11.show();

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put(KEY_CompanyID, company_id);
                map.put(KEY_cust_id, CustomerID);
                map.put(KEY_name,CustomerName );
                map.put(KEY_emailid, CustomerMail);
                map.put(KEY_contact_no,MobileNumber );
                map.put(KEY_alt_no,alternatenumber );
                map.put(KEY_door_no, door_no);
                map.put(KEY_street, street);
                map.put(KEY_town, town);
                map.put(KEY_landmark, land);
                map.put(KEY_city, city);
                map.put(KEY_state, state);
                map.put(KEY_country, country);
                map.put(KEY_pincode, pincode);
                map.put(KEY_product_id, productID);
                map.put(KEY_cat_id,subcatID );
                map.put(KEY_model_no,model_number.getText().toString() );
                map.put(KEY_contract_type,cat_name);
                map.put(KEY_quantity, quantity.getText().toString());
                map.put(KEY_pref_date,date.toString() );
                map.put(KEY_pref_time,time.toString());

                Log.e("testing","test->"+map);
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
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Spinner spinner = (Spinner) adapterView;

        switch (spinner.getId()){


            case R.id.product_spinner:
                productID = productIDList.get(i);
                getamcCategoryRaiseTicket(productID);

                break;

            case R.id.sub_category_spinner:
                subcatID = sub_categoryIDList.get(i);
                getamcContractType();
            /*    getModelNumber(productID,subcatID);*/
                break;

            case R.id.contract_type_spinner:
                cat_name = contractTypeName.get(i);
                getamcCallCategoryTicket();
                break;
           /* case R.id.call_category_spinner:
                calcatID = call_categoryIDList.get(i);
                break;
*/
        }
    }

/*
    private void getModelNumber(final String productID, final String subcatID) {



        StringRequest stringRequest = new StringRequest(Request.Method.POST, API.ModelNumber,
                new Response.Listener<String>() {

            @Override
                    public void onResponse(String response) {
                       */
/* Toast.makeText(getActivity(), "asc"+response, Toast.LENGTH_SHORT).show();*//*


                        try {

                            JSONObject jobj = new JSONObject(response);
                            int status = jobj.getInt("status");

                            if(status == 1){

                                JSONArray jaryproduct = jobj.getJSONArray("result");
                                productNameList.clear();
                                for (int i = 0; i < jaryproduct.length(); i++) {

                                    JSONObject restatusobj = jaryproduct.getJSONObject(i);
                                    model_num = restatusobj.getString("model_no");
                                    model_number.setText(model_num);


                                }

                            } else{

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getActivity(), error+ "Something went wrong please try again later ! ", Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put(KEY_CompanyID, company_id);
                map.put(KEY_cust_id, CustomerID);
                map.put(KEY_ProductID, productID);
                map.put(KEY_SubCat, subcatID);

                Log.e("ModelNumber","ModelNumber->"+map);
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
*/


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }





    private void getamcProductRaiseTicket() {

        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, API.RaiseTicket_Product,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       /* Toast.makeText(getActivity(), "asc"+response, Toast.LENGTH_SHORT).show();*/

                        try {
                            pDialog.dismiss();

                            JSONObject jobj = new JSONObject(response);
                            int status = jobj.getInt("status");

                            if(status == 1){

                                JSONArray jaryproduct = jobj.getJSONArray("result");
                                productNameList.clear();
                                for (int i = 0; i < jaryproduct.length(); i++) {
                                    JSONObject restatusobj = jaryproduct.getJSONObject(i);
                                    String product_id = restatusobj.getString("product_id");
                                    String product_name = restatusobj.getString("product_name");
                                    productIDList.add(product_id);
                                    productNameList.add(product_name);

                                }

                                if (getActivity()!=null){
                                    ArrayAdapter<String> adp2 = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item, productNameList);
                               /* Toast.makeText(context, "product"+productNameList, Toast.LENGTH_SHORT).show();*/
                                    adp2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    product_spinner.setAdapter(adp2);
                                }



                            } else{

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getActivity(), error+ "Something went wrong please try again later ! ", Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put(KEY_CompanyID, company_id);
                map.put(KEY_cust_id, CustomerID);
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

    private void getamcCategoryRaiseTicket(final String productID) {

        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, API.RaiseTicket_Category,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            pDialog.dismiss();

                            JSONObject jobj = new JSONObject(response);
                            int status = jobj.getInt("status");

                            if (status == 1) {

                                JSONArray jaryproduct = jobj.getJSONArray("result");
                                sub_categoryNameList.clear();
                                for (int i = 0; i < jaryproduct.length(); i++) {
                                    JSONObject restatusobj = jaryproduct.getJSONObject(i);
                                    String sub_cat_id = restatusobj.getString("cat_id");
                                    String sub_cat_name = restatusobj.getString("cat_name");
                                    sub_categoryIDList.add(sub_cat_id);
                                    sub_categoryNameList.add(sub_cat_name);

                                }
                                if (getActivity()!=null){
                                    ArrayAdapter<String> adp2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, sub_categoryNameList);
                                    adp2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    sub_category_spinner.setAdapter(adp2);
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
                map.put(KEY_CompanyID, company_id);
                map.put(KEY_ProductID, productID);
                map.put(KEY_cust_id, CustomerID);
                Log.e("hai", "jdjd" + map);
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
    private void getamcContractType() {

        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, API.RaiseTicket_Contractortype,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

//                            Toast.makeText(getActivity(), "cattype"+response, Toast.LENGTH_SHORT).show();
                            pDialog.dismiss();

                            JSONObject jobj = new JSONObject(response);
                            int status = jobj.getInt("status");

                            if(status == 1){

                                JSONArray jaryproduct = jobj.getJSONArray("result");
                                contractTypeName.clear();
                                for (int i = 0; i < jaryproduct.length(); i++) {
                                    JSONObject restatusobj = jaryproduct.getJSONObject(i);
                                     amc_type = restatusobj.getString("amc_type");
                                     contractID = restatusobj.getString("id");
                                    contractTypeIDList.add(contractID);
                                    contractTypeName.add(amc_type);
                                }
                                if(contractTypeName.isEmpty()){
                                    contractTypeName.add("On-Demand");
                                }

                                if (getActivity()!=null){
                                    ArrayAdapter<String> adp2 = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item, contractTypeName);
                                    adp2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    contract_type_spinner.setAdapter(adp2);
                                }


                            } else{

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getActivity(), error+ "Something went wrong please try again later ! ", Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put(KEY_cust_id, CustomerID);
                map.put(KEY_CompanyID, company_id);
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


    private void getamcCallCategoryTicket() {

        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, API.RaiseTicket_CallCategory,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            pDialog.dismiss();

//                            Toast.makeText(getActivity(), "cattype"+response, Toast.LENGTH_SHORT).show();


                            JSONObject jobj = new JSONObject(response);
                            int status = jobj.getInt("status");

                            if(status == 1){

                                JSONArray jaryproduct = jobj.getJSONArray("result");
                                call_categoryNameList.clear();
                                for (int i = 0; i < jaryproduct.length(); i++) {
                                    JSONObject restatusobj = jaryproduct.getJSONObject(i);
                                    String call = restatusobj.getString("call");
                                    String id = restatusobj.getString("id");
                                    call_categoryIDList.add(id);
                                    call_categoryNameList.add(call);
                                }
                              /*  if (getActivity()!=null){
                                    ArrayAdapter<String> adp2 = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item, call_categoryNameList);
                                    adp2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    call_category_spinner.setAdapter(adp2);
                                }*/



                            } else{

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getActivity(), error+ "Something went wrong please try again later ! ", Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("comp_id", company_id);
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
}

