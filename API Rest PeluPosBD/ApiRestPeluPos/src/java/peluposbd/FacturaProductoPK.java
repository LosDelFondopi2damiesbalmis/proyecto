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
public class FacturaProductoPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "id_factura")
    private long idFactura;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_producto")
    private long idProducto;

    public FacturaProductoPK() {
    }

    public FacturaProductoPK(long idFactura, long idProducto) {
        this.idFactura = idFactura;
        this.idProducto = idProducto;
    }

    public long getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(long idFactura) {
        this.idFactura = idFactura;
    }

    public long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(long idProducto) {
        this.idProducto = idProducto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idFactura;
        hash += (int) idProducto;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FacturaProductoPK)) {
            return false;
        }
        FacturaProductoPK other = (FacturaProductoPK) object;
        if (this.idFactura != other.idFactura) {
            return false;
        }
        if (this.idProducto != other.idProducto) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "peluposbd.FacturaProductoPK[ idFactura=" + idFactura + ", idProducto=" + idProducto + " ]";
    }
    
}
