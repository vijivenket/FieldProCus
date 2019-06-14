package com.capricot.fieldprocustomer.FragmentHome;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.capricot.fieldprocustomer.Adapters.TicketHistoryAdapter;
import com.capricot.fieldprocustomer.Bean.Ticket;
import com.capricot.fieldprocustomer.Pref_storage;
import com.capricot.fieldprocustomer.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by BalajiPrabhu on 9/13/2017.
 */

public class TicketHistory extends Fragment {

    private TicketHistoryAdapter ticketHistoryAdapter;
     static Context context;
    private RecyclerView recyclerView;
    private List<Ticket> ticketList = new ArrayList<>();
    private String ticketId;
    private String techName;
    private String productName;
    private String catName;
    private String contactNo;
    public String doorNo;
    public String street;
    public String town;
    public String city;
    public String state;
    public String country;
    public String landmark;
    public String pincode;
    public String probDesc;
    public String callCategory;
    public String serviceCategory;
    public String currentStatus;
    public String custPreferenceDate;
    public String raisedTime;
    public String totalAmount;
    public String billNo;
    public String ticketStartTime;
    public String ticketEndTime;
    private final String KEY_CompanyID = "company_id";
    private String KEY_ProductID = "product_id";
    private final String KEY_cust_id = "cust_id";

    private Ticket ticket;

    private final ArrayList<Ticket> ticketArrayList = new ArrayList<>();

    private String company_id,CustomerID;


    private LinearLayoutManager llm;
    public TicketHistory() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_ticket_history, container, false);

        company_id = Pref_storage.getDetail(getContext(),"company_id");
        CustomerID = Pref_storage.getDetail(getContext(),"CustID");

        recyclerView = (RecyclerView) view.findViewById(R.id.ticket_history);
        recyclerView.setHasFixedSize(true);
        llm = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(llm);
        getTicketDetails();
        return view;

    }


    private void getTicketDetails() {

        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, API.LoadTickets,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Toast.makeText(getActivity(), "API Hit"+response, Toast.LENGTH_SHORT).show();

                        try {

                            pDialog.dismiss();

                            JSONObject jobj = new JSONObject(response);
                            int status = jobj.getInt("status");

                            if(status == 1) {


                                JSONArray jaryproduct = jobj.getJSONArray("result");
                                ticketArrayList.clear();
                                for (int i = 0; i < jaryproduct.length(); i++) {
                                    ticket = new Ticket();
                                    JSONObject restatusobj = jaryproduct.getJSONObject(i);
                                    ticketId = restatusobj.getString("ticket_id");
                                    Log.e("eeef","fefe"+ticketId);
                                    techName = restatusobj.getString("tech_name");
                                    contactNo = restatusobj.getString("contact_no");
                                    productName = restatusobj.getString("product_name");
                                    catName = restatusobj.getString("cat_name");

                                    ticket.setTicketId(ticketId);
                                    ticket.setTechName(techName);
                                    ticket.setContactNo(contactNo);
                                    ticket.setProductName(productName);
                                    ticket.setCatName(catName);
                                    ticketArrayList.add(ticket);


                                }
                                ticketHistoryAdapter = new TicketHistoryAdapter(ticketArrayList,getActivity());
                                recyclerView.setItemAnimator(new DefaultItemAnimator());
                                recyclerView.setAdapter(ticketHistoryAdapter);
                                ticketHistoryAdapter.notifyDataSetChanged();


                            }
                            else
                            {

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
                Log.e("test","test"+map);
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
