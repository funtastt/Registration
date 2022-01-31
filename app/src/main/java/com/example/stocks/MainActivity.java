package com.example.stocks;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private static final int SIGN_IN_CODE = 1;
    TextView text;
    Button button;
    SignInButton mSignInButton;
    GoogleSignInClient mGoogleSignInClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("558262318409-ssodom6fmpgfb5r6h63o9q81ktl8orto.apps.googleusercontent.com")
                .requestEmail()
                .build();
        Log.d("Error", "hello");

        try {
            mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        } catch (Exception e) {
            Log.e("Error", "e", e);
        }

        text = findViewById(R.id.text);
        button = findViewById(R.id.button);
        mSignInButton = findViewById(R.id.sign_in_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "hello", Toast.LENGTH_SHORT).show();
                connectToDatabase();
            }
        });

        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, SIGN_IN_CODE);
    }

    @Override
    protected void onStart() {
        super.onStart();

        GoogleSignInAccount account = null;
        account = GoogleSignIn.getLastSignedInAccount(MainActivity.this);
        if (account != null) Toast.makeText(this, "roe[gerogk[gv", Toast.LENGTH_SHORT).show();
    }

    private void connectToDatabase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://stocks-95f7e-default-rtdb.europe-west1.firebasedatabase.app//");
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SIGN_IN_CODE) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> task) {
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            Toast.makeText(this, "Congrats!", Toast.LENGTH_SHORT).show();
        } catch (ApiException e) {
            e.printStackTrace();
            Log.w("Error", "signInResult:failed code=" + e.getStatusCode());
        }
    }
}