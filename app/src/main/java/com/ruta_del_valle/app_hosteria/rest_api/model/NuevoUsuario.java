package com.ruta_del_valle.app_hosteria.rest_api.model;

public class NuevoUsuario {

    private long id_user;
    private String dni;
    private String nombre;
    private String email;
    private String telefono;
    private String username;
    private String password;
    private boolean estado;
    private String rol;

    public NuevoUsuario(){

    }

    public NuevoUsuario(String dni, String nombre, String email, String telefono, String username, String password,String rol) {
        this.dni = dni;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.username = username;
        this.password = password;
        this.rol = rol;
    }

    public NuevoUsuario(String dni, String nombre, String email, String telefono, String username, String password, boolean estado, String rol) {
        this.dni = dni;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.username = username;
        this.password = password;
        this.estado = estado;
        this.rol = rol;
    }

    public long getId_user() {
        return id_user;
    }

    public void setId_user(long id_user) {
        this.id_user = id_user;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "NuevoUsuario{" +
                "dni='" + dni + '\'' +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", telefono='" + telefono + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", estado=" + estado +
                ", rol='" + rol + '\'' +
                '}';
    }
}
