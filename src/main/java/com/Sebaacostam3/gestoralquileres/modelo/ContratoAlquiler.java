package com.Sebaacostam3.gestoralquileres.modelo;

import java.time.LocalDate;

public class ContratoAlquiler {

    private String id;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Propiedad propiedad;
    private Inquilino inquilino;
    private double valorMensual;
    private boolean activo;

    public ContratoAlquiler(String id, LocalDate fechaInicio, LocalDate fechaFin, Propiedad propiedad, Inquilino inquilino, double valorMensual, boolean activo) {
        this.id = id;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.propiedad = propiedad;
        this.inquilino = inquilino;
        this.valorMensual = valorMensual;
        this.activo = activo;
    }

    public String getId() {
        return id;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Propiedad getPropiedad() {
        return propiedad;
    }

    public Inquilino getInquilino() {
        return inquilino;
    }

    public double getValorMensual() {
        return valorMensual;
    }

    public void setValorMensual(double valorMensual) {
        this.valorMensual = valorMensual;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPropiedad(Propiedad propiedad) {
        this.propiedad = propiedad;
    }

    public void setInquilino(Inquilino inquilino) {
        this.inquilino = inquilino;
    }



    @Override
    public String toString() {
        return "ContratoAlquiler{" + "id=" + id + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + ", propiedad=" + propiedad + ", inquilino=" + inquilino + ", valorMensual=" + valorMensual + ", activo=" + activo + '}';
    }
}
