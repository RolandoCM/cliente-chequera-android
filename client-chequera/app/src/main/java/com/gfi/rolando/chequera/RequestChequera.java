package com.gfi.rolando.chequera;


import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class RequestChequera  extends JsonObjectRequest{

    private final static String URL = "http://192.168.100.16:8080/post";
    private JSONObject params;

    public RequestChequera( String requestBody, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener, JSONObject params) {
        super(Method.POST, URL, requestBody, listener, errorListener);
        this.params = params;
    }
}
