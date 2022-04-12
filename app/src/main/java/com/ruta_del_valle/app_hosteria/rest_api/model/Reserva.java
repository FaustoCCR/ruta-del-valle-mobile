package com.ruta_del_valle.app_hosteria.rest_api.model;

import java.util.Date;

public class Reserva {

    private String username;
    private long id_habitacion;
    private Date fecha_reserva;
    private String fecha_ingreso;
    private String fecha_salida;
    private int adultos;
    private int ninos;
    private double costo_alojamiento;
    private String observaciones;
    private String estado;

    public Reserva(String username, long id_habitacion, String fecha_ingreso, String fecha_salida, int adultos, int ninos, double costo_alojamiento, String observaciones, String estado) {
        this.username = username;
        this.id_habitacion = id_habitacion;
        this.fecha_ingreso = fecha_ingreso;
        this.fecha_salida = fecha_salida;
        this.adultos = adultos;
        this.ninos = ninos;
        this.costo_alojamiento = costo_alojamiento;
        this.observaciones = observaciones;
        this.estado = estado;
    }

    public Reserva(String username, long id_habitacion, Date fecha_reserva, String fecha_ingreso, String fecha_salida, int adultos, int ninos, double costo_alojamiento, String observaciones, String estado) {
        this.username = username;
        this.id_habitacion = id_habitacion;
        this.fecha_reserva = fecha_reserva;
        this.fecha_ingreso = fecha_ingreso;
        this.fecha_salida = fecha_salida;
        this.adultos = adultos;
        this.ninos = ninos;
        this.costo_alojamiento = costo_alojamiento;
        this.observaciones = observaciones;
        this.estado = estado;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getId_habitacion() {
        return id_habitacion;
    }

    public void setId_habitacion(long id_habitacion) {
        this.id_habitacion = id_habitacion;
    }

    public Date getFecha_reserva() {
        return fecha_reserva;
    }

    public void setFecha_reserva(Date fecha_reserva) {
        this.fecha_reserva = fecha_reserva;
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

    public double getCosto_alojamiento() {
        return costo_alojamiento;
    }

    public void setCosto_alojamiento(double costo_alojamiento) {
        this.costo_alojamiento = costo_alojamiento;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
