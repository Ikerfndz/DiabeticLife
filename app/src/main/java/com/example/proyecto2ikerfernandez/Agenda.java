package com.example.proyecto2ikerfernandez;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

                Contacto c = new Contacto(nombre.getText().toString(), direccion.getText().toString(), Mail.getText().toString(), telefono.getText().toString());
                marksRef.push().setValue(c);
                Toast.makeText(Agenda.this, "Contacto registrado", Toast.LENGTH_SHORT).show();
                String vacio ="";
                nombre.setText(vacio);
                direccion.setText(vacio);
                Mail.setText(vacio);
                telefono.setText(vacio);

            }
        });


        borrar = (Button) findViewById(R.id.buttonBorrar);
        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText nombre = (EditText) findViewById(R.id.Nombre_editText);
                Contacto c = new Contacto();
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
                Contacto c = new Contacto();
                marksRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Contacto c;
                        ArrayAdapter<String> adaptador;
                        ArrayList<String> listado = new ArrayList<String>();
                        for (DataSnapshot ds: snapshot.getChildren()){
                            c = ds.getValue(Contacto.class);
                            listado.add("Nombre: " + c.getNombre() + "|| Nº: " + c.getTelefono());
                        }
                        adaptador = new ArrayAdapter<String>(Agenda.this, android.R.layout.simple_list_item_1, listado );
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
                Contacto c = new Contacto(nombre.getText().toString(), direccion.getText().toString(), Mail.getText().toString(), telefono.getText().toString());

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
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menuxml,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        String mensaje = "";
        switch (item.getItemId()){
            case R.id.Menu1:
                Intent intent = new Intent(getApplicationContext(), Agenda.class);
                startActivity(intent);
                return true;

            case R.id.Menu3:
                Intent intent3 = new Intent(getApplicationContext(), Login.class);
                startActivity(intent3);
            default:return super.onOptionsItemSelected(item);
        }
    }
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu,v,menuInfo);
        MenuInflater inflate = getMenuInflater();
        inflate.inflate(R.menu.menuxml,menu);

    }
}