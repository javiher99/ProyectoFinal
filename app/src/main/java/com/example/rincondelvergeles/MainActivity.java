package com.example.rincondelvergeles;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.rincondelvergeles.methods.Methods;
import com.example.rincondelvergeles.model.Empleado;
import com.example.rincondelvergeles.model.Factura;
import com.example.rincondelvergeles.view.MainViewModel;
import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String MENSAJE="aa";

    private TextInputLayout lUsuario;
    private TextInputLayout lContraseña;
    private EditText etUsuario;
    private EditText etContraseña;
    private Button btLogin;
    private ImageButton btInfo;
    private MainViewModel viewModel;
    private List<Empleado> empleados;
    private static List<Factura> facturas;
    ImageView imageView;
    private Animation animFade;
    SharedPreferences sharedPreferences;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alertDialog = Methods.cargandoMostrar(this, getResources().getString(R.string.cargandoEmpleados)).create();
        alertDialog.show();
        bajarEmpleados();
        initComponents();
    }

    public void bajarEmpleados() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.empleadosLiveData().observe(MainActivity.this, new Observer<List<Empleado>>() {
            @Override
            public void onChanged(List<Empleado> empleados) {
                MainActivity.this.empleados = empleados;
                alertDialog.cancel();
            }
        });
    }

    private void initComponents() {
        lUsuario = findViewById(R.id.lUsuario);
        lContraseña = findViewById(R.id.lContraseña);
        etUsuario = findViewById(R.id.etUsuario);
        etContraseña = findViewById(R.id.etContraseña);
        btLogin = findViewById(R.id.btLogin);
        btInfo = findViewById(R.id.btInfo);
        imageView = findViewById(R.id.imageView);
        animFade(MainActivity.this, imageView);
        etUsuario.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etUsuario.getText().toString().isEmpty()) {
                    lUsuario.setError(getResources().getString(R.string.nameRequired));
                } else {
                    lUsuario.setError(null);
                }
            }
        });
        etContraseña.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etContraseña.getText().toString().isEmpty()) {
                    lContraseña.setError(getResources().getString(R.string.passRequired));
                } else {
                    lContraseña.setError(null);
                }
            }
        });

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etUsuario.getText().toString().isEmpty()) {
                    String login = "";
                    int pos = -1;
                    for (int i = 1; i < empleados.size(); i++) {
                        if (empleados.get(i).getLogin().equals(etUsuario.getText().toString())) {
                            login = empleados.get(i).getLogin();
                            pos = i;
                            break;
                        }
                    }
                    try {
                        if (etContraseña.getText().toString().equals(empleados.get(pos).getPass())) {
                            Toast.makeText(MainActivity.this, getResources().getString(R.string.welcome) + " " + login, Toast.LENGTH_LONG).show();
                            sharedPreferences = getSharedPreferences(getResources().getString(R.string.packageName), Context.MODE_PRIVATE);
                            sharedPreferences.edit().putLong(getResources().getString(R.string.id), empleados.get(pos).getId()).commit();
                            sharedPreferences.edit().putString(getResources().getString(R.string.nombre), empleados.get(pos).getLogin()).commit();
                            startActivity(new Intent(MainActivity.this, FacturasActivity.class));
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        } else {
                            lContraseña.setError(getResources().getString(R.string.notCorrectPass));
                            Methods.animShake(MainActivity.this, btLogin);
                        }
                    } catch (Exception e) {
                        lUsuario.setError(getResources().getString(R.string.nonExistingUser));
                        lContraseña.setError(getResources().getString(R.string.notCorrectPass));
                        Methods.animShake(MainActivity.this, btLogin);
                    }


                } else {
                    lUsuario.setError(getResources().getString(R.string.empty));
                    Methods.animShake(MainActivity.this, btLogin);
                }
            }
        });

        btInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle(getResources().getString(R.string.info))
                        .setMessage(getResources().getString(R.string.tvCopyright))
                        .setNeutralButton(getResources().getString(R.string.ok), null)
                        .show();
            }
        });
    }

    public void animFade (Context context, final View view) {
        animFade = AnimationUtils.loadAnimation(context, R.anim.fade);
        view.startAnimation(animFade);
    }

}
