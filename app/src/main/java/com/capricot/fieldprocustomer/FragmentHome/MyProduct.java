package com.capricot.fieldprocustomer.FragmentHome;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
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
import com.capricot.fieldprocustomer.API;
import com.capricot.fieldprocustomer.Adapters.ProductAdapter;
import com.capricot.fieldprocustomer.Bean.Product;
import com.capricot.fieldprocustomer.Home;
import com.capricot.fieldprocustomer.MyProfile;
import com.capricot.fieldprocustomer.Pref_storage;
import com.capricot.fieldprocustomer.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by BalajiPrabhu on 9/13/2017.
 */

public class MyProduct extends Fragment implements Spinner.OnItemSelectedListener,FragmentManager.OnBackStackChangedListener {


    private View view;
    private RecyclerView recyclerView;
    private List<Product> productList = new ArrayList<>();
    private ProductAdapter productAdapter;
    private static Context context;
    private Spinner product_spinner;
    private Spinner sub_category_spinner;
    private final String KEY_CompanyID = "company_id";
    private final String KEY_ProductID = "product_id";
    private final String KEY_cust_id = "cust_id";
    private String categoryName;
    private String categoryID;
    private String productName;
    private String productID;
    private String companyName;
    private String companyID;
    private ProductAdapter productadapter;
    private Product product;
    private EditText serial_no;
    private EditText model_no;
    private EditText retailer_name;
    private TextView purcharseDate;
    private Spinner contract_type_spinner;

    private static String typeofcontract;
    private static String product_name;
    private static String category;
    private static String model;
    private static String serial;
    private static String contract_type;
    public static String contract_typeID;
    public static String contract_typeName;
    private static String expiry_date;
    public static String purchase_date;
    private static String retailerName;

    private Button addProduct;
    private Button confirm;
    private Button cancel;
    private RelativeLayout addproductForm;
    private ImageButton img;
    private final ArrayList<Product> ProductArrayList = new ArrayList<>();
    private ArrayList<String> productNameList, productIDList, sub_categoryNameList, sub_categoryIDList, contractTypeName, companyNameList, companyIDList, exp_dateList;

    private String company_id, CustomerID;
    private TextView startdate_text;
    private EditText duration;
  //  private ArrayList<String> contractTypeName=new ArrayList<>();
    private final ArrayList<String> contractTypeIDList=new ArrayList<>();
    private String amc_type;
    private String contractID;

    public MyProduct() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_my_product, container, false);
        context = view.getContext();

        addproductForm = (RelativeLayout) view.findViewById(R.id.top_spinner);
        addProduct = (Button) view.findViewById(R.id.add_product);
        confirm = (Button) view.findViewById(R.id.confirm);
        cancel = (Button) view.findViewById(R.id.cancel);

        product_spinner = (Spinner) view.findViewById(R.id.product_spinner);
        sub_category_spinner = (Spinner) view.findViewById(R.id.sub_category_spinner);
        contract_type_spinner = (Spinner) view.findViewById(R.id.contract_type_spinner);

        serial_no = (EditText) view.findViewById(R.id.serial_number_input);
        model_no = (EditText) view.findViewById(R.id.model_number_input);
        model_no.setSelection(0);
        retailer_name = (EditText) view.findViewById(R.id.retailerName);
        purcharseDate = (TextView) view.findViewById(R.id.purchase_date_id);
        img = (ImageButton) view.findViewById(R.id.img);
        startdate_text = (TextView) view.findViewById(R.id.startdate_text);
        duration = (EditText) view.findViewById(R.id.duration);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyProfile.class);
                startActivity(intent);
            }
        });

        serial_no.setFilters(new InputFilter[]{ignoreFirstWhiteSpace()});
        model_no.setFilters(new InputFilter[]{ignoreFirstWhiteSpace()});
        retailer_name.setFilters(new InputFilter[]{ignoreFirstWhiteSpace()});

        serial_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serial_no.setFocusable(true);
            }
        });

        company_id = Pref_storage.getDetail(getContext(), "company_id");
        CustomerID = Pref_storage.getDetail(getContext(), "CustID");

        productNameList = new ArrayList<String>();
        productIDList = new ArrayList<String>();
        sub_categoryNameList = new ArrayList<String>();
        sub_categoryIDList = new ArrayList<String>();
        contractTypeName = new ArrayList<String>();
        companyNameList = new ArrayList<String>();
        companyIDList = new ArrayList<String>();
        exp_dateList = new ArrayList<String>();


        product_spinner.setOnItemSelectedListener(this);


        getContractType();

        startdate_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                                startdate_text.setText(frmdate);
                                startdate_text.setError(null);

                                Log.e("frmdate", "frmdate" + frmdate);


                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });

        purcharseDate.setOnClickListener(new View.OnClickListener() {
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

                                purcharseDate.setText(frmdate);
                                purcharseDate.setError(null);

                                Log.e("frmdate", "frmdate" + frmdate);


                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });


        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.setVisibility(View.GONE);
                addproductForm.setVisibility(View.VISIBLE);
                addProduct.setVisibility(view.GONE);
                getCompany();
                getProduct(companyID);
                /*getCategory(productID,companyID);*/
//                getContractType();

            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkValiadation();
                if (checkValiadation()) {

                    getDetails();

                    recyclerView.setVisibility(View.VISIBLE);
                    addproductForm.setVisibility(View.GONE);
                    addProduct.setVisibility(view.VISIBLE);
                }
            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.setVisibility(View.VISIBLE);
                addproductForm.setVisibility(View.GONE);
                addProduct.setVisibility(view.VISIBLE);
                model_no.setText("");
                serial_no.setText("");
                purcharseDate.setText("");
                retailer_name.setText("");
                model_no.setError(null);
                serial_no.setError(null);
                purcharseDate.setError(null);
                retailer_name.setError(null);
            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.product_list);
        LinearLayoutManager LayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        recyclerView.setLayoutManager(LayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        getProductsall();

        return view;
    }


    private void getContractType() {

        final ProgressDialog pDialog = new ProgressDialog(getActivity());
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

                                if (getActivity() != null) {
                                    ArrayAdapter<String> adp2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, contractTypeName);
                                    adp2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    contract_type_spinner.setAdapter(adp2);
                                    contract_type_spinner.setSelection(adp2.getPosition(contract_type));
                                 }
                                contract_type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                        if(contract_type_spinner.getSelectedItem().toString().equals("Select Contract type"))
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

                        Toast.makeText(getActivity(), error + "Something went wrong please try again later ! ", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("comp_id", company_id);

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
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }

    private boolean checkValiadation() {

        boolean valid = true;

        serial = serial_no.getText().toString();
        model = model_no.getText().toString();
        retailerName = retailer_name.getText().toString();


        if ((purcharseDate.getText().toString()).isEmpty()) {
            serial_no.setError("Please select purchase date");
            valid = false;
        } else {
            serial_no.setError(null);
        }

        if ((startdate_text.getText().toString()).isEmpty()) {
            startdate_text.setError("Please select Start date");
            valid = false;
        } else {
            startdate_text.setError(null);
        }

        if(contract_type_spinner.getSelectedItem().toString().equalsIgnoreCase("Select Contract type"))
        {
            Toast.makeText(context, "Please Select Contract type", Toast.LENGTH_SHORT).show();
            valid=false;
        }else
        {

        }

        if ((duration.getText().toString()).isEmpty()) {
            duration.setError("Please Enter duration");
            valid = false;
        } else {
            duration.setError(null);
        }


        if (serial.isEmpty()) {
            serial_no.setError("Please enter serial number");
            valid = false;
        } else {
            serial_no.setError(null);
        }

        if (retailerName.isEmpty()) {
            retailer_name.setError("Please enter retailer number");
            valid = false;
        } else {
            serial_no.setError(null);
        }

        if (model.isEmpty()) {
            model_no.setError("Please enter model number");
            valid = false;
        } else {
            model_no.setError(null);
        }

        return valid;
    }


    private void getProductsall() {

        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, API.LoadProduct,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            pDialog.dismiss();

                            JSONObject jobj = new JSONObject(response);
                            int status = jobj.getInt("status");

                            if (status == 1) {

                                JSONArray jaryproduct = jobj.getJSONArray("result");
                                ProductArrayList.clear();
                                for (int i = 0; i < jaryproduct.length(); i++) {
                                    product = new Product();
                                    JSONObject restatusobj = jaryproduct.getJSONObject(i);
                                    product_name = restatusobj.getString("product");
                                    category = restatusobj.getString("category");
                                    model = restatusobj.getString("model");
                                    serial = restatusobj.getString("serial");
                                    contract_type = restatusobj.getString("contract_type");
                                    expiry_date = restatusobj.getString("expiry_date");
                                    product.setProduct(product_name);
                                    product.setSub_category(category);
                                    product.setModel_no(model);
                                    product.setContract_type(contract_type);
                                    product.setSerial_number(serial);
                                    ProductArrayList.add(product);
                                }
                                if (ProductArrayList != null) {
                                    productadapter = new ProductAdapter(ProductArrayList, context);
                                    recyclerView.setHasFixedSize(true);
                                    recyclerView.setAdapter(productadapter);
                                    productadapter.notifyDataSetChanged();
                                }
                            }
                            else {

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
                map.put(KEY_cust_id, CustomerID);
                Log.e("test", "test" + map);
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


//    private void getContractType(){
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, API.ContractType,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//                        try {
//
//                            JSONObject jobj = new JSONObject(response);
//                            int status = jobj.getInt("status");
//
//                            if(status == 1){
//                                JSONArray jaryproduct = jobj.getJSONArray("result");
//                                for (int i = 0;i<jaryproduct.length();i++){
//                                    JSONObject restatusobj = jaryproduct.getJSONObject(i);
//
//                                    contract_typeName = restatusobj.getString("amc_type");
//                                    contract_typeID = restatusobj.getString("id");
//                                }
//                            }
//                            if(getActivity()!=null) {
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                        Toast.makeText(getActivity(), error+ "Something went wrong please try again later ! ", Toast.LENGTH_SHORT).show();
//                    }
//                })
//        {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> map = new HashMap<String, String>();
//                return map;
//            }
//        };
//
//        stringRequest.setRetryPolicy(new RetryPolicy() {
//            @Override
//            public int getCurrentTimeout() {
//                return 50000;
//            }
//
//            @Override
//            public int getCurrentRetryCount() {
//                return 50000;
//            }
//
//            @Override
//            public void retry(VolleyError error) throws VolleyError {
//
//            }
//        });
//        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
//        requestQueue.add(stringRequest);
//
//
//    }


    private void getCompany() {

        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, API.LoadCompany_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            pDialog.dismiss();

                            JSONArray jary = new JSONArray(response);


                            for (int i = 0; i < jary.length(); i++) {

                                JSONObject restatusobj = jary.getJSONObject(i);
                                companyID = restatusobj.getString("company_id");
                                companyName = restatusobj.getString("company_name");
                                companyNameList.add(companyName);
                                companyIDList.add(companyID);
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


    private void getProduct(final String companyID) {

        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.show();

        StringRequest sr = new StringRequest(Request.Method.POST, API.LoadProduct_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            pDialog.dismiss();

                            JSONObject jobj = new JSONObject(response);
                            int status = jobj.getInt("status");

                            if (status == 1) {
                                JSONArray jaryproduct = jobj.getJSONArray("result");
                                productNameList.clear();
                                for (int i = 0; i < jaryproduct.length(); i++) {
                                    JSONObject jproduct = jaryproduct.getJSONObject(i);
                                    productName = jproduct.getString("product_name");
                                    productID = jproduct.getString("product_id");
                                    productNameList.add(productName);
                                    productIDList.add(productID);

                                }
                                if (getActivity() != null) {
                                    ArrayAdapter<String> adp2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, productNameList);
                                    adp2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    product_spinner.setAdapter(adp2);
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
                Log.e("test", "test" + map);

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
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(sr);

    }


    private void getCategory(final String productID, final String companyID) {

//        final ProgressDialog pDialog = new ProgressDialog(getActivity());
//        pDialog.setMessage("Loading...");
//        pDialog.show();

        StringRequest sr = new StringRequest(Request.Method.POST, API.LoadCategory_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {

//                            pDialog.dismiss();

                            /*Toast.makeText(getActivity(), "cat"+response, Toast.LENGTH_SHORT).show();*/

                            JSONObject jobj1 = new JSONObject(response);
                            int status = jobj1.getInt("status");

                            if (status == 1) {

                                JSONArray jarycat = jobj1.getJSONArray("result");
                                sub_categoryNameList.clear();
                                for (int i = 0; i < jarycat.length(); i++) {
                                    JSONObject jcat = jarycat.getJSONObject(i);
                                    categoryName = jcat.getString("cat_name");
                                    categoryID = jcat.getString("cat_id");
                                    sub_categoryNameList.add(categoryName);
                                    sub_categoryIDList.add(categoryID);

                                }
                                if (getActivity() != null) {
                                    ArrayAdapter<String> adp3 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, sub_categoryNameList);
                                    /*Toast.makeText(Registration.this, "category" + categoryNameList, Toast.LENGTH_LONG).show();*/
                                    adp3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    sub_category_spinner.setAdapter(adp3);
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
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(sr);

    }


    private void getDetails() {

        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, API.AddProductDetails,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Toast.makeText(getActivity(), "API Hit"+response, Toast.LENGTH_SHORT).show();
                        try {
                            pDialog.dismiss();

                            JSONObject jobj = new JSONObject(response);
                            int status = jobj.getInt("status");

                            if (status == 1) {

                                final AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                                builder1.setTitle("Success");
                                builder1.setMessage("Added successfully");
                                builder1.setCancelable(true);

                                builder1.setPositiveButton(
                                        "ok",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                getProductsall();
                                                serial_no.setText("");
                                                model_no.setText("");
                                                retailer_name.setText("");
                                                purcharseDate.setText("");
                                            }
                                        });

                                AlertDialog alert11 = builder1.create();
                                alert11.show();

                            }
                            else{
                                Toast.makeText(getContext(), "Failure", Toast.LENGTH_SHORT).show();
                            }

                            } catch(JSONException e){
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
                map.put("cat_id", categoryID);
                map.put("model_no", model_no.getText().toString());
                map.put("serial_no", serial_no.getText().toString());
                map.put("contract_type", typeofcontract);
                map.put("purchase_date", purcharseDate.getText().toString());
                map.put("retailer_name", retailer_name.getText().toString());
                map.put("start_date", startdate_text.getText().toString());
                map.put("contract_duration", duration.getText().toString());
                Log.e("test", "test" + map);
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

        switch (spinner.getId()) {

            case R.id.product_spinner:
                productID = productIDList.get(i);
                getCategory(productID, companyID);
                break;
            case R.id.category_spinner:
                categoryID = sub_categoryIDList.get(i);
                break;

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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

    @Override
    public void onBackStackChanged() {
        Intent intent = new Intent(getContext(),Home.class);
        startActivity(intent);
    }
}