/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package peluposbd;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

/**
 *
 * @author alumno
 */
@Embeddable
public class FacturaServicioPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "id_factura")
    private long idFactura;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_servicio")
    private long idServicio;

    public FacturaServicioPK() {
    }

    public FacturaServicioPK(long idFactura, long idServicio) {
        this.idFactura = idFactura;
        this.idServicio = idServicio;
    }

    public long getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(long idFactura) {
        this.idFactura = idFactura;
    }

    public long getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(long idServicio) {
        this.idServicio = idServicio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idFactura;
        hash += (int) idServicio;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FacturaServicioPK)) {
            return false;
        }
        FacturaServicioPK other = (FacturaServicioPK) object;
        if (this.idFactura != other.idFactura) {
            return false;
        }
        if (this.idServicio != other.idServicio) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "peluposbd.FacturaServicioPK[ idFactura=" + idFactura + ", idServicio=" + idServicio + " ]";
    }
    
}
