package charlesjamnajim.micropol;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView txtWelcome;
    private FirebaseAuth auth;
    private final int RC_SIGN_IN = 0;
    public  String lol;

    @Override
    public void onBackPressed() {
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtWelcome = (TextView)findViewById(R.id.txtWelcome);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            lol= auth.getCurrentUser().getDisplayName();
            txtWelcome.setText(lol);
        }else {
            startActivityForResult(AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setProviders(
                            AuthUI.FACEBOOK_PROVIDER,
                            AuthUI.GOOGLE_PROVIDER
                    )
                    .setIsSmartLockEnabled(false)
                    .setTheme(R.style.GreenTheme)
                    .build(), RC_SIGN_IN);
        }



        findViewById(R.id.btnLogout).setOnClickListener(this);
        findViewById(R.id.btnContinue).setOnClickListener(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN){

            if (resultCode == RESULT_OK){
               finish();
                startActivity(getIntent());
                //user authenticated
               /* txtWelcome = (TextView)findViewById(R.id.txtWelcome);
                txtWelcome.setText("Welcome " + auth.getCurrentUser().getDisplayName());*/

            }else{
                //user not authenticated


            }
        }

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnLogout){
                AuthUI.getInstance()
                        .signOut(this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                startActivity(new Intent(MainActivity.this,MainActivity.class));
                                finish();
                            }
                        });

        }else if (view.getId() == R.id.btnContinue){
            startActivity(new Intent(MainActivity.this,ThePollActivity.class));


        }
    }
}







/*        private GoogleApiClient mGoogleApiClient;
        private FirebaseAuth mAuth;
        private FirebaseAuth.AuthStateListener mAuthListner;
        private static final int RC_SIGN_IN = 0;


        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == RC_SIGN_IN){
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

                if (result.isSuccess()){
                    GoogleSignInAccount account = result.getSignInAccount();
                    firebaseAuthWithGoogle(account);
                }
                else{
                    Log.d("TAG","login failed");
                }

            }

        }

    private void firebaseAuthWithGoogle(GoogleSignInAccount accnt) {
        AuthCredential credential = GoogleAuthProvider.getCredential(accnt.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("AUTH", "sign in credential:complete" + task.isSuccessful());
                    }
                });
    }*/


        //Code for Googlesign


        /*        mAuth = FirebaseAuth.getInstance();
        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    Log.d("AUTH", user.getEmail());
                }
                else{
                    Log.d("AUTH","Logged out");
                }

            }
        };


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                    .requestIdToken(getString(R.string.default_web_client_id))
                                    .requestEmail()
                                    .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        findViewById(R.id.sign_in_button).setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListner);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuth != null){
            mAuth.removeAuthStateListener(mAuthListner);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d("Auth", "connection Failed");

    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.sign_in_button:
                signIn();
                break;
        }
    }

    private void signIn() {

        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }*/

