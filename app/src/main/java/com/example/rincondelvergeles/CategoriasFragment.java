package com.example.rincondelvergeles;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.rincondelvergeles.adapter.CategoriasAdapter;
import com.example.rincondelvergeles.adapter.ProductosAdapter;
import com.example.rincondelvergeles.model.Categoria;
import com.example.rincondelvergeles.model.Factura;
import com.example.rincondelvergeles.model.Historial;
import com.example.rincondelvergeles.model.Producto;
import com.example.rincondelvergeles.view.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class CategoriasFragment extends Fragment {

    public static RecyclerView recyclerView;
    public static RecyclerView.LayoutManager layoutManager;
    public static CategoriasAdapter adapter;
    Context context;

    public CategoriasFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vista;
        vista = inflater.inflate(R.layout.fragment_categorias, container);
        recyclerView = vista.findViewById(R.id.rvCategorias);
        return vista;
    }

}
