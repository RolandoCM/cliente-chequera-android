package com.gfi.rolando.chequera;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {

    private EditText etMail;
    private EditText etPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final RequestQueue queue = Volley.newRequestQueue(this);
        etMail = findViewById(R.id.etMail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!camposVacios())
                    login();
                else
                    Toast.makeText(LoginActivity.this, "llenar campos",Toast.LENGTH_SHORT).show();
            }
        });


    }
    public void login (){
        String pass = etPassword.getText().toString();
        String mail = etMail.getText().toString();
        HashMap<String,String> map = new HashMap<>();
        map.put("mail", mail);
        map.put("password", pass);
        JSONObject params = new JSONObject(map);
        VolleySingleton.getInstance(LoginActivity.this).addToRequestQueue(
                new JsonObjectRequest(
                        Request.Method.POST,
                        Api.LOGIN,
                        params,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                procesarRespuesta(response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("Error volley", error.getMessage());
                            }
                        }
                )
                {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<>();
                        headers.put("Content-Type", "application/json; charset=utf-8");
                        headers.put("Accept", "application/json");
                        return headers;
                    }

                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8"+getParamsEncoding();
                    }
                }
        );
    }
    public void procesarRespuesta(JSONObject response){
        try{
            String name = response.getString("name");
            String mail = response.getString("mail");
            if(!mail.isEmpty()){
                Toast.makeText(LoginActivity.this,"Bienvenido "+response.getString("name"),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, NavActivity.class);
                intent.putExtra("mail",mail);
                intent.putExtra("name",name);
                startActivity(intent);
                this.finish();
            }
            else{
                Toast.makeText(LoginActivity.this, "Intente nuevamente",Toast.LENGTH_SHORT).show();
            }
        }catch(JSONException e){
            Log.d("Error", e.getMessage());
        }
    }
    public boolean camposVacios(){
        String mail = etMail.getText().toString();
        String pass = etPassword.getText().toString();

        return (mail.isEmpty()|| pass.isEmpty());
    }
}
