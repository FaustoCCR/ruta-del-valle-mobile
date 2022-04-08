package com.ruta_del_valle.app_hosteria.rest_api.model;

public class Habitacion {

    private long id_habitacion;
    private int num_habitacion;
    private String planta;
    private String tipo_habitacion;
    private String descripcion;
    private String estado;
    private double costo_noche;

    public Habitacion(){

    }

    public Habitacion(long id_habitacion, int num_habitacion, String planta, String tipo_habitacion, String descripcion, String estado, double costo_noche) {
        this.id_habitacion = id_habitacion;
        this.num_habitacion = num_habitacion;
        this.planta = planta;
        this.tipo_habitacion = tipo_habitacion;
        this.descripcion = descripcion;
        this.estado = estado;
        this.costo_noche = costo_noche;
    }

    public long getId_habitacion() {
        return id_habitacion;
    }

    public void setId_habitacion(long id_habitacion) {
        this.id_habitacion = id_habitacion;
    }

    public int getNum_habitacion() {
        return num_habitacion;
    }

    public void setNum_habitacion(int num_habitacion) {
        this.num_habitacion = num_habitacion;
    }

    public String getPlanta() {
        return planta;
    }

    public void setPlanta(String planta) {
        this.planta = planta;
    }

    public String getTipo_habitacion() {
        return tipo_habitacion;
    }

    public void setTipo_habitacion(String tipo_habitacion) {
        this.tipo_habitacion = tipo_habitacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getCosto_noche() {
        return costo_noche;
    }

    public void setCosto_noche(double costo_noche) {
        this.costo_noche = costo_noche;
    }
}
