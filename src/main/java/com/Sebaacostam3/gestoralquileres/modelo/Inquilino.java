package com.Sebaacostam3.gestoralquileres.modelo;

public class Inquilino {

    private String id;
    private String nombre;
    private String apellido;
    private String telefono;
    private String dni;
    private String email;

    public Inquilino(String id, String nombre, String apellido, String telefono, String dni, String email) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.dni = dni;
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
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

    public String getId() {
        return id;
    }

    public String setId(String id) {
        return this.id = id;

    }
    public String getDni() {
        return dni;
    }

    @Override
    public String toString() {
        return "Inquilino{" + "id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", telefono=" + telefono + ", dni=" + dni + ", email=" + email + '}';
    }

}

