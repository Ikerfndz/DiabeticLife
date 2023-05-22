package com.example.proyecto2ikerfernandez;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Agenda extends Preferencias {

    EditText nombre;
    EditText direccion;
    EditText Mail;
    EditText telefono;
    private DatabaseReference marksRef;
    Button save;
    Button lista;
    Button borrar;
    Button modificar;
    ImageButton llamar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Agendas");
        marksRef = userRef.child("Agenda de: " + userId);

        save = (Button) findViewById(R.id.buttonSave);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombre = (EditText) findViewById(R.id.Nombre_editText);
                direccion = (EditText) findViewById(R.id.Dir_editText);
                Mail = (EditText) findViewById(R.id.Mail_editText);
                telefono = (EditText) findViewById(R.id.Tel_editText);

                String nombreStr = nombre.getText().toString();
                String direccionStr = direccion.getText().toString();
                String mailStr = Mail.getText().toString();
                String telefonoStr = telefono.getText().toString();

                if (nombreStr.isEmpty() || direccionStr.isEmpty() || mailStr.isEmpty() || telefonoStr.isEmpty()) {
                    // Si algún campo está vacío, muestra un mensaje de error
                    Toast.makeText(Agenda.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                } else if (!telefonoStr.matches("\\d{9}")) {
                    // Si el número de teléfono no tiene 9 caracteres o no son todos números, muestra un mensaje de error
                    Toast.makeText(Agenda.this, "Por favor, ingresa un número de teléfono válido (9 caracteres, solo números)", Toast.LENGTH_SHORT).show();
                } else {
                    // Si todos los campos están completos y el número de teléfono es válido, crea el contacto y lo agrega a la base de datos
                    //Usuario c = new Usuario(nombreStr, direccionStr, mailStr, telefonoStr);
                    //marksRef.push().setValue(c);
                    Toast.makeText(Agenda.this, "Contacto registrado", Toast.LENGTH_SHORT).show();

                    // Limpia los campos de texto
                    nombre.setText("");
                    direccion.setText("");
                    Mail.setText("");
                    telefono.setText("");
                }
            }


        });


        borrar = (Button) findViewById(R.id.buttonBorrar);
        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText nombre = (EditText) findViewById(R.id.Nombre_editText);
               //Usuario c = new Usuario();
                Query q = marksRef.orderByChild("nombre").equalTo(nombre.getText().toString());
                q.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds: snapshot.getChildren()){
                            String clave = ds.getKey();
                            marksRef.child(clave).removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }


                });
            }
        });

        lista = (Button) findViewById(R.id.buttonList);
        lista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListView lista = findViewById(R.id.lista);
                //Usuario c = new Usuario();
                marksRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Usuario c;
                        ArrayAdapter<Usuario> adaptador;
                        ArrayList<Usuario> listado = new ArrayList<Usuario>();
                        for (DataSnapshot ds: snapshot.getChildren()){
                            c = ds.getValue(Usuario.class);
                            listado.add(c);
                        }
                        adaptador = new ArrayAdapter<Usuario>(Agenda.this, android.R.layout.simple_list_item_1, listado );
                        lista.setAdapter(adaptador);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        modificar = (Button) findViewById(R.id.buttonModificar);
        modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombre = (EditText) findViewById(R.id.Nombre_editText);
                direccion = (EditText) findViewById(R.id.Dir_editText);
                Mail = (EditText) findViewById(R.id.Mail_editText);
                telefono = (EditText) findViewById(R.id.Tel_editText);

                if (nombre.getText().toString().isEmpty()) {
                    Toast.makeText(Agenda.this, "Por favor, ingrese el nombre del contacto", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (direccion.getText().toString().isEmpty()) {
                    Toast.makeText(Agenda.this, "Por favor, ingrese la dirección del contacto", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (Mail.getText().toString().isEmpty()) {
                    Toast.makeText(Agenda.this, "Por favor, ingrese el correo electrónico del contacto", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (telefono.getText().toString().isEmpty()) {
                    Toast.makeText(Agenda.this, "Por favor, ingrese el número de teléfono del contacto", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (telefono.getText().toString().length() != 9) {
                    Toast.makeText(Agenda.this, "Por favor, ingrese un número de teléfono válido (9 dígitos)", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    Long.parseLong(telefono.getText().toString());
                } catch (NumberFormatException e) {
                    Toast.makeText(Agenda.this, "Por favor, ingrese solo números en el número de teléfono", Toast.LENGTH_SHORT).show();
                    return;
                }

              //  Usuario c = new Usuario(nombre.getText().toString(), direccion.getText().toString(), Mail.getText().toString(), telefono.getText().toString());

                Query q = marksRef.orderByChild("nombre").equalTo(nombre.getText().toString());
                q.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds: snapshot.getChildren()){
                            String clave = ds.getKey();
                            marksRef.child(clave).child("direccion").setValue(direccion.getText().toString());
                            marksRef.child(clave).child("Mail").setValue(Mail.getText().toString());
                            marksRef.child(clave).child("telefono").setValue(telefono.getText().toString());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        ListView lista = findViewById(R.id.lista);





    }



}