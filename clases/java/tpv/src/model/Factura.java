package model;

import java.util.Date;
import java.util.List;

public class Factura {
    private long idFactura;
    private double monto;
    private Date fecha;
    private boolean pendiente;
    private String tipoPago;
    private Cliente cliente;
    private Empleado empleado;
    private List<Producto> productos;
    private List<Servicio> servicios;

    public long getIdFactura() {
        return idFactura;
    }
    public void setIdFactura(long idFactura) {
        this.idFactura = idFactura;
    }
    public double getMonto() {
        return monto;
    }
    public void setMonto(double monto) {
        this.monto = monto;
    }
    public Date getFecha() {
        return fecha;
    }
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    public boolean isPendiente() {
        return pendiente;
    }
    public void setPendiente(boolean pendiente) {
        this.pendiente = pendiente;
    }
    public String getTipoPago() {
        return tipoPago;
    }
    public void setTipoPago(String tipoPago) {
        this.tipoPago = tipoPago;
    }
    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    public Empleado getEmpleado() {
        return empleado;
    }
    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }
    public List<Producto> getProductos() {
        return productos;
    }
    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }
    public List<Servicio> getServicios() {
        return servicios;
    }
    public void setServicios(List<Servicio> servicios) {
        this.servicios = servicios;
    }
    
}
