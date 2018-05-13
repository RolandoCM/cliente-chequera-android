package com.gfi.rolando.chequera;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonWriter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText etMail;
    private EditText etPassword;
    private Button btnLogin;
    private RequestChequera request;
    private Map<String, String> params;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etMail = findViewById(R.id.etMail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        final RequestQueue queue = Volley.newRequestQueue(this);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pass = etPassword.getText().toString();
                String mail = etMail.getText().toString();
                JSONObject obj = new JSONObject();
                try {
                    obj.put("mail",mail);
                    obj.put("password",pass);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String url = "http://192.168.100.16:8080/post";
                JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, url,
                        obj,
                        new Response.Listener<JSONObject>()
                        {
                            @Override
                            public void onResponse(JSONObject response) {
                                // display response
                                Log.d("Response", response.toString());
                            }
                        },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("Error.Response", "");
                            }
                        }

                );

// add it to the RequestQueue
                queue.add(getRequest);
                /*JSONObject obj = new JSONObject();
                //Toast.makeText(LoginActivity.this, "check", Toast.LENGTH_SHORT).show();
                Login login = new Login();
                login.setMail(mail);
                login.setPassword(pass);
                try {
                    obj.put("mail",mail);
                    obj.put("password",pass);
                    Toast.makeText(LoginActivity.this, obj.get("mail").toString(),Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                request = new RequestChequera("", new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {

                    }
                },null,obj);
                queue.add(request);*/
            }
        });

    }
}
