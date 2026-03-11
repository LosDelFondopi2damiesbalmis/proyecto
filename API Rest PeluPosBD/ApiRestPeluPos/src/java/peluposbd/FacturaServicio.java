/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package peluposbd;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author alumno
 */
@Entity
@Table(name = "factura_servicio")
@NamedQueries({
    @NamedQuery(name = "FacturaServicio.findAll", query = "SELECT f FROM FacturaServicio f"),
    @NamedQuery(name = "FacturaServicio.findByIdFactura", query = "SELECT f FROM FacturaServicio f WHERE f.facturaServicioPK.idFactura = :idFactura"),
    @NamedQuery(name = "FacturaServicio.findByIdServicio", query = "SELECT f FROM FacturaServicio f WHERE f.facturaServicioPK.idServicio = :idServicio"),
    @NamedQuery(name = "FacturaServicio.findByCantidad", query = "SELECT f FROM FacturaServicio f WHERE f.cantidad = :cantidad"),
    @NamedQuery(name = "FacturaServicio.findByPrecioCobrado", query = "SELECT f FROM FacturaServicio f WHERE f.precioCobrado = :precioCobrado")})
public class FacturaServicio implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected FacturaServicioPK facturaServicioPK;
    @Column(name = "cantidad")
    private Integer cantidad;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "precio_cobrado")
    private BigDecimal precioCobrado;
    @JoinColumn(name = "id_factura", referencedColumnName = "id_factura", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    @JsonbTransient
    private Factura factura;
    @JoinColumn(name = "id_servicio", referencedColumnName = "id_servicio", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Servicio servicio;

    public FacturaServicio() {
    }

    public FacturaServicio(FacturaServicioPK facturaServicioPK) {
        this.facturaServicioPK = facturaServicioPK;
    }

    public FacturaServicio(long idFactura, long idServicio) {
        this.facturaServicioPK = new FacturaServicioPK(idFactura, idServicio);
    }

    public FacturaServicioPK getFacturaServicioPK() {
        return facturaServicioPK;
    }

    public void setFacturaServicioPK(FacturaServicioPK facturaServicioPK) {
        this.facturaServicioPK = facturaServicioPK;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioCobrado() {
        return precioCobrado;
    }

    public void setPrecioCobrado(BigDecimal precioCobrado) {
        this.precioCobrado = precioCobrado;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (facturaServicioPK != null ? facturaServicioPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FacturaServicio)) {
            return false;
        }
        FacturaServicio other = (FacturaServicio) object;
        if ((this.facturaServicioPK == null && other.facturaServicioPK != null) || (this.facturaServicioPK != null && !this.facturaServicioPK.equals(other.facturaServicioPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "peluposbd.FacturaServicio[ facturaServicioPK=" + facturaServicioPK + " ]";
    }
    
}
