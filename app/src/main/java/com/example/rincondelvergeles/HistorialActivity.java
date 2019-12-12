package com.example.rincondelvergeles;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TimePicker;

import com.example.rincondelvergeles.adapter.HistorialAdapter;
import com.example.rincondelvergeles.methods.Methods;
import com.example.rincondelvergeles.model.Historial;
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
import java.util.Calendar;
import java.util.List;

public class HistorialActivity extends AppCompatActivity {

    AlertDialog alertDialog;
    public HistorialAdapter adapter;
    public ArrayList<Historial> historialArrayList;
    public MainViewModel viewModel;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FloatingActionButton fab, refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.rvHistorial);
        fab = findViewById(R.id.filterByHour);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                final int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(HistorialActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String hora = String.valueOf(selectedHour);
                        if (selectedHour < 10) {
                            hora = "0" + selectedHour;
                        }
                        String minuto = String.valueOf(selectedMinute);
                        if (selectedMinute < 10) {
                            minuto = "0" + selectedMinute;
                        }
                        alertDialog = Methods.cargandoMostrar(HistorialActivity.this, getResources().getString(R.string.filtering)).create();
                        alertDialog.show();
                        ArrayList<Historial> filteredHistorial = new ArrayList<>();
                        for (int i = 0; i < historialArrayList.size(); i++) {
                            String horaMinutoInicio = historialArrayList.get(i).getHoraInicio().substring(0, 5);
                            String horaMinutoFinal = historialArrayList.get(i).getHoraCierre().substring(0, 5);
                            if (horaMinutoInicio.equals(hora + ":" + minuto)
                                    || horaMinutoFinal.equals(hora + ":" + minuto)) {
                                filteredHistorial.add(historialArrayList.get(i));
                            }
                        }
                        adapter = new HistorialAdapter(filteredHistorial, HistorialActivity.this);
                        recyclerView.setAdapter(adapter);
                        alertDialog.cancel();
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle(getResources().getString(R.string.filtrarHoraMinuto));
                mTimePicker.show();
            }
        });
        alertDialog = Methods.cargandoMostrar(this, getResources().getString(R.string.cargandoHistorial)).create();
        historialArrayList = new ArrayList<>();

        refresh = findViewById(R.id.refresh_bt3);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.show();
                bajarDatos();
            }
        });

        alertDialog.show();
        initComponents();
        bajarDatos();
    }

    void bajarDatos() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.historialLiveData().observe(HistorialActivity.this, new Observer<List<Historial>>() {
            @Override
            public void onChanged(List<Historial> historials) {
                if (historialArrayList != historials) {
                    historialArrayList = (ArrayList<Historial>) historials;
                    adapter = new HistorialAdapter(historialArrayList, HistorialActivity.this);
                    recyclerView.setAdapter(adapter);
                    alertDialog.cancel();
                }
            }
        });
    }

    void initComponents() {
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

}
