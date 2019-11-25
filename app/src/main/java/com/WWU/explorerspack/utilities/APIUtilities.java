package com.WWU.explorerspack.utilities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.WWU.explorerspack.ui.logs.hiking_maps.MapContent.MapListContent;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class APIUtilities extends AsyncTask<String, Integer, ArrayList<MapListContent.MapListItem>> {
    private ProgressDialog p;
    private String JSONResultString;
    private ArrayList<MapListContent.MapListItem> resultList;
    public AsyncResponse delegate = null;
    private Context mContext;

    public APIUtilities (Context context){
        mContext = context;
    }

    @Override
    protected ArrayList<MapListContent.MapListItem> doInBackground(String... queryString) {
        RequestQueue queue = Volley.newRequestQueue(mContext);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, queryString[0],
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        JSONResultString = response;
                        //TODO:
                        //time to build the list string.
                        //call a method in the context?

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(mContext,
                            "ERROR: Response Timeout or no connection to database",
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(mContext,
                            "ERROR: Authentication failure",
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(mContext,
                            "ERROR: Server Response issue",
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(mContext,
                            "ERROR: Response Timeout or no connection to a network",
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(mContext,
                            "ERROR: query parse error",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        super.onPreExecute();
        p = new ProgressDialog(mContext);
        p.setMessage("Please wait...It is downloading");
        p.setIndeterminate(false);
        p.setCancelable(false);
        p.show();
    }

    @Override
    protected void onPostExecute(ArrayList<MapListContent.MapListItem> queryResultList){
        super.onPostExecute(queryResultList);
        delegate.processFinish(queryResultList);
        mContext = null;
    }

    public ArrayList<MapListContent.MapListItem> getResultList(){
        return resultList;
    }

    public interface AsyncResponse {
        void processFinish(ArrayList<MapListContent.MapListItem> resultList);
    }

}
