package com.Sebaacostam3.gestoralquileres.modelo;

import java.time.LocalDate;

public class Pago {

    private String id;
    private ContratoAlquiler contrato;
    private LocalDate fechaPago;
    private double valorPago;
    private boolean confirmado;
    private String periodoCorrespondiente;

    public Pago(String id, ContratoAlquiler contrato, LocalDate fechaPago, double valorPago, boolean pagado, String periodoCorrespondiente) {
        this.id = id;
        this.contrato = contrato;
        this.fechaPago = fechaPago;
        this.valorPago = valorPago;
        this.confirmado = pagado;
        this.periodoCorrespondiente = periodoCorrespondiente;
    }

    public String getId() {
        return id;
    }

    public ContratoAlquiler getContrato() {
        return contrato;
    }

    public LocalDate getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDate fechaPago) {
        this.fechaPago = fechaPago;
    }

    public double getValorPago() {
        return valorPago;
    }

    public void setValorPago(double valorPago) {
        this.valorPago = valorPago;
    }

    public boolean isConfirmado() {
        return confirmado;
    }

    public void setConfirmado(boolean confirmado) {
        this.confirmado = confirmado;
    }

    public String getPeriodoCorrespondiente() {
        return periodoCorrespondiente;
    }

    public void setPeriodoCorrespondiente(String periodoCorrespondiente) {
        this.periodoCorrespondiente = periodoCorrespondiente;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setContrato(ContratoAlquiler contrato) {
        this.contrato = contrato;
    }


    @Override
    public String toString() {
        return "Pago{" + "id=" + id + ", contrato=" + contrato + ", fechaPago=" + fechaPago + ", valorPago=" + valorPago + ", pagado=" + confirmado + ", periodoCorrespondiente=" + periodoCorrespondiente + '}';
    }
}
