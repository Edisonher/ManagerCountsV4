package com.example.jacobo.managercounts;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.Arrays;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private static final int SIGN_IN_CODE = 777;
    private LoginButton loginButton;                //Facebook
    private CallbackManager callbackManager;        //Facebook
    private FirebaseAuth firebaseAuth;              //Facebook
    private FirebaseAuth.AuthStateListener firebaseAuthListener;        //Facebook


    private GoogleApiClient googleApiClient;        //Google
    private SignInButton signInButton;              //Google

    Button bLIniciar;
    TextView tLRegistrarse;

    EditText eLUsuario, eLClave;
    private int OpLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


//++++++++++++++++++++++++++++++++++++++++++++++++GOOGLE+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        callbackManager = CallbackManager.Factory.create();         //Facebook
        loginButton = (LoginButton) findViewById(R.id.bLoginFacebook);
        loginButton.setReadPermissions(Arrays.asList("email"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {         //Facebook
            @Override
            public void onSuccess(LoginResult loginResult) {
                OpLog = 2;
                handleFacebookAccesToken (loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), R.string.cancel_login, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), R.string.error_login, Toast.LENGTH_SHORT).show();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    goMainScreen();
                }
            }
        };
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// ++++++++++++++++++++++++++++++++++++++++++++++++GOOGLE+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this) //Googleapiclient es el intermedio entre las api de google y la app
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        signInButton = (SignInButton) findViewById(R.id.bLoginGoogle);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpLog=1;
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, SIGN_IN_CODE);
            }
        });






//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        bLIniciar = (Button) findViewById(R.id.bLIniciar);              //Botón Iniciar que esta en la actividad Login, al presionarse debe direccionar .
        tLRegistrarse = (TextView) findViewById(R.id.tLRegistrarse);    //El TextView REGISTRARSE funciona como un botón el cual direcciona a la actividad REGISTRO.

        eLUsuario = (EditText) findViewById(R.id.eLUsuario);
        eLClave = (EditText) findViewById(R.id.eLClave);

        tLRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistroActivity.class);
                startActivityForResult(intent,1234);
            }
        });

    }

    private void handleFacebookAccesToken(AccessToken accessToken) {

        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),R.string.firebase_error_login, Toast.LENGTH_LONG).show();;
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
         firebaseAuth.addAuthStateListener(firebaseAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(firebaseAuthListener);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        super.onActivityResult(requestCode, resultCode, data);

        if (OpLog==1) {    //login con Google
            Log.d("entroIF", "SI");
            // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);

            if (requestCode == SIGN_IN_CODE) {
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                Log.d("entro2IF", "SI");
                handleSignInResult(result);
            }
        }
        else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
        /*if (requestCode == 1234 && resultCode == RESULT_OK) {

        } else if (requestCode == 1234 && resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "ERROR en Registro", Toast.LENGTH_SHORT).show();
        }*/
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()){
            goMainScreen();
            Log.d("entroMetodo","SI");
        }else{
            Log.d("entroMetodo","NO");
            Intent intent = new Intent(this, LoginActivity.class);
            Toast.makeText(this, R.string.not_login, Toast.LENGTH_SHORT).show();
        }
    }

    private void goMainScreen() {
        Intent intent = new Intent(this, DrawerClienteActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void Inicio(View view){
        if(eLUsuario.getText().toString().matches("Admin")) {
            Intent intent = new Intent(this, DrawerVendActivity.class);
            startActivity(intent);
            finish();
        }
        else if(eLUsuario.getText().toString().matches("Cliente")) {
            Intent intent = new Intent(this, DrawerClienteActivity.class);
            startActivity(intent);
            finish();
        }

        else{
            Toast.makeText(this,"Datos Incorrectos",Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
