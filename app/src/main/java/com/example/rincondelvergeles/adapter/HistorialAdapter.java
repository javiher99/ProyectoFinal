package com.example.rincondelvergeles.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rincondelvergeles.ComandasActivity;
import com.example.rincondelvergeles.R;
import com.example.rincondelvergeles.methods.Methods;
import com.example.rincondelvergeles.model.Factura;
import com.example.rincondelvergeles.model.Historial;
import com.example.rincondelvergeles.view.MainViewModel;
import com.google.gson.Gson;

import java.util.ArrayList;

public class HistorialAdapter extends RecyclerView.Adapter<HistorialAdapter.MyViewHolder>{
    private LayoutInflater inflater;
    private ArrayList<Historial> historialArrayList;
    Context context;

    public HistorialAdapter(ArrayList<Historial> historials, Context context) {
        this.historialArrayList = historials;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.itemhistorial, parent, false);//Lleva false porque el adapter se encarga de añadirlo a la vista
        MyViewHolder vh = new MyViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.tvMesa.setText(String.valueOf(historialArrayList.get(position).getMesa()));
        holder.tvHoraIni.setText(historialArrayList.get(position).getHoraInicio());
        holder.tvHoraFin.setText(historialArrayList.get(position).getHoraCierre());
        holder.tvFactura.setText(context.getResources().getString(R.string.num) + " " + historialArrayList.get(position).getId());
        holder.tvTotal.setText(historialArrayList.get(position).getTotal() + context.getResources().getString(R.string.euro));
    }

    @Override
    public int getItemCount() {
        return historialArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{//ViewHolder es solo para las vistas individuales
        TextView tvMesa, tvHoraIni, tvHoraFin, tvFactura, tvTotal;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFactura = itemView.findViewById(R.id.tvFactura);
            tvMesa = itemView.findViewById(R.id.tvMesa);
            tvHoraIni = itemView.findViewById(R.id.tvHoraIn);
            tvHoraFin = itemView.findViewById(R.id.tvHoraFin);
            tvTotal = itemView.findViewById(R.id.tvTotal);
        }
    }

}
