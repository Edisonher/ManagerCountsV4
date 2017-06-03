package com.example.jacobo.managercounts;

/**
 * Created by admin on 13/03/2017.
 */

public class Lista_Entrada {
    private int idImagen;
    private String nombre, descripcion,valor,estado;

    public Lista_Entrada(int idImagen, String nombre, String descripcion,String valor,String estado) {
        this.idImagen = idImagen;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.valor = valor;
        this.estado = estado;
    }

    public int getIdImagen() {
        return idImagen;
    }

    public void setIdImagen(int idImagen) {
        this.idImagen = idImagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}


