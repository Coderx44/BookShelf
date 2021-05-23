package com.mitadt.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**

 * Use the  factory method to
 * create an instance of this fragment.
 */
public class Sign_Up_2 extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    private EditText emailid;
    private EditText password;
    private EditText confirm;
    private Button signup;
    private TextView login;
    private String fname, lname;
    private RequestQueue requestQueue;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_sign_up_2, container, false);
        emailid = (EditText) root.findViewById(R.id.emailid);
        password = (EditText) root.findViewById(R.id.password);
        confirm = (EditText) root.findViewById(R.id.confirm);
        signup = (Button) root.findViewById((R.id.signup));
        login = (TextView) root.findViewById((R.id.loginlink1));
        fname = getArguments().getString("fname");
        lname = getArguments().getString("lname");
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (emailid.getText().toString().length() > 0 && password.getText().toString().length() > 0 && confirm.getText().toString().length() > 0) {
                    if (password.getText().toString().equals(confirm.getText().toString())) {
                        Toast.makeText(getActivity(), "Account created!", Toast.LENGTH_SHORT).show();
                        postRequest(fname.toString(), lname.toString(), emailid.toString(), password.toString());
                    } else {
                        Toast.makeText(getActivity(), "Password don't match!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Enter details", Toast.LENGTH_SHORT).show();
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogIn fragment  = new LogIn();
                FragmentManager fragmentManager = getFragmentManager();
                int count = fragmentManager.getBackStackEntryCount();
                for(int i = 0; i < count; ++i) {
                    fragmentManager.popBackStack();
                }
                fragmentManager.beginTransaction()
                        .replace(R.id.c3, fragment, null)
                        .commit();
            }
        });


        return root;
    }
    private void postRequest(String fname, String lname, String email, String password) {

        requestQueue = Volley.newRequestQueue(getActivity());
        String url = "https://f7ff59931006.ngrok.io/api/signin";
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("res", response);
                Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("err", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("fname", fname);
                params.put("lname", lname);
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };
        requestQueue.add(sr);
    }
}