package com.mitadt.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.accessibilityservice.AccessibilityService;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

public class HomePage extends AppCompatActivity {
    private String user;
    private TextView txt;
    private TextView Login;
    private TextView logout;
    private TextView api;
    private RequestQueue requestQueue;
    private Button button_sign_out;
    GoogleSignInClient mGoogleSignInClient;
    private static final int CAMERA_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        txt = (TextView) findViewById(R.id.textView2);
        Login = (TextView) findViewById(R.id.textView5);
        logout = (TextView) findViewById(R.id.logout);
        api = (TextView) findViewById(R.id.api);
        button_sign_out = (Button)findViewById(R.id.button_sign_out);
        isconnected(this);
        if(isconnected(this))
        {
            Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
        }
        else Toast.makeText(this, "Not connected", Toast.LENGTH_SHORT).show();
//        Log.d("hi",user);
//        check permission
//        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
//        {
//            if(getApplicationContext().checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(HomePage.this, "Permission Granted",Toast.LENGTH_SHORT).show();
////                dispatchTakePictureIntent();
//            }
//            else{
////
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE );
//            }
//        }
//        else
//        {
//            Toast.makeText(HomePage.this, "Permission granted",Toast.LENGTH_SHORT).show();
//        }


        SharedPreferences sh = getSharedPreferences("User", MODE_PRIVATE);
        SharedPreferences.Editor editor = sh.edit();
        String User = sh.getString("User", "Not logged in");
        if (User != "Not logged in") {
            //do here
//            user = getIntent().getStringExtra("User");
            txt.setText(User);
        } else {
            txt.setText("Hello");
        }
        if (User != "Not logged in") {
            Login.setVisibility(View.GONE);
        }
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.remove("User");
                editor.apply();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });

        ImageView imageView = (ImageView) findViewById(R.id.my_image_view);
        if(sh.getString("pic", "Not logged in")!="Not logged in") {
            Glide.with(this).load(sh.getString("pic", "Not logged in")).into(imageView);
        }
        else imageView.setVisibility(View.GONE);
        String url = "https://08ebbaaef6c3.ngrok.io/api/users?email=bhosaleprasad849@gmail.com";
        requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Toast.makeText(HomePage.this, response.toString(), Toast.LENGTH_SHORT).show();
                            api.setText(response.getString("Firstname"));
                        } catch (JSONException e) {
                            Log.d("email","Not received");
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d("email","Not received");
                    }
                });

// Access the RequestQueue through your singleton class.
        requestQueue.add(jsonObjectRequest);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account==null) {
            Log.d("signed in","NO");
            button_sign_out.setVisibility(View.GONE);
        }
        if(account!=null || User=="Not logged in")
            logout.setVisibility(View.GONE);
        button_sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    // ...
                    case R.id.button_sign_out:
                        signOut();
                        break;
                    // ...
                }
            }
        });
//
//    @Override
//    public void onBackPressed() {
//        // super.onBackPressed(); commented this line in order to disable back press
//        //Write your code here
//        if (getCallingActivity()!=null) {
//            finishAffinity();
//            finish();
//        } else
//            super.onBackPressed();
////        finishAffinity();
////        finish();
//    }
    }
    private void signOut() {
        SharedPreferences sh = getSharedPreferences("User", MODE_PRIVATE);
        SharedPreferences.Editor editor = sh.edit();
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                        editor.remove("User");
                        editor.remove("pic");
                        editor.apply();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
    }
    private void revokeAccess() {
        mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
    }
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
            // display error state to the user
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == CAMERA_PERMISSION_CODE)
        {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
//                dispatchTakePictureIntent();
            }
            else{
                Toast.makeText(HomePage.this,"DENIED",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static boolean isconnected(Context context)
    {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if(!isConnected)
        {
            return false;
        }
        else return true;
    }

}

