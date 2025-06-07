package com.Sebaacostam3.gestoralquileres.modelo;

public class Propiedad {

    private String id;
    private String direccion;
    private String tipo;
    private double metrosCuadrados;
    private boolean alquilada;
    private double precioMensual;

    public Propiedad(String id, String direccion, String tipo, double metrosCuadrados, boolean alquilada, double precioMensual) {
        this.id = id;
        this.direccion = direccion;
        this.tipo = tipo;
        this.metrosCuadrados = metrosCuadrados;
        this.alquilada = false;
        this.precioMensual = precioMensual;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getPrecioMensual() {
        return precioMensual;
    }

    public void setPrecioMensual(double precioMensual) {
        this.precioMensual = precioMensual;
    }

    public boolean isAlquilada() {
        return alquilada;
    }

    public void setAlquilada(boolean alquilada) {
        this.alquilada = alquilada;
    }

    public double getMetrosCuadrados() {
        return metrosCuadrados;
    }

    public void setMetrosCuadrados(double metrosCuadrados) {
        this.metrosCuadrados = metrosCuadrados;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return "Propiedad{" + "ID=" + id + ", Direccion=" + direccion + ", Tipo=" + tipo + ", metros cuadrados=" + metrosCuadrados + ", Alquilada=" + alquilada + ", Precio Mensual=" + precioMensual + '}';
    }


}
