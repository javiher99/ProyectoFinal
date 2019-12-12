package com.example.rincondelvergeles.view;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.rincondelvergeles.model.*;
import com.example.rincondelvergeles.repository.*;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private Repository repository;

    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository();
    }

    public LiveData<List<Mesa>> getLiveDataMesasList() {
        return repository.liveDataMesas();
    }

    public LiveData<List<Producto>> getLiveDataProductosList() {
        return repository.liveDataProductos();
    }

    public LiveData<Mesa> mesaLiveData(long id) {
        return repository.mesaLiveData(id);
    }

    public LiveData<List<Factura>> specificFacturasLiveData(long id) {
        return  repository.facturaLiveData(id);
    }

    public LiveData<List<Comanda>> specificComandasLiveData(long id) {
        return repository.specificComandasLiveData(id);
    }

    public LiveData<List<Producto>> getSomeProductos(String ids) {
        return repository.getSomeProductosLiveData(ids);
    }

    public LiveData<List<Factura>> getAllFacturas() {
        return repository.allFacturaLiveData();
    }

    public LiveData<List<Mesa>> getMesasLibres() {
        return repository.fetchSpecificMesas(0);
    }

    public LiveData<List<Mesa>> getMesasOcupadas() {
        return repository.fetchSpecificMesas(1);
    }

    public LiveData<List<Empleado>> empleadosLiveData() {
        return repository.empleadosLiveData();
    }

    public LiveData<List<Producto>> productosLiveData() {
        return repository.getAllProductos();
    }

    public LiveData<List<Historial>> historialLiveData() {
        return repository.getAllHistorial();
    }

    public LiveData<List<Categoria>> categoriaLiveData() {
        return repository.getAllCategorias();
    }

    public MutableLiveData<Integer> deleteFactura(long id) {
        return repository.borrarFactura(id);
    }

    public MutableLiveData<Integer> updateMesaStatus(Mesa mesa, long id) {
        return repository.updateMesa(mesa, id);
    }

    public MutableLiveData<Integer> updateFactura(Factura factura, long id){
        return repository.updateFactura(factura, id);
    }

    public MutableLiveData<Long> postFactura(Factura factura) {
        return repository.crearfactura(factura);
    }

    public MutableLiveData<Long> crearComanda(Comanda comanda) {
        return repository.crearComanda(comanda);
    }

    public MutableLiveData<Long> postHistorial(Historial historial) {
        return repository.crearHistorial(historial);
    }
}
