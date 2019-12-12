package com.example.rincondelvergeles.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rincondelvergeles.R;
import com.example.rincondelvergeles.model.Comanda;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ComandasAdapter extends RecyclerView.Adapter<ComandasAdapter.MyViewHolder>{

    private LayoutInflater inflater;
    private ArrayList<Comanda> comandas;
    private Context context;

    public ComandasAdapter(ArrayList<Comanda> misComandas, Context context) {
        this.comandas = misComandas;
        this.context = context;
    }

    @NonNull
    @Override
    public ComandasAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.itemcomanda, parent, false);//Lleva false porque el adapter se encarga de añadirlo a la vista
        ComandasAdapter.MyViewHolder vh = new ComandasAdapter.MyViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ComandasAdapter.MyViewHolder holder, final int position) {
        holder.tvProducto.setText(comandas.get(position).getNombreProducto());
        holder.tvUnidades.setText(String.valueOf(comandas.get(position).getUnidades()));
        holder.tvTotal.setText(String.valueOf(comandas.get(position).getPrecio()));
        if (comandas.get(position).getEntregada() == 0) {
            holder.tvEntregado.setText(context.getResources().getString(R.string.no));
            holder.clComanda.setBackgroundColor(context.getResources().getColor(R.color.colorComandaActiva));
        } else {
            holder.tvEntregado.setText(context.getResources().getString(R.string.si));
            holder.clComanda.setBackgroundColor(context.getResources().getColor(R.color.colorComandaEntregada));
        }
        holder.clComanda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return comandas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{//ViewHolder es solo para las vistas individuales
        TextView tvProducto, tvTotal, tvEntregado;
        TextView tvUnidades;
        ConstraintLayout clComanda;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProducto = itemView.findViewById(R.id.tvProductoComanda);
            tvUnidades = itemView.findViewById(R.id.tvUnidades);
            tvTotal = itemView.findViewById(R.id.tvTotalComanda);
            tvEntregado = itemView.findViewById(R.id.tvEntregado);
            clComanda = itemView.findViewById(R.id.clComanda);
        }
    }
}
