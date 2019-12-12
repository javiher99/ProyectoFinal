package com.example.rincondelvergeles;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.rincondelvergeles.adapter.FacturasAdapter;
import com.example.rincondelvergeles.methods.Methods;
import com.example.rincondelvergeles.model.Factura;
import com.example.rincondelvergeles.view.MainViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FacturasActivity extends AppCompatActivity {

    private RecyclerView rvFacturas;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Factura> misFacturas;
    public static FacturasAdapter myAdapter;
    MainViewModel viewModel;
    SharedPreferences sharedPreferences;
    long idEmp;
    String nombre;
    AlertDialog alertDialog;
    Toolbar toolbar;
    FloatingActionButton refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facturas);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        alertDialog = Methods.cargandoMostrar(this, getResources().getString(R.string.cargandoFacturas)).create();
        misFacturas = new ArrayList<>();
        sharedPreferences = getSharedPreferences(getResources().getString(R.string.packageName), Context.MODE_PRIVATE);
        idEmp = sharedPreferences.getLong(getResources().getString(R.string.id), 0);
        toolbarTexto();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FacturasActivity.this, SeleccionarMesa.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        refresh = findViewById(R.id.refresh_bt);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.show();
                bajarFacturas();
            }
        });
        initComponentes();
    }

    void toolbarTexto() {
        nombre = sharedPreferences.getString(getResources().getString(R.string.nombre), "");
        getSupportActionBar().setTitle(getResources().getString(R.string.saludo) + " " + nombre);
        toolbar.setTitleTextColor(getResources().getColor(R.color.blanco));
    }

    @Override
    protected void onResume() {
        super.onResume();
        alertDialog.show();
        bajarFacturas();
    }

    void bajarFacturas() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getAllFacturas().observe(this, new Observer<List<Factura>>() {
            @Override
            public void onChanged(List<Factura> facturas) {
                if (misFacturas != facturas) {
                    misFacturas = (ArrayList<Factura>) facturas;
                    myAdapter = new FacturasAdapter(misFacturas,
                            FacturasActivity.this,
                            viewModel,
                            FacturasActivity.this,
                            idEmp,
                            FacturasActivity.this,
                            sharedPreferences);
                    rvFacturas.setAdapter(myAdapter);
                    alertDialog.cancel();
                }
            }
        });
    }

    private void initComponentes() {
        rvFacturas = findViewById(R.id.rvFacturas);
        //Asignarle un layout manager (my_recycler_view.xml)
        layoutManager = new LinearLayoutManager(this);//Por defecto con this en el constructor hace una lista vertical
        //layoutManager = new GridLayoutManager(this, 2);//El segundo parámetro es el número de columnas
        rvFacturas.setLayoutManager(layoutManager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.historial:
                Intent intent = new Intent(FacturasActivity.this, HistorialActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
