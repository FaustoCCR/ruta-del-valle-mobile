package com.ruta_del_valle.app_hosteria.rest_api.model;

public class MiReserva {

    private String cliente;
    private int num_habitacion;
    private String tipo_habitacion;
    private int adultos;
    private int ninos;
    private String fecha_ingreso;
    private String fecha_salida;
    private double costo_alojamiento;

    public MiReserva(){

    }

    public MiReserva(String cliente, int num_habitacion, String tipo_habitacion, int adultos, int ninos, String fecha_ingreso, String fecha_salida, double costo_alojamiento) {
        this.cliente = cliente;
        this.num_habitacion = num_habitacion;
        this.tipo_habitacion = tipo_habitacion;
        this.adultos = adultos;
        this.ninos = ninos;
        this.fecha_ingreso = fecha_ingreso;
        this.fecha_salida = fecha_salida;
        this.costo_alojamiento = costo_alojamiento;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public int getNum_habitacion() {
        return num_habitacion;
    }

    public void setNum_habitacion(int num_habitacion) {
        this.num_habitacion = num_habitacion;
    }

    public String getTipo_habitacion() {
        return tipo_habitacion;
    }

    public void setTipo_habitacion(String tipo_habitacion) {
        this.tipo_habitacion = tipo_habitacion;
    }

    public int getAdultos() {
        return adultos;
    }

    public void setAdultos(int adultos) {
        this.adultos = adultos;
    }

    public int getNinos() {
        return ninos;
    }

    public void setNinos(int ninos) {
        this.ninos = ninos;
    }

    public String getFecha_ingreso() {
        return fecha_ingreso;
    }

    public void setFecha_ingreso(String fecha_ingreso) {
        this.fecha_ingreso = fecha_ingreso;
    }

    public String getFecha_salida() {
        return fecha_salida;
    }

    public void setFecha_salida(String fecha_salida) {
        this.fecha_salida = fecha_salida;
    }

    public double getCosto_alojamiento() {
        return costo_alojamiento;
    }

    public void setCosto_alojamiento(double costo_alojamiento) {
        this.costo_alojamiento = costo_alojamiento;
    }
}
