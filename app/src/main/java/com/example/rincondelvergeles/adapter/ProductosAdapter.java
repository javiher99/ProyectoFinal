package com.example.rincondelvergeles.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rincondelvergeles.R;
import com.example.rincondelvergeles.methods.Methods;
import com.example.rincondelvergeles.model.Comanda;
import com.example.rincondelvergeles.model.Factura;
import com.example.rincondelvergeles.model.Producto;
import com.example.rincondelvergeles.view.MainViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProductosAdapter extends RecyclerView.Adapter<ProductosAdapter.MyViewHolder>{

    private LayoutInflater inflater;
    private ArrayList<Producto> productos;
    Context context;
    MainViewModel viewModel;
    LifecycleOwner lifecycleOwner;
    long idEmp;
    Activity activity;
    AlertDialog alertDialog, alertDialog2;
    Factura factura;
    SharedPreferences sharedPreferences;

    public ProductosAdapter(ArrayList<Producto> productos,
                            Context context,
                            MainViewModel viewModel,
                            LifecycleOwner lifecycleOwner,
                            long idEmp,
                            Activity activity,
                            Factura factura,
                            SharedPreferences sharedPreferences) {
        this.productos = productos;
        this.context = context;
        this.viewModel = viewModel;
        this.lifecycleOwner = lifecycleOwner;
        this.idEmp = idEmp;
        this.activity = activity;
        this.factura = factura;
        this.sharedPreferences = sharedPreferences;
    }

    @NonNull
    @Override
    public ProductosAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.itemproducto, parent, false);//Lleva false porque el adapter se encarga de añadirlo a la vista
        ProductosAdapter.MyViewHolder vh = new ProductosAdapter.MyViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductosAdapter.MyViewHolder holder, final int position) {
        holder.tvProducto.setText(String.valueOf(productos.get(position).getNombre()));
        holder.tvPrecioUd.setText(productos.get(position).getPrecio()+" €");
        holder.clProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog2 = Methods.cargandoMostrar(context, context.getResources().getString(R.string.creandoComanda)).create();
                new MaterialAlertDialogBuilder(context)
                        .setTitle(context.getResources().getString(R.string.crearNuevaComandaTit))
                        .setMessage(context.getResources().getString(R.string.aniadir) + " " + productos.get(position).getNombre() + " " + context.getResources().getString(R.string.aLaFactura))
                        .setPositiveButton(context.getResources().getString(R.string.si), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                alertDialog = Methods.numberPickerDialog(context, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        alertDialog2.show();
                                        final Comanda comanda = new Comanda(0,
                                                factura.getId(),
                                                productos.get(position).getId(),
                                                productos.get(position).getNombre(),
                                                idEmp,
                                                Methods.numberPicker.getValue(),
                                                0,
                                                productos.get(position).getPrecio() * Methods.numberPicker.getValue());
                                        viewModel.crearComanda(comanda).observe(lifecycleOwner, new Observer<Long>() {
                                            @Override
                                            public void onChanged(Long aLong) {
                                                if (aLong > 0) {
                                                    factura.setTotal(factura.getTotal() + productos.get(position).getPrecio() * Methods.numberPicker.getValue());
                                                    viewModel.updateFactura(factura, factura.getId()).observe(lifecycleOwner, new Observer<Integer>() {
                                                        @Override
                                                        public void onChanged(Integer integer) {
                                                            if (integer == factura.getId()) {
                                                                Gson gson = new Gson();
                                                                String json = gson.toJson(factura);
                                                                sharedPreferences.edit().putString(context.getResources().getString(R.string.factura), json).commit();
                                                                alertDialog2.cancel();
                                                                Toast.makeText(context, context.getResources().getString(R.string.comandaCreada), Toast.LENGTH_LONG).show();
                                                                activity.finish();
                                                            }
                                                        }
                                                    });
                                                } else {
                                                    Toast.makeText(context, context.getResources().getString(R.string.comandaNoCreada), Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });
                                    }
                                }).create();
                                alertDialog.show();
                            }
                        })
                        .setNegativeButton(context.getResources().getString(R.string.cancelar), null)
                        .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{//ViewHolder es solo para las vistas individuales
        TextView tvProducto, tvPrecioUd;
        ConstraintLayout clProducto;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProducto = itemView.findViewById(R.id.tvProducto);
            tvPrecioUd = itemView.findViewById(R.id.tvPrecioUnidad);
            clProducto = itemView.findViewById(R.id.clProducto);
        }
    }
}
