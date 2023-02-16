package com.example.platillosvoladoresapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.example.platillosvoladoresapp.R;
import com.example.platillosvoladoresapp.entity.service.Cliente;
import com.example.platillosvoladoresapp.entity.service.Usuario;
import com.example.platillosvoladoresapp.viewmodel.ClienteViewModel;
import com.example.platillosvoladoresapp.viewmodel.DocumentoAlmacenadoViewModel;
import com.example.platillosvoladoresapp.viewmodel.UsuarioViewModel;
import com.google.android.material.textfield.TextInputLayout;

import java.time.LocalDateTime;

import okhttp3.Response;

public class RegistrarUsuarioActivity extends AppCompatActivity {

    private EditText edtNameUser, edtPrimerApellidoU, edtSegundoApellidoU, edtNumDocU, edtTelefonoU, edtDireccionU, edtEmailUser, edtPasswordUser;
    private Button btnGuardarDatos;
    private TextInputLayout  txtInputNameUser, txtInputPrimerApellidoU, txtInputSegundoApellidoU, txtInputTipoDoc, txtInputNumeroDocU, txtInputCiudad, txtInputProvincia
            , txtInputTelefonoU, txtInputDireccionU, txtInputEmailUser, txtInputPasswordUser;
    private AutoCompleteTextView dropDownTipoDoc, dropDownCiudad, dropdownProvincia;

    private ClienteViewModel clienteViewModel;
    private UsuarioViewModel usuarioViewModel;
    private DocumentoAlmacenadoViewModel documentoAlmacenadoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);
        this.init();
        this.initViewModel();
        this.spinners();
    }

    private void spinners() {
        //LISTA DE TIPOS DE DOCUMENTOS
        String[] tipoDoc = getResources().getStringArray(R.array.tipoDoc);
        ArrayAdapter arrayTipoDoc = new ArrayAdapter(this, R.layout.dropdown_item, tipoDoc);
        dropDownTipoDoc.setAdapter(arrayTipoDoc);
        //LISTA DE CIUDADES
        String[] ciudades = getResources().getStringArray(R.array.ciudad);
        ArrayAdapter arrayCiudades = new ArrayAdapter(this, R.layout.dropdown_item, ciudades);
        dropDownCiudad.setAdapter(arrayCiudades);
        //LISTA DE PROVINCIAS
        String[] provincias = getResources().getStringArray(R.array.provincias);
        ArrayAdapter arrayProvincias = new ArrayAdapter(this, R.layout.dropdown_item, provincias);
        dropdownProvincia.setAdapter(arrayProvincias);
    }

    private void initViewModel() {
        final ViewModelProvider vmp = new ViewModelProvider(this);
        this.clienteViewModel = vmp.get(ClienteViewModel.class);
        this.usuarioViewModel = vmp.get(UsuarioViewModel.class);
        this.documentoAlmacenadoViewModel = vmp.get(DocumentoAlmacenadoViewModel.class);
    }

    private void init(){
        dropDownTipoDoc = findViewById(R.id.dropdownTipoDoc);
        dropDownCiudad = findViewById(R.id.dropdownCiudad);
        dropdownProvincia = findViewById(R.id.dropdownProvincia);
        edtNameUser = findViewById(R.id.edtNameUser);
        edtPrimerApellidoU = findViewById(R.id.edtPrimerApellidoU);
        edtSegundoApellidoU = findViewById(R.id.edtSegundoApellidoU);
        edtNumDocU = findViewById(R.id.edtNumDocU);
        edtTelefonoU = findViewById(R.id.edtTelefonoU);
        edtDireccionU = findViewById(R.id.edtDireccionU);
//        edtEmailUser = findViewById(R.id.edtEmailUser);
//        edtPasswordUser = findViewById(R.id.edtPasswordUser);
        txtInputNameUser = findViewById(R.id.txtInputNameUser);
        txtInputPrimerApellidoU = findViewById(R.id.txtInputPrimerApellidoU);
        txtInputSegundoApellidoU = findViewById(R.id.txtInputSegundoApellidoU);
        txtInputTipoDoc = findViewById(R.id.txtInputTipoDoc);
        txtInputNumeroDocU = findViewById(R.id.txtInputNumeroDocU);
        txtInputCiudad = findViewById(R.id.txtInputCiudad);
        txtInputProvincia = findViewById(R.id.txtInputProvincia);
        txtInputTelefonoU = findViewById(R.id.txtInputTelefonoU);
        txtInputDireccionU = findViewById(R.id.txtInputDireccionU);
//        txtInputEmailUser = findViewById(R.id.txtInputEmailUser);
//        txtInputPasswordUser = findViewById(R.id.txtInputPasswordUser);
//        btnGuardarDatos = findViewById(R.id.btnGuardarDatos);

//        btnGuardarDatos.setOnClickListener(v ->{
//            this.guardarDatos();
//        });
        ///ONCHANGE LISTENEER A LOS EDITEXT
        edtNameUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtInputNameUser.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtPrimerApellidoU.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtInputPrimerApellidoU.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtSegundoApellidoU.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtInputSegundoApellidoU.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtNumDocU.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtInputNumeroDocU.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtTelefonoU.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtInputTelefonoU.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtDireccionU.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtInputDireccionU.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        dropDownTipoDoc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtInputTipoDoc.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        dropdownProvincia.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtInputProvincia.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        dropDownCiudad.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtInputCiudad.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void guardarDatos() {
        Cliente cliente = new Cliente();
        if(validar()){
            try {
                cliente.setNombres(edtNameUser.getText().toString());
                cliente.setPrimerApellido(edtPrimerApellidoU.getText().toString());
                cliente.setSegundoApellido(edtSegundoApellidoU.getText().toString());
                cliente.setTipoDoc(dropDownTipoDoc.getText().toString());
                cliente.setNumDoc(edtNumDocU.getText().toString());
                cliente.setTelefono(edtTelefonoU.getText().toString());
                cliente.setCiudad(dropDownCiudad.getText().toString());
                cliente.setProvincia(dropdownProvincia.getText().toString());
                cliente.setDireccionEnvio(edtDireccionU.getText().toString());
                cliente.setId(0);
                this.clienteViewModel.guardarCliente(cliente).observe(this, cResponse->{
                    if(cResponse.getRpta() ==1){
                        int idCliente = cResponse.getBody().getId();
                        Usuario usuario = new Usuario();
                        usuario.setEmail(edtEmailUser.getText().toString());
                        usuario.setClave(edtPasswordUser.getText().toString());
                        usuario.setVigencia(true);
                        usuario.setCliente(new Cliente(idCliente));
                        this.usuarioViewModel.save(usuario);
                    }

                });

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private boolean validar(){
        boolean retorno = true;
        String nombre, primerApellido, segundoApellido, numDoc, numTelefono, direccion
                , correo, clave, dropTipoDoc, dropCiudad, dropProvincia;

        nombre = edtNameUser.getText().toString();
        primerApellido = edtPrimerApellidoU.getText().toString();
        segundoApellido = edtSegundoApellidoU.getText().toString();
        numDoc = edtNumDocU.getText().toString();
        numTelefono = edtTelefonoU.getText().toString();
        direccion = edtDireccionU.getText().toString();
        correo = edtEmailUser.getText().toString();
        clave = edtPasswordUser.getText().toString();
        dropTipoDoc = dropDownTipoDoc.getText().toString();
        dropCiudad = dropDownCiudad.getText().toString();
        dropProvincia = dropdownProvincia.getText().toString();

        if (nombre.isEmpty()) {
            txtInputNameUser.setError("Ingrese su nombre");
            retorno = false;
        } else {
            txtInputNameUser.setErrorEnabled(false);
        }
        if (primerApellido.isEmpty()) {
            txtInputPrimerApellidoU.setError("Ingrese su primer apellido");
            retorno = false;
        } else {
            txtInputPrimerApellidoU.setErrorEnabled(false);
        }
        if (segundoApellido.isEmpty()) {
            txtInputSegundoApellidoU.setError("Ingrese su segundo apellido");
            retorno = false;
        } else {
            txtInputSegundoApellidoU.setErrorEnabled(false);
        }
        if (numDoc.isEmpty()) {
            txtInputNumeroDocU.setError("Ingrese su numero de documento");
            retorno = false;
        } else {
            txtInputNumeroDocU.setErrorEnabled(false);
        }
        if (dropTipoDoc.isEmpty()) {
            txtInputTipoDoc.setError("Ingrese su numero de telefono");
            retorno = false;
        } else {
            txtInputTipoDoc.setErrorEnabled(false);
        }
        if (dropCiudad.isEmpty()) {
            txtInputCiudad.setError("Ingrese su numero de telefono");
            retorno = false;
        } else {
            txtInputCiudad.setErrorEnabled(false);
        }
        if (dropProvincia.isEmpty()) {
            txtInputProvincia.setError("Ingrese su numero de telefono");
            retorno = false;
        } else {
            txtInputProvincia.setErrorEnabled(false);
        }
        if (numTelefono.isEmpty()) {
            txtInputTelefonoU.setError("Ingrese su numero de telefono");
            retorno = false;
        } else {
            txtInputTelefonoU.setErrorEnabled(false);
        }
        if (direccion.isEmpty()) {
            txtInputDireccionU.setError("Ingrese su direccion");
            retorno = false;
        } else {
            txtInputDireccionU.setErrorEnabled(false);
        }
        if (correo.isEmpty()) {
            txtInputEmailUser.setError("Ingrese su email");
            retorno = false;
        } else {
            txtInputEmailUser.setErrorEnabled(false);
        }
        if (clave.isEmpty()) {
            txtInputPasswordUser.setError("Ingrese su contraseña");
            retorno = false;
        } else {
            txtInputPasswordUser.setErrorEnabled(false);
        }
        return retorno;
    }
}