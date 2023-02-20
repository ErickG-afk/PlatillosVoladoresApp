package com.example.platillosvoladoresapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
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
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.sql.Date;
import java.sql.Time;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity {

    private EditText edtMail, edtPassword;
    private Button btnIniciarSesion;
    private UsuarioViewModel viewModel;
    private TextView txtNuevoUsuario;
    private TextInputLayout txtInputUsuario, txtInputPassword;


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

    private void init() {
        edtMail = findViewById(R.id.edtMail);
        edtPassword = findViewById(R.id.edtPassword);
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion);
        txtNuevoUsuario = findViewById(R.id.txtNuevoUsuario);
        txtInputUsuario = findViewById(R.id.txtInputUsuario);
        txtInputPassword = findViewById(R.id.txtInputPassword);



        btnIniciarSesion.setOnClickListener(v -> {
            try {
                if (validar()) {
                    viewModel.login(edtMail.getText().toString(), edtPassword.getText().toString()).observe(this, response -> {
                        if (response.getRpta() == 1) {
                            //Toast.makeText(this, response.getMessage(), Toast.LENGTH_SHORT).show();
                            DisplayCustomToast(response.getMessage());
                            Usuario usuario = response.getBody();
                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                            SharedPreferences.Editor editor = preferences.edit();
                            final Gson gSon = new GsonBuilder()
                                    .registerTypeAdapter(Date.class, new DateSerializer())
                                    .registerTypeAdapter(Time.class, new TimeSerializer()).create();

                            editor.putString("UsuarioJson", gSon.toJson(usuario, new TypeToken<Usuario>() {
                            }.getType()));

                            editor.apply();
                            edtMail.setText("");
                            edtPassword.setText("");
                            startActivity(new Intent(this, StartActivity.class));
                        } else {
                            DisplayCustomToast2("Credenciales invalidas");
                        }
                    });

                } else {
                    DisplayCustomToast2("Por favor, complete todos los campos.");
                }

            } catch (Exception e) {
                DisplayCustomToast2("Se ha producido un error al hacer login : " + e.getMessage());

            }
        });

        txtNuevoUsuario.setOnClickListener(v -> {
            Intent i = new Intent(this, RegistrarUsuarioActivity.class);
            startActivity(i);


        });

        edtMail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtInputUsuario.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtInputPassword.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public void DisplayCustomToast(String mensaje) {
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.custom_toast_ok));

        TextView txtMensaje = view.findViewById(R.id.mensaje_toast1);
        txtMensaje.setText(mensaje);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 200);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();
    }

    public void DisplayCustomToast2(String mensaje) {
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_toast_wrong_login, (ViewGroup) findViewById(R.id.custom_toast_wrong_login));

        TextView txtMensaje = view.findViewById(R.id.mensaje_toast2);
        txtMensaje.setText(mensaje);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 200);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();
    }

    private boolean validar() {

        boolean retorno = true;
        String usuario, password;

        usuario = edtMail.getText().toString();
        password = edtPassword.getText().toString();

         if (usuario.isEmpty()) {
            txtInputUsuario.setError("Ingrese su usario y/o correo electrónico");
            retorno = false;
        } else {
            txtInputUsuario.setErrorEnabled(false);
        }
        if (password.isEmpty()) {
            txtInputPassword.setError("Ingrese su contraseña");
            retorno = false;
        } else {
            txtInputPassword.setErrorEnabled(false);
        }
        return retorno;
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String pref = preferences.getString("UsuarioJson", "");
        if (!pref.equals("")) {
            DisplayCustomToast("Se detecto una sesión activa, el login será omitido!");
            this.startActivity(new Intent(this, StartActivity.class));
            this.overridePendingTransition(R.anim.left_in, R.anim.left_out);
        }
    }

    @Override
    public void onBackPressed() {
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE).setTitleText("has presionado el botón atrás")
                .setContentText("¿Quieres cerrar la aplicación?")
                .setCancelText("No, Cancelar!").setConfirmText("Sí, Cerrar")
                .showCancelButton(true).setCancelClickListener(sDialog -> {
                    sDialog.dismissWithAnimation();
                    new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE).setTitleText("Operación cancelada")
                            .setContentText("No saliste de la app")
                            .show();
                }).setConfirmClickListener(sweetAlertDialog -> {
                    sweetAlertDialog.dismissWithAnimation();
                    System.exit(0);
                }).show();
    }

    public void errorMessage(String message) {
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE).setTitleText("Oops...").setContentText(message).show();
    }

    public void warningMessage(String message) {
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE).setTitleText("Notificacion del Sistema").setContentText(message).setConfirmText("ok").show();
    }

    public void successMessage(String message) {
        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE).setTitleText("Correcto").setContentText(message).show();
    }

}