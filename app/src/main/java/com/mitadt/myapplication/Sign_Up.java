package com.mitadt.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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

import static com.mitadt.myapplication.HomePage.isconnected;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
 public class Sign_Up extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    public Sign_Up() {
//        // Required empty public constructor
////        super(R.layout.fragment_sign__up);
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
////     * @param param1 Parameter 1.
////     * @param param2 Parameter 2.
//     * @return A new instance of fragment Sign_In.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static Sign_Up newInstance() {
//        Sign_Up fragment = new Sign_Up();
////        Bundle args = new Bundle();
////        args.putString(ARG_PARAM1, param1);
////        args.putString(ARG_PARAM2, param2);
////        fragment.setArguments(args);
//        return fragment;
//    }

    private EditText fname;
    private EditText lname;
    private TextView login;
    Button next;
    private RequestQueue requestQueue;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.activity_sign_in, container, false);

        fname = (EditText) root.findViewById(R.id.fName);
        lname = (EditText) root.findViewById(R.id.lName);
        next = (Button) root.findViewById(R.id.next);
        login = (TextView) root.findViewById(R.id.loginlink);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fname.getText().toString().length() > 0 && lname.getText().toString().length() > 0 ) {
                    Sign_Up_2 fragment = new Sign_Up_2();
                    Bundle args = new Bundle();
                    args.putString("fname", fname.getText().toString());
                    args.putString("lname", lname.getText().toString());
                    fragment.setArguments(args);
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.c3, fragment, null).addToBackStack(null)
                            .commit();
                } else {
                    Toast.makeText(getActivity(), "Enter details", Toast.LENGTH_SHORT).show();
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isconnected(getActivity())) {

                    LogIn fragment2 = new LogIn();
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.popBackStackImmediate();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.c3, fragment2);
                    fragmentTransaction.commit();

                } else Toast.makeText(getActivity(), "Not connected", Toast.LENGTH_SHORT).show();
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

