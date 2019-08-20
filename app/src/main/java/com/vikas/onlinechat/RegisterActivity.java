package com.vikas.onlinechat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.client.Firebase;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {
EditText username,password;
Button btnregister;
TextView login;
String user,pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        btnregister=findViewById(R.id.btnregister);
        login=findViewById(R.id.login);
        Firebase.setAndroidContext(this);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);

            }
        });
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user=username.getText().toString();
                pass=password.getText().toString();

                String url= "https://chatapp-2429a.firebaseio.com/users.json";
//                    Stringrequest method to send data to the api
                StringRequest stringRequest=new StringRequest(0, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Firebase reference = new Firebase("https://chatapp-2429a.firebaseio.com/users");
                        try {
                            JSONObject obj = new JSONObject(response);
//
//                                    if user is not present then create new user
                            if (!obj.has(user)) {
                                reference.child(user).child("password").setValue(pass); //firebase send data
                                Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                                startActivity(intent);

                                //Toast.makeText(RegisterActivity.this, "registration successful", Toast.LENGTH_LONG).show();
//                                        check if user is already present then make toast
                            } else {
                                Toast.makeText(RegisterActivity.this, "username already exists", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                RequestQueue requestQueue= Volley.newRequestQueue(RegisterActivity.this);
                requestQueue.add(stringRequest);

            }
        });
    }

}
