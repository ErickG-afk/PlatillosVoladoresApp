package com.example.platillosvoladoresapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.platillosvoladoresapp.R;
import com.example.platillosvoladoresapp.entity.service.Usuario;
import com.example.platillosvoladoresapp.utils.DateSerializer;
import com.example.platillosvoladoresapp.utils.TimeSerializer;
import com.example.platillosvoladoresapp.viewmodel.UsuarioViewModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.sql.Date;
import java.sql.Time;

public class MainActivity extends AppCompatActivity {

    private EditText edtMail, edtPassword;
    private Button btnIniciarSesion;
    private UsuarioViewModel viewModel;
    private TextView txtInputUsuario, txtInputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.initViewModel();
        this.init();
    }

    private void initViewModel() {

        viewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);


    }

    private void init()
    {
        edtMail = findViewById(R.id.edtMail);
        edtPassword = findViewById(R.id.edtPassword);
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion);

        btnIniciarSesion.setOnClickListener(v -> {
            viewModel.login(edtMail.getText().toString(),edtPassword.getText().toString()).observe(this,response -> {
                if(response.getRpta() ==1){
                    Toast.makeText(this, response.getMessage(), Toast.LENGTH_SHORT).show();
                    Usuario usuario = response.getBody();
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                    SharedPreferences.Editor editor = preferences.edit();
                    final Gson gSon = new GsonBuilder()
                            .registerTypeAdapter(Date.class, new DateSerializer())
                            .registerTypeAdapter(Time.class, new TimeSerializer()).create();

                    editor.putString("UsuarioJson", gSon.toJson(usuario, new TypeToken<Usuario>(){}.getType()));

                    editor.apply();
                    edtMail.setText("");
                    edtPassword.setText("");
                    startActivity(new Intent(this, StartActivity.class));

                    Toast.makeText(this, "Successful log in ", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(this, "Error al hacer Log In " + response.getMessage(), Toast.LENGTH_SHORT).show();
                        System.out.println(response.getMessage());
                }

            });
        });
    }
}