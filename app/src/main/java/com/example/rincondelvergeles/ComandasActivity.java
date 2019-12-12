package com.example.rincondelvergeles;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.print.PrintManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.rincondelvergeles.adapter.ComandasAdapter;
import com.example.rincondelvergeles.adapter.MyPrintDocumentAdapter;
import com.example.rincondelvergeles.methods.Methods;
import com.example.rincondelvergeles.model.Comanda;
import com.example.rincondelvergeles.model.Factura;
import com.example.rincondelvergeles.model.Historial;
import com.example.rincondelvergeles.model.Mesa;
import com.example.rincondelvergeles.view.MainViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

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

public class ComandasActivity extends AppCompatActivity {

    private RecyclerView rvComandas;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Comanda> misComandas ;
    private ComandasAdapter myAdapter;
    MainViewModel viewModel;
    long idFactura;
    long idMesa;
    Factura factura;
    AlertDialog alertDialog;
    Toolbar toolbar;
    long idEmp;
    SharedPreferences sharedPreferences;
    FloatingActionButton refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comandas);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        alertDialog = Methods.cargandoMostrar(this, getResources().getString(R.string.cargandoComandas)).create();
        misComandas = new ArrayList<>();
        sharedPreferences = getSharedPreferences(getResources().getString(R.string.packageName), Context.MODE_PRIVATE);
        idEmp = sharedPreferences.getLong(getResources().getString(R.string.id), 0);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ComandasActivity.this, ProductosActivity.class);
                intent.putExtra(getResources().getString(R.string.idFactura), idFactura).putExtra(getResources().getString(R.string.idEmp), idEmp);
                intent.putExtra(getResources().getString(R.string.factura), factura);
                startActivity(intent);
                ComandasActivity.this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        refresh = findViewById(R.id.refresh_bt2);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.show();
                bajarComandas(idFactura);
            }
        });

        initComponentes();
    }

    @Override
    protected void onResume() {
        super.onResume();
        alertDialog.show();
        Gson gson = new Gson();
        String json = sharedPreferences.getString(getResources().getString(R.string.factura), "");
        factura = gson.fromJson(json, Factura.class);
        idFactura = factura.getId();
        idMesa = factura.getMesa();
        toolbarTexto(idFactura);
        bajarComandas(idFactura);
    }

    void toolbarTexto(long idFactura) {
        getSupportActionBar().setTitle(getResources().getString(R.string.toolbarTitle) + " " + idFactura);
        toolbar.setTitleTextColor(getResources().getColor(R.color.blanco));
    }

    void bajarComandas(long id) {
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.specificComandasLiveData(id).observe(ComandasActivity.this, new Observer<List<Comanda>>() {
            @Override
            public void onChanged(List<Comanda> comandas) {
                if (misComandas != comandas) {
                    Log.v("Comandas", "renewed");
                    misComandas = (ArrayList<Comanda>) comandas;
                    myAdapter = new ComandasAdapter(misComandas, ComandasActivity.this);
                    rvComandas.setAdapter(myAdapter);
                    alertDialog.cancel();
                }
            }
        });
    }

    private void initComponentes() {
        rvComandas = findViewById(R.id.rvComandas);
        //Asignarle un layout manager (my_recycler_view.xml)
        layoutManager = new LinearLayoutManager(this);//Por defecto con this en el constructor hace una lista vertical
        //layoutManager = new GridLayoutManager(this, 2);//El segundo parámetro es el número de columnas
        rvComandas.setLayoutManager(layoutManager);
        //Asignarle un adaptador
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_comandas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cerrar_factura:
                new MaterialAlertDialogBuilder(this)
                        .setTitle(getResources().getString(R.string.askClosing))
                        .setPositiveButton(getResources().getString(R.string.si), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                alertDialog = Methods.cargandoMostrar(ComandasActivity.this, getResources().getString(R.string.borrandoFactura)).create();
                                alertDialog.show();
                                Calendar rightNow = Calendar.getInstance();
                                String hora = "";
                                String minuto = "";
                                String segundo = "";
                                if (rightNow.get(Calendar.HOUR_OF_DAY) < 10) {
                                    hora = getResources().getString(R.string.zero) + rightNow.get(Calendar.HOUR_OF_DAY);
                                } else {
                                    hora = String.valueOf(rightNow.get(Calendar.HOUR_OF_DAY));
                                }
                                if (rightNow.get(Calendar.MINUTE) < 10) {
                                    minuto = getResources().getString(R.string.zero) + rightNow.get(Calendar.MINUTE);
                                } else {
                                    minuto = String.valueOf(rightNow.get(Calendar.MINUTE));
                                }
                                if (rightNow.get(Calendar.SECOND) < 10) {
                                    segundo = getResources().getString(R.string.zero) + rightNow.get(Calendar.SECOND);
                                } else {
                                    segundo = String.valueOf(rightNow.get(Calendar.SECOND));
                                }
                                final String now = hora + ":" + minuto + ":" + segundo;
                                final Historial historial = new Historial(0,
                                        factura.getHoraInicio(),
                                        now,
                                        factura.getTotal(),
                                        factura.getMesa(),
                                        factura.getIdEmpleadoInicio(),
                                        idEmp
                                );
                                viewModel.mesaLiveData(idMesa)
                                        .observe(ComandasActivity.this, new Observer<Mesa>() {
                                            @Override
                                            public void onChanged(final Mesa mesa) {
                                                mesa.setLibre();
                                                viewModel.updateMesaStatus(mesa, mesa.getId())
                                                        .observe(ComandasActivity.this, new Observer<Integer>() {
                                                    @Override
                                                    public void onChanged(Integer integer) {
                                                        if (integer == mesa.getId()) {
                                                            factura.setHoraCierre(now);
                                                            factura.setIdEmpleadoFinal(idEmp);
                                                            viewModel.updateFactura(factura, idFactura)
                                                                    .observe(ComandasActivity.this, new Observer<Integer>() {
                                                                @Override
                                                                public void onChanged(Integer integer) {
                                                                    if (integer == factura.getId()) {
                                                                        viewModel.postHistorial(historial)
                                                                                .observe(ComandasActivity.this, new Observer<Long>() {
                                                                            @Override
                                                                            public void onChanged(Long aLong) {
                                                                                if (aLong > 0) {
                                                                                    Toast.makeText(ComandasActivity.this, getResources().getString(R.string.facturaBorrada), Toast.LENGTH_LONG).show();
                                                                                    alertDialog.cancel();
                                                                                    finish();
                                                                                }
                                                                            }
                                                                        }); //fin observer historial
                                                                    }
                                                                }
                                                            });//fin observer factura
                                                        }
                                                    }
                                                }); //fin observer mesa update
                                            }
                                        }); //fin observer get mesa
                            }
                        })
                        .setNeutralButton(getResources().getString(R.string.cancelar), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FacturasActivity.myAdapter.notifyDataSetChanged();
                            }
                        })
                        .show();
                return true;

            case R.id.enviar_correo:
                String correo = getResources().getString(R.string.comandasDeLaFactura) + " " + factura.getId() + ":\n";
                for (int i = 0; i < misComandas.size(); i++) {
                    correo += misComandas.get(i).getNombreProducto() + ": "
                            + misComandas.get(i).getUnidades() + " " + getResources().getString(R.string.unidadesComa) + " "
                            + misComandas.get(i).getPrecio() + " €\n";
                }
                correo += getResources().getString(R.string.totalDialog) + " " + factura.getTotal() + "€";
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse(getResources().getString(R.string.mailto)));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.facturaNum) + " " + factura.getId());
                emailIntent.putExtra(Intent.EXTRA_TEXT, correo);
                try {
                    startActivity(Intent.createChooser(emailIntent, getResources().getString(R.string.mandarCorreo)));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(ComandasActivity.this,
                            getResources().getString(R.string.noneClients), Toast.LENGTH_SHORT).show();
                }
                return true;

            case R.id.imprimir_factura:
                Bitmap bitmap = Methods.redimensionarImagen(BitmapFactory.decodeResource(this.getResources(), R.drawable.logo));
                ArrayList<Comanda> comandasImp = misComandas;
                PrintManager printManager = (PrintManager) this
                        .getSystemService(Context.PRINT_SERVICE);
                String jobName = this.getString(R.string.app_name) + " " + getResources().getString(R.string.document);
                printManager.print(jobName, new MyPrintDocumentAdapter(this,
                                comandasImp,
                                bitmap,
                                String.valueOf(factura.getTotal()),
                                factura.getHoraInicio()),
                        null); //
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
