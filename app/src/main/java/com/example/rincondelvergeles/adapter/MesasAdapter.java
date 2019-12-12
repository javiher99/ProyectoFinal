package com.example.rincondelvergeles.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rincondelvergeles.R;
import com.example.rincondelvergeles.methods.Methods;
import com.example.rincondelvergeles.model.Factura;
import com.example.rincondelvergeles.model.Mesa;
import com.example.rincondelvergeles.view.MainViewModel;

import java.util.ArrayList;
import java.util.Calendar;

public class MesasAdapter extends RecyclerView.Adapter<MesasAdapter.MyViewHolder>{
    private LayoutInflater inflater;
    private ArrayList<Mesa> mesas;
    MainViewModel viewModel;
    long idEmp;
    Activity activity;
    Context context;
    LifecycleOwner lifecycleOwner;
    AlertDialog alertDialog;

    public MesasAdapter(ArrayList<Mesa> mesas, MainViewModel viewModel, long idEmp, Activity activity, Context c, LifecycleOwner lifecycleOwner) {
        this.mesas = mesas;
        this.viewModel = viewModel;
        this.idEmp = idEmp;
        this.activity = activity;
        this.context = c;
        this.lifecycleOwner = lifecycleOwner;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.itemmesa, parent, false);//Lleva false porque el adapter se encarga de añadirlo a la vista
        MyViewHolder vh = new MyViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.tvMesa.setText(mesas.get(position).getMesa());
        holder.lMesa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog = Methods.cargandoMostrar(context, context.getResources().getString(R.string.creandoFactura)).create();
                alertDialog.show();
                Mesa mesa = mesas.get(position);
                mesa.setOcupada();
                viewModel.updateMesaStatus(mesa, mesa.getId());
                Calendar rightNow = Calendar.getInstance();
                String hora = "";
                String minuto = "";
                String segundo = "";
                if (rightNow.get(Calendar.HOUR_OF_DAY) < 10) {
                    hora = context.getResources().getString(R.string.zero) + rightNow.get(Calendar.HOUR_OF_DAY);
                } else {
                    hora = String.valueOf(rightNow.get(Calendar.HOUR_OF_DAY));
                }
                if (rightNow.get(Calendar.MINUTE) < 10) {
                    minuto = context.getResources().getString(R.string.zero) + rightNow.get(Calendar.MINUTE);
                } else {
                    minuto = String.valueOf(rightNow.get(Calendar.MINUTE));
                }
                if (rightNow.get(Calendar.SECOND) < 10) {
                    segundo = context.getResources().getString(R.string.zero) + rightNow.get(Calendar.SECOND);
                } else {
                    segundo = String.valueOf(rightNow.get(Calendar.SECOND));
                }
                String now = hora + ":" + minuto + ":" + segundo;
                Factura factura = new Factura(0,
                        now,
                        context.getResources().getString(R.string.none),
                        0.0,
                        mesa.getId(),
                        idEmp,
                        1);
                viewModel.postFactura(factura).observe(lifecycleOwner, new Observer<Long>() {
                    @Override
                    public void onChanged(Long aLong) {
                        if (aLong > 0) {
                            alertDialog.cancel();
                            Toast.makeText(context, context.getResources().getString(R.string.facturaCreada), Toast.LENGTH_LONG).show();
                            activity.finish();
                        } else {
                            Toast.makeText(context, context.getResources().getString(R.string.facturaNoCreada), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return mesas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{//ViewHolder es solo para las vistas individuales
        TextView tvMesa;
        ConstraintLayout lMesa;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMesa = itemView.findViewById(R.id.textView);
            lMesa = itemView.findViewById(R.id.layoutMesa);
        }
    }
}
