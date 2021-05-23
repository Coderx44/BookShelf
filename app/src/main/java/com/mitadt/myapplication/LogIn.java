package com.mitadt.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;
import static com.mitadt.myapplication.HomePage.isconnected;

/**
 * A simple {@link Fragment} subclass.
// * Use the {@link LogIn#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LogIn extends Fragment {



//public LogIn LogIn()
//{
//    LogIn fragment = new LogIn();
//
//    return fragment;
//}
//
//    // TODO: Rename and change types and number of parameters
    public static LogIn newInstance() {
        LogIn fragment = new LogIn();

        return fragment;
    }
    private EditText etUsername;
    private EditText etPassword;
    private Button btLogin;
    private TextView create;
    GoogleSignInClient mGoogleSignInClient;
    private RequestQueue requestQueue;
    private static int  RC_SIGN_IN=101;
    LayoutInflater inflater;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_log_in, container, false);
        etUsername = (EditText) root.findViewById(R.id.editTextTextPersonName2);
        etPassword = (EditText) root.findViewById(R.id.editTextTextPersonName3);
        btLogin = (Button) root.findViewById(R.id.button);
        create = (TextView) root.findViewById(R.id.signin);
        SharedPreferences sh = this.getActivity().getSharedPreferences("User",MODE_PRIVATE);
        SharedPreferences.Editor editor = sh.edit();
        // Set the dimensions of the sign-in button.
        SignInButton signInButton = root.findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        btLogin.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view)
            {
                if(isconnected(getActivity())) {
                    if (etUsername.getText().toString().length() > 0 && etPassword.getText().toString().length() > 0) {
                        Toast.makeText(getActivity(), "Login Success", Toast.LENGTH_SHORT).show();
                        editor.putString("User", etUsername.getText().toString());
                        editor.apply();
                        postRequest(etUsername.getText().toString(), etPassword.getText().toString());
                        Intent intent = new Intent(getActivity(), HomepageFinal.class);
                        intent.putExtra("User", etUsername.getText().toString());
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getActivity(), "Enter details", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                    Toast.makeText(getActivity(), "Not connected", Toast.LENGTH_SHORT).show();
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isconnected(getActivity())) {
                    Sign_Up fragment2 = new Sign_Up();
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.c3, fragment2).addToBackStack(fragment2.getClass().getName());
                    fragmentTransaction.commit();

                }
                else Toast.makeText(getActivity(), "Not connected", Toast.LENGTH_SHORT).show();
            }


        });

        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
        // Check for existing Google Sign In account, if the user is already signed in
// the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getActivity());
//        updateUI(account);
        root.findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isconnected(getActivity())) {
                    switch (v.getId()) {
                        case R.id.sign_in_button:
                            signIn();
                            break;
                        // ...
                    }
                }
                else Toast.makeText(getActivity(), "Not connected", Toast.LENGTH_SHORT).show();
            }
        });
        return root;
    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            SharedPreferences sh = this.getActivity().getSharedPreferences("User",MODE_PRIVATE);
            SharedPreferences.Editor editor = sh.edit();
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getActivity());
            if (acct != null) {
                String personName = acct.getDisplayName();
                String personGivenName = acct.getGivenName();
                String personFamilyName = acct.getFamilyName();
                String personEmail = acct.getEmail();
                String personId = acct.getId();
                Uri personPhoto = acct.getPhotoUrl();
                Intent intent = new Intent(this.getActivity(), HomePage.class);
                editor.putString("User", personName);
                if(personPhoto != null)
                    editor.putString("pic",personPhoto.toString());
                editor.apply();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
            // Signed in successfully, show authenticated UI.
//            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.d("error", "signInResult:failed code=" + e.getStatusCode());
//            updateUI(null);
        }
    }

    private void postRequest(String email, String password) {

        requestQueue = Volley.newRequestQueue(getActivity());
        String url = "https://f7ff59931006.ngrok.io/api/login";
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("res", response);
                Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
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
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };
        requestQueue.add(sr);
    }
}