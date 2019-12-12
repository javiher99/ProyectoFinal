package com.example.rincondelvergeles;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.rincondelvergeles.adapter.CategoriasAdapter;
import com.example.rincondelvergeles.adapter.ProductosAdapter;
import com.example.rincondelvergeles.methods.Methods;
import com.example.rincondelvergeles.model.Categoria;
import com.example.rincondelvergeles.model.Comanda;
import com.example.rincondelvergeles.model.Factura;
import com.example.rincondelvergeles.model.Producto;
import com.example.rincondelvergeles.view.MainViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ProductosActivity extends AppCompatActivity {

    private RecyclerView rvProductos;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Producto> productoList;
    private ArrayList<Categoria> categoriasList;
    MainViewModel viewModel;
    long idEmp, idFactura;
    Toolbar toolbar;
    AlertDialog alertDialog;
    Factura factura;
    SharedPreferences sharedPreferences;
    CategoriasFragment fragment;
    ProductosAdapter myAdapter;
    CategoriasAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);
        alertDialog = Methods.cargandoMostrar(this, getResources().getString(R.string.cargandoProductos)).create();
        alertDialog.show();
        sharedPreferences = getSharedPreferences(getResources().getString(R.string.packageName), Context.MODE_PRIVATE);
        idEmp = getIntent().getLongExtra(getResources().getString(R.string.idEmp), 1);
        idFactura = getIntent().getLongExtra(getResources().getString(R.string.idFactura), 0);
        factura = getIntent().getParcelableExtra(getResources().getString(R.string.factura));
        initComponentes();
    }

    @Override
    protected void onResume() {
        super.onResume();
        bajarDatos();
    }

    private void initComponentes() {
        rvProductos = findViewById(R.id.rvProductos);
        layoutManager = new LinearLayoutManager(this);
        rvProductos.setLayoutManager(layoutManager);
    }

    void bajarDatos() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.productosLiveData().observe(ProductosActivity.this, new Observer<List<Producto>>() {
            @Override
            public void onChanged(List<Producto> productos) {
                productoList = (ArrayList<Producto>) productos;
                myAdapter = new ProductosAdapter(productoList,
                        ProductosActivity.this,
                        viewModel,
                        ProductosActivity.this,
                        idEmp,
                        ProductosActivity.this,
                        factura,
                        sharedPreferences
                );
                rvProductos.setAdapter(myAdapter);
                viewModel.categoriaLiveData().observe(ProductosActivity.this, new Observer<List<Categoria>>() {
                    @Override
                    public void onChanged(List<Categoria> categorias) {
                        categoriasList = (ArrayList<Categoria>) categorias;
                        CategoriasFragment.adapter = new CategoriasAdapter(categoriasList, new CategoriasAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(Categoria categoria) {
                                ArrayList<Producto> filtered = new ArrayList<>();
                                for (int i = 0; i < productoList.size(); i++) {
                                    if (productoList.get(i).getCategoria() == categoria.getId()) {
                                        filtered.add(productoList.get(i));
                                    }
                                }
                                myAdapter = new ProductosAdapter(filtered,
                                        ProductosActivity.this,
                                        viewModel,
                                        ProductosActivity.this,
                                        idEmp,
                                        ProductosActivity.this,
                                        factura,
                                        sharedPreferences
                                );
                                rvProductos.setAdapter(myAdapter);
                            }
                        });
                        CategoriasFragment.layoutManager = new LinearLayoutManager(ProductosActivity.this);
                        CategoriasFragment.recyclerView.setLayoutManager(CategoriasFragment.layoutManager);
                        CategoriasFragment.recyclerView.setAdapter(CategoriasFragment.adapter);
                        alertDialog.cancel();
                    }
                });
            }
        });
    }

}
