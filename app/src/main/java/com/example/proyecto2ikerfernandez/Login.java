package com.example.proyecto2ikerfernandez;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends Preferencias {

    private FirebaseAuth mAuth;
    Intent intenMain;
    Switch tema;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText Editmail = (EditText) findViewById(R.id.mail);
        EditText Editpassword = (EditText) findViewById(R.id.password);

        Button Login = (Button) findViewById(R.id.loginButton);
        Button Registro = (Button) findViewById(R.id.registroButton);


        // tema = (Switch) findViewById(R.id.switch1);
       /* SharedPreferences sp = getSharedPreferences("SP", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        int i = sp.getInt("Theme", 1);

        if(i==1){
            tema.setChecked(false);

        }else{
            tema.setChecked(true);
        }
        tema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(tema.isChecked()){
                    editor.putInt("Theme", 0);
                }else{
                    editor.putInt("Theme", 1);
                }
                editor.commit();
                setDayNight();
            }
        });
*/

        Registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Registro.class);
                startActivity(intent);
            }
        });


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean valido = false;


                String mail = Editmail.getText().toString();
                if (mail.isEmpty()) {
                    Toast.makeText(Login.this, "Login fallido, mail vacio",
                            Toast.LENGTH_SHORT).show();
                } else {
                    valido = true;
                }

                String pass = Editpassword.getText().toString();
                if (pass.isEmpty()) {
                    Toast.makeText(Login.this, "Login fallido, password vacio",
                            Toast.LENGTH_SHORT).show();
                    valido = false;
                } else {
                    valido = true;
                }


                if (valido == true) {
                    login(mail, pass);
                }

            }
        });

    }


    private void login(String mail, String pass) {
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(mail, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(Login.this, PrincipalApp.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
}
