package com.example.rincondelvergeles.repository;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.rincondelvergeles.model.*;
import com.example.rincondelvergeles.rest.Client;

import java.io.IOException;
import java.net.CookieManager;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Repository {

    public Client apiClient;
    public final String url = "3.95.138.148";
    private Mesa mesa;
    private MutableLiveData<List<Mesa>> liveDataMesa = new MutableLiveData<>();
    private MutableLiveData<Mesa> mesaMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Factura>> facturaMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Comanda>> comandaMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Producto>> someProductosLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Empleado>> empleadosLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Producto>> liveDataProductos = new MutableLiveData<>();
    private MutableLiveData<List<Categoria>> liveDataCategoria = new MutableLiveData<>();
    private MutableLiveData<List<Historial>> liveDataHistorial = new MutableLiveData<>();
    private MutableLiveData<Long> status = new MutableLiveData<>();
    private MutableLiveData<Integer> status1 = new MutableLiveData<>();

    public Repository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://" + url + "/web/RinconDelVergeles/public/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiClient = retrofit.create(Client.class);
    }

    public void fetchEmpleadosList() {
        Call<ArrayList<Empleado>> call = apiClient.getEmpleados();

        call.enqueue(new Callback<ArrayList<Empleado>>() {
            @Override
            public void onResponse(Call<ArrayList<Empleado>> call, Response<ArrayList<Empleado>> response) {
                empleadosLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Empleado>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void postComanda(Comanda comanda) {
        Call<Long> call = apiClient.postComanda(comanda);
        call.enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                status.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void fetchMesaList() {
        Call<ArrayList<Mesa>> call = apiClient.getMesas();
        call.enqueue(new Callback<ArrayList<Mesa>>() {
            @Override
            public void onResponse(Call<ArrayList<Mesa>> call, Response<ArrayList<Mesa>> response) {
                    liveDataMesa.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Mesa>> call, Throwable t) {
                    t.printStackTrace();
            }
        });
    }

    public void fetchHistorialList() {
        Call<ArrayList<Historial>> call = apiClient.getHistorials();
        call.enqueue(new Callback<ArrayList<Historial>>() {
            @Override
            public void onResponse(Call<ArrayList<Historial>> call, Response<ArrayList<Historial>> response) {
                liveDataHistorial.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Historial>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void fetchSomeMesas(long status) {
        Call<ArrayList<Mesa>> call = apiClient.getSomeMesas(status);
        call.enqueue(new Callback<ArrayList<Mesa>>() {
            @Override
            public void onResponse(Call<ArrayList<Mesa>> call, Response<ArrayList<Mesa>> response) {
                liveDataMesa.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Mesa>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void fetchSomeProductos(String ids) {
        Call<ArrayList<Producto>> call = apiClient.getSomeProductos(ids);
        call.enqueue(new Callback<ArrayList<Producto>>() {
            @Override
            public void onResponse(Call<ArrayList<Producto>> call, Response<ArrayList<Producto>> response) {
                someProductosLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Producto>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void fetchProductosList() {
        Call<ArrayList<Producto>> call = apiClient.getProductos();
        call.enqueue(new Callback<ArrayList<Producto>>() {
            @Override
            public void onResponse(Call<ArrayList<Producto>> call, Response<ArrayList<Producto>> response) {
                liveDataProductos.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Producto>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void getSpecificMesa(long id) {
        Call<Mesa> call= apiClient.getMesa(id);
        call.enqueue(new Callback<Mesa>() {
            @Override
            public void onResponse(Call<Mesa> call, Response<Mesa> response) {
                mesaMutableLiveData.setValue(response.body());
                mesa = response.body();
            }

            @Override
            public void onFailure(Call<Mesa> call, Throwable t) {
                mesa = null;
                t.printStackTrace();
            }
        });
    }

    public void getSpecificComandas(long id) {
        Call<ArrayList<Comanda>> call = apiClient.getSpecificComandas(id);
        call.enqueue(new Callback<ArrayList<Comanda>>() {
            @Override
            public void onResponse(Call<ArrayList<Comanda>> call, Response<ArrayList<Comanda>> response) {
                comandaMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Comanda>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void getSpecificFacturas(long id) {
        Call<ArrayList<Factura>> call = apiClient.getSpecificFacturas(id);
        call.enqueue(new Callback<ArrayList<Factura>>() {
            @Override
            public void onResponse(Call<ArrayList<Factura>> call, Response<ArrayList<Factura>> response) {
                facturaMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Factura>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void getFacturas() {
        Call<ArrayList<Factura>> call = apiClient.getFacturas();
        call.enqueue(new Callback<ArrayList<Factura>>() {
            @Override
            public void onResponse(Call<ArrayList<Factura>> call, Response<ArrayList<Factura>> response) {
                facturaMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Factura>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }



    public void postFactura(Factura factura) {
        Call<Long> call = apiClient.postFactura(factura);

        call.enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                status.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {
            }
        });
    }

    public void postHistorial(Historial historial) {
        Call<Long> call = apiClient.postHistorial(historial);
        call.enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                status.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {
            }
        });
    }

    public void deleteFactura(long id) {
        Call<Integer> call = apiClient.deleteFactura(id);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                status1.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
            }
        });
    }

    public void updateMesaStatus(Mesa mesa, long id) {
        Call<Integer> call = apiClient.putMesa(id, mesa);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                status1.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
            }
        });
    }

    public void putFactura(Factura factura, long id) {
        Call<Integer> call = apiClient.putFactura(id, factura);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                status1.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
            }
        });
    }

    public void getCategorias() {
        Call<ArrayList<Categoria>> call = apiClient.getCategorias();
        call.enqueue(new Callback<ArrayList<Categoria>>() {
            @Override
            public void onResponse(Call<ArrayList<Categoria>> call, Response<ArrayList<Categoria>> response) {
                liveDataCategoria.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Categoria>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public LiveData<List<Mesa>> liveDataMesas() {
        return liveDataMesa;
    }

    public LiveData<List<Producto>> liveDataProductos() {
        return liveDataProductos;
    }

    public LiveData<Mesa> mesaLiveData(long id) {
        getSpecificMesa(id);
        return mesaMutableLiveData;
    }

    public LiveData<List<Factura>> facturaLiveData(long id) {
        getSpecificFacturas(id);
        return facturaMutableLiveData;
    }

    public LiveData<List<Comanda>> specificComandasLiveData(long id) {
        getSpecificComandas(id);
        return comandaMutableLiveData;
    }

    public LiveData<List<Producto>> getSomeProductosLiveData(String ids) {
        fetchSomeProductos(ids);
        return someProductosLiveData;
    }

    public LiveData<List<Empleado>> empleadosLiveData() {
        fetchEmpleadosList();
        return empleadosLiveData;
    }

    public LiveData<List<Factura>> allFacturaLiveData() {
        getFacturas();
        return facturaMutableLiveData;
    }

    public LiveData<List<Mesa>> fetchSpecificMesas(long status){
        fetchSomeMesas(status);
        return liveDataMesa;
    }

    public MutableLiveData<Integer> borrarFactura(long id) {
        deleteFactura(id);
        return status1;
    }

    public MutableLiveData<Integer> updateMesa(Mesa mesa, long id) {
        updateMesaStatus(mesa, id);
        return status1;
    }

    public MutableLiveData<Integer> updateFactura(Factura factura, long id) {
        putFactura(factura, id);
        return status1;
    }

    public MutableLiveData<Long> crearfactura(Factura factura) {
        postFactura(factura);
        return status;
    }

    public MutableLiveData<Long> crearComanda(Comanda comanda) {
        postComanda(comanda);
        return status;
    }

    public MutableLiveData<Long> crearHistorial(Historial historial) {
        postHistorial(historial);
        return status;
    }

    public MutableLiveData<List<Producto>> getAllProductos() {
        fetchProductosList();
        return liveDataProductos;
    }

    public MutableLiveData<List<Historial>> getAllHistorial() {
        fetchHistorialList();
        return liveDataHistorial;
    }

    public MutableLiveData<List<Categoria>> getAllCategorias() {
        getCategorias();
        return liveDataCategoria;
    }
}
