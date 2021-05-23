 package com.mitadt.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

import static com.mitadt.myapplication.HomePage.isconnected;


 public class MainActivity extends AppCompatActivity {
//    private EditText etUsername;
//    private EditText etPassword;
//    private Button btLogin;
//    private TextView create;
//    GoogleSignInClient mGoogleSignInClient;
//     private RequestQueue requestQueue;
//    private static int  RC_SIGN_IN=101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LogIn fragment = new LogIn();
        getSupportFragmentManager().beginTransaction().add(R.id.c3, fragment, null).commit();
//        etUsername = (EditText)findViewById(R.id.editTextTextPersonName2);
//        etPassword = (EditText)findViewById(R.id.editTextTextPersonName3);
//        btLogin = (Button)findViewById(R.id.button);
//        create = (TextView)findViewById(R.id.signin);
//        SharedPreferences sh = getSharedPreferences("User",MODE_PRIVATE);
//        SharedPreferences.Editor editor = sh.edit();
//        // Set the dimensions of the sign-in button.
//        SignInButton signInButton = findViewById(R.id.sign_in_button);
//        signInButton.setSize(SignInButton.SIZE_STANDARD);
//
//        btLogin.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View view)
//            {
//                if(isconnected(MainActivity.this)) {
//                    if (etUsername.getText().toString().length() > 0 && etPassword.getText().toString().length() > 0) {
//                        Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
//                        editor.putString("User", etUsername.getText().toString());
//                        editor.apply();
//                        postRequest(etUsername.getText().toString(), etPassword.getText().toString());
//                        Intent intent = new Intent(MainActivity.this, HomePage.class);
//                        intent.putExtra("User", etUsername.getText().toString());
//                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(intent);
//                    } else {
//                        Toast.makeText(MainActivity.this, "Enter details", Toast.LENGTH_SHORT).show();
//                    }
//                }
//                else
//                    Toast.makeText(MainActivity.this, "Not connected", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        create.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (isconnected(MainActivity.this)) {
//                    Intent intent = new Intent(MainActivity.this, SignIn.class);
//                    startActivity(intent);
////                    Sign_Up fragment = new Sign_Up();
////                        getSupportFragmentManager().beginTransaction()
////                                .setReorderingAllowed(true)
////                                .add(R.id.constrain, fragment, null)
////                                .commit();
//
//                }
//                else Toast.makeText(MainActivity.this, "Not connected", Toast.LENGTH_SHORT).show();
//            }
//
//
//        });
//
//        // Configure sign-in to request the user's ID, email address, and basic
//// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestEmail()
//                .build();
//
//        // Build a GoogleSignInClient with the options specified by gso.
//        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
//        // Check for existing Google Sign In account, if the user is already signed in
// the GoogleSignInAccount will be non-null.
//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
//        updateUI(account);
//        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (isconnected(MainActivity.this)) {
//                    switch (v.getId()) {
//                        case R.id.sign_in_button:
//                            signIn();
//                            break;
//                        // ...
//                    }
//                }
//                else Toast.makeText(MainActivity.this, "Not connected", Toast.LENGTH_SHORT).show();
//            }
//        });
//
    }
//    private void signIn() {
//        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
//        startActivityForResult(signInIntent, RC_SIGN_IN);
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
//        if (requestCode == RC_SIGN_IN) {
//            // The Task returned from this call is always completed, no need to attach
//            // a listener.
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            handleSignInResult(task);
//        }
//    }
//
//    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
//        try {
//            SharedPreferences sh = getSharedPreferences("User",MODE_PRIVATE);
//            SharedPreferences.Editor editor = sh.edit();
//            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
//            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
//            if (acct != null) {
//                String personName = acct.getDisplayName();
//                String personGivenName = acct.getGivenName();
//                String personFamilyName = acct.getFamilyName();
//                String personEmail = acct.getEmail();
//                String personId = acct.getId();
//                Uri personPhoto = acct.getPhotoUrl();
//                Intent intent = new Intent(MainActivity.this, HomePage.class);
//                editor.putString("User", personName);
//                if(personPhoto != null)
//                editor.putString("pic",personPhoto.toString());
//                editor.apply();
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//            }
//            // Signed in successfully, show authenticated UI.
////            updateUI(account);
//        } catch (ApiException e) {
//            // The ApiException status code indicates the detailed failure reason.
//            // Please refer to the GoogleSignInStatusCodes class reference for more information.
//            Log.d("error", "signInResult:failed code=" + e.getStatusCode());
////            updateUI(null);
//        }
//    }
//
//     private void postRequest(String email, String password) {
//
//         requestQueue = Volley.newRequestQueue(this);
//         String url = "https://f7ff59931006.ngrok.io/api/login";
//         StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//             @Override
//             public void onResponse(String response) {
//                 Log.d("res", response);
//                 Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
//             }
//         }, new Response.ErrorListener() {
//             @Override
//             public void onErrorResponse(VolleyError error) {
//                 Log.e("err", error.toString());
//             }
//         }) {
//             @Override
//             protected Map<String, String> getParams() {
//                 Map<String, String> params = new HashMap<String, String>();
//                 params.put("email", email);
//                 params.put("password", password);
//                 return params;
//             }
//         };
//         requestQueue.add(sr);
//     }
}