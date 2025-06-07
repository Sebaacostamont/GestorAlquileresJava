package com.Sebaacostam3.gestoralquileres.modelo;

import java.time.LocalDate;

public class Incidencia {

    private String id;
    private Propiedad propiedad;
    private String descripcion;
    private LocalDate fecha;
    private String estado;
    private double costoEstimado;

    public Incidencia(String id, Propiedad propiedad, String descripcion, LocalDate fecha, String estado, double costoEstimado) {
        this.id = id;
        this.propiedad = propiedad;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.estado = estado;
        this.costoEstimado = costoEstimado;
    }

    public String getId() {
        return id;
    }

    public Propiedad getPropiedad() {
        return propiedad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getCostoEstimado() {
        return costoEstimado;
    }

    public void setCostoEstimado(double costoEstimado) {
        this.costoEstimado = costoEstimado;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPropiedad(Propiedad propiedad) {
        this.propiedad = propiedad;
    }



    @Override
    public String toString() {
        return "Incidencia{" + "id=" + id + ", propiedad=" + propiedad + ", descripcion=" + descripcion + ", fecha=" + fecha + ", estado=" + estado + ", costoEstimado=" + costoEstimado + '}';
    }
}


