package com.example.rincondelvergeles.rest;

import com.example.rincondelvergeles.model.*;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Client {
    @DELETE("mesa/{id}")
    Call<Integer> deleteMesa(@Path("id") long id);

    @GET("mesa/{id}")
    Call<Mesa> getMesa(@Path("id") long id);

    @GET("mesa")
    Call<ArrayList<Mesa>> getMesas();

    @GET("mesa/ocupada/{status}")
    Call<ArrayList<Mesa>> getSomeMesas(@Path("status") long status);

    @POST("mesa")
    Call<Long> postMesa(@Body Mesa mesa);

    @PUT("mesa/{id}")
    Call<Integer> putMesa(@Path("id") long id, @Body Mesa mesa);


    @DELETE("producto/{id}")
    Call<Integer> deleteProducto(@Path("id") long id);

    @GET("producto/{id}")
    Call<Producto> getProducto(@Path("id") long id);

    @GET("producto")
    Call<ArrayList<Producto>> getProductos();

    @GET("producto/categoria/{id}")
    Call<ArrayList<Producto>> getProductosCategoria();

    @GET("producto/getsome/{ids}")
    Call<ArrayList<Producto>> getSomeProductos(@Path("ids") String ids);

    @POST("producto")
    Call<Long> postProducto(@Body Producto producto);

    @PUT("producto/{id}")
    Call<Integer> putProducto(@Path("id") long id, @Body Producto producto);


    @DELETE("categoria/{id}")
    Call<Integer> deleteCategoria(@Path("id") long id);

    @GET("categoria/{id}")
    Call<Categoria> getCategoria(@Path("id") long id);

    @GET("categoria")
    Call<ArrayList<Categoria>> getCategorias();

    @POST("categoria")
    Call<Long> postCategoria(@Body Categoria categoria);

    @PUT("categoria/{id}")
    Call<Integer> putCategoria(@Path("id") long id, @Body Categoria categoria);


    @DELETE("empleado/{id}")
    Call<Integer> deleteEmpleado(@Path("id") long id);

    @GET("empleado/{id}")
    Call<Empleado> getEmpleado(@Path("id") long id);

    @GET("empleado")
    Call<ArrayList<Empleado>> getEmpleados();

    @POST("empleado")
    Call<Long> postEmpleado(@Body Empleado empleado);

    @PUT("empleado/{id}")
    Call<Integer> putEmpleado(@Path("id") long id, @Body Empleado empleado);


    @DELETE("factura/{id}")
    Call<Integer> deleteFactura(@Path("id") long id);

    @GET("factura/{id}")
    Call<Factura> getFactura(@Path("id") long id);

    @GET("factura/abierta/1")
    Call<ArrayList<Factura>> getFacturas();

    @GET("factura")
    Call<ArrayList<Factura>> getAllFacturas();

    @POST("factura")
    Call<Long> postFactura(@Body Factura factura);

    @PUT("factura/{id}")
    Call<Integer> putFactura(@Path("id") long id, @Body Factura factura);

    @GET("factura/mesa/{id}")
    Call<ArrayList<Factura>> getSpecificFacturas(@Path("id") long id);


    @DELETE("comanda/{id}")
    Call<Integer> deleteComanda(@Path("id") long id);

    @GET("comanda/{id}")
    Call<Comanda> getComanda(@Path("id") long id);

    @GET("comanda")
    Call<ArrayList<Comanda>> getComandas();

    @GET("comanda/factura/{id}")
    Call<ArrayList<Comanda>> getSpecificComandas(@Path("id") long id);

    @POST("comanda")
    Call<Long> postComanda(@Body Comanda comanda);

    @PUT("comanda/{id}")
    Call<Integer> putComanda(@Path("id") long id, @Body Comanda comanda);


    @DELETE("historial/{id}")
    Call<Integer> deleteHistorial(@Path("id") long id);

    @GET("historial/{id}")
    Call<Historial> getHistorial(@Path("id") long id);

    @GET("historial")
    Call<ArrayList<Historial>> getHistorials();

    @POST("historial")
    Call<Long> postHistorial(@Body Historial historial);

    @PUT("historial/{id}")
    Call<Integer> putHistorial(@Path("id") long id, @Body Historial historial);
}
