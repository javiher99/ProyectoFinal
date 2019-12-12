package com.example.rincondelvergeles.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rincondelvergeles.ComandasActivity;
import com.example.rincondelvergeles.FacturasActivity;
import com.example.rincondelvergeles.MainActivity;
import com.example.rincondelvergeles.R;
import com.example.rincondelvergeles.methods.Methods;
import com.example.rincondelvergeles.model.Factura;
import com.example.rincondelvergeles.model.Historial;
import com.example.rincondelvergeles.model.Mesa;
import com.example.rincondelvergeles.view.MainViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;

public class FacturasAdapter extends RecyclerView.Adapter<FacturasAdapter.MyViewHolder>{
    private LayoutInflater inflater;
    private ArrayList<Factura> misFacturas;
    Context context;
    MainViewModel viewModel;
    LifecycleOwner lifecycleOwner;
    AlertDialog alertDialog;
    long idEmp;
    Activity activity;
    SharedPreferences sharedPreferences;

    public FacturasAdapter(ArrayList<Factura> misFacturas,
                           Context context,
                           MainViewModel viewModel,
                           LifecycleOwner lifecycleOwner,
                           long idEmp,
                           Activity activity,
                           SharedPreferences sharedPreferences) {
        this.misFacturas = misFacturas;
        this.context = context;
        this.viewModel = viewModel;
        this.lifecycleOwner = lifecycleOwner;
        this.idEmp = idEmp;
        this.activity = activity;
        this.sharedPreferences = sharedPreferences;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.itemfactura, parent, false);//Lleva false porque el adapter se encarga de añadirlo a la vista
        MyViewHolder vh = new MyViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.tvMesa.setText(String.valueOf(misFacturas.get(position).getMesa()));
        holder.tvHora.setText(misFacturas.get(position).getHoraInicio());
        holder.tvId.setText(context.getResources().getString(R.string.num) + " " + Long.toString(misFacturas.get(position).getId()));
        holder.clFactura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                String json = gson.toJson(misFacturas.get(position));
                sharedPreferences.edit().putString(context.getResources().getString(R.string.factura), json).commit();
                context.startActivity(new Intent(context, ComandasActivity.class));
                activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        holder.clFactura.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View view) {
                Methods.animShakeSides(context, holder.clFactura);
                new AlertDialog.Builder(context)
                        .setTitle(context.getResources().getString(R.string.facturaNum) + " " + misFacturas.get(position).getId())
                        .setMessage(context.getResources().getString(R.string.totalDialog)+ " " + misFacturas.get(position).getTotal() + context.getString(R.string.euro))
                        .setNeutralButton(context.getResources().getString(R.string.ok), null)
                        .show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return misFacturas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{//ViewHolder es solo para las vistas individuales
        TextView tvMesa, tvHora, tvId;
        ConstraintLayout clFactura;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tvFactura);
            tvMesa = itemView.findViewById(R.id.tvMesa);
            clFactura = itemView.findViewById(R.id.clFactura);
            tvHora = itemView.findViewById(R.id.tvHoraApertura);
        }
    }

}
