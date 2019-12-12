package com.example.rincondelvergeles;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerViewAccessibilityDelegate;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.rincondelvergeles.adapter.FacturasAdapter;
import com.example.rincondelvergeles.adapter.MesasAdapter;
import com.example.rincondelvergeles.methods.Methods;
import com.example.rincondelvergeles.model.Factura;
import com.example.rincondelvergeles.model.Mesa;
import com.example.rincondelvergeles.view.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class SeleccionarMesa extends AppCompatActivity {

    private ArrayList<Mesa> mesasList;
    private RecyclerView recyclerView;
    private MesasAdapter adapter;
    private SharedPreferences sharedPreferences;
    long idEmp;
    private RecyclerView.LayoutManager layoutManager;
    MainViewModel viewModel;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_mesa);

        sharedPreferences = getSharedPreferences(getResources().getString(R.string.packageName), Context.MODE_PRIVATE);
        idEmp = sharedPreferences.getLong(getResources().getString(R.string.id), 0);
        alertDialog = Methods.cargandoMostrar(this, getResources().getString(R.string.cargandoMesas)).create();
        alertDialog.show();
        initComponentes();
        bajarMesas();
    }

    void initComponentes() {
        recyclerView = findViewById(R.id.rvMesas);
        int spanCount = 2;
        layoutManager = new GridLayoutManager(this, spanCount);//Por defecto con this en el constructor hace una lista vertical
        recyclerView.setLayoutManager(layoutManager);
    }

    void bajarMesas() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getMesasLibres().observe(this, new Observer<List<Mesa>>() {
            @Override
            public void onChanged(List<Mesa> mesas) {
                mesasList = (ArrayList<Mesa>) mesas;
                adapter = new MesasAdapter(mesasList, viewModel, idEmp, SeleccionarMesa.this, SeleccionarMesa.this, SeleccionarMesa.this);
                recyclerView.setAdapter(adapter);
                alertDialog.cancel();
            }
        });
    }

}
