package com.example.rincondelvergeles.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rincondelvergeles.R;
import com.example.rincondelvergeles.model.Categoria;
import com.example.rincondelvergeles.model.Factura;
import com.example.rincondelvergeles.model.Historial;
import com.example.rincondelvergeles.model.Producto;
import com.example.rincondelvergeles.view.MainViewModel;

import java.util.ArrayList;

public class CategoriasAdapter extends RecyclerView.Adapter<CategoriasAdapter.MyViewHolder>{
    private LayoutInflater inflater;
    private ArrayList<Categoria> categoriaArrayList;
    CategoriasAdapter.OnItemClickListener itemClickListener;
    public interface OnItemClickListener{
        public void onItemClick(Categoria categoria);
    }

    public CategoriasAdapter(ArrayList<Categoria> categoriaArrayList,
                             CategoriasAdapter.OnItemClickListener listener) {
        this.categoriaArrayList = categoriaArrayList;
        this.itemClickListener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.itemcategoria, parent, false);//Lleva false porque el adapter se encarga de añadirlo a la vista
        MyViewHolder vh = new MyViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final Categoria categoria = categoriaArrayList.get(position);
        holder.tvBoton.setText(categoriaArrayList.get(position).getNombre());
        holder.tvBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onItemClick(categoria);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoriaArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{//ViewHolder es solo para las vistas individuales
        Button tvBoton;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBoton = itemView.findViewById(R.id.catButton);
        }
    }

}
