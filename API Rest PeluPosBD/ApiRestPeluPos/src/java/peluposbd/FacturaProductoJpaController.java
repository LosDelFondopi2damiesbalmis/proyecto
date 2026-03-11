/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package peluposbd;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;
import peluposbd.exceptions.NonexistentEntityException;
import peluposbd.exceptions.PreexistingEntityException;

/**
 *
 * @author alumno
 */
public class FacturaProductoJpaController implements Serializable {

    public FacturaProductoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(FacturaProducto facturaProducto) throws PreexistingEntityException, Exception {
        if (facturaProducto.getFacturaProductoPK() == null) {
            facturaProducto.setFacturaProductoPK(new FacturaProductoPK());
        }
        facturaProducto.getFacturaProductoPK().setIdProducto(facturaProducto.getProducto().getIdProducto());
        facturaProducto.getFacturaProductoPK().setIdFactura(facturaProducto.getFactura().getIdFactura());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Factura factura = facturaProducto.getFactura();
            if (factura != null) {
                factura = em.getReference(factura.getClass(), factura.getIdFactura());
                facturaProducto.setFactura(factura);
            }
            Producto producto = facturaProducto.getProducto();
            if (producto != null) {
                producto = em.getReference(producto.getClass(), producto.getIdProducto());
                facturaProducto.setProducto(producto);
            }
            em.persist(facturaProducto);
            if (factura != null) {
                factura.getFacturaProductoCollection().add(facturaProducto);
                factura = em.merge(factura);
            }
            if (producto != null) {
                producto.getFacturaProductoCollection().add(facturaProducto);
                producto = em.merge(producto);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFacturaProducto(facturaProducto.getFacturaProductoPK()) != null) {
                throw new PreexistingEntityException("FacturaProducto " + facturaProducto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(FacturaProducto facturaProducto) throws NonexistentEntityException, Exception {
        facturaProducto.getFacturaProductoPK().setIdProducto(facturaProducto.getProducto().getIdProducto());
        facturaProducto.getFacturaProductoPK().setIdFactura(facturaProducto.getFactura().getIdFactura());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FacturaProducto persistentFacturaProducto = em.find(FacturaProducto.class, facturaProducto.getFacturaProductoPK());
            Factura facturaOld = persistentFacturaProducto.getFactura();
            Factura facturaNew = facturaProducto.getFactura();
            Producto productoOld = persistentFacturaProducto.getProducto();
            Producto productoNew = facturaProducto.getProducto();
            if (facturaNew != null) {
                facturaNew = em.getReference(facturaNew.getClass(), facturaNew.getIdFactura());
                facturaProducto.setFactura(facturaNew);
            }
            if (productoNew != null) {
                productoNew = em.getReference(productoNew.getClass(), productoNew.getIdProducto());
                facturaProducto.setProducto(productoNew);
            }
            facturaProducto = em.merge(facturaProducto);
            if (facturaOld != null && !facturaOld.equals(facturaNew)) {
                facturaOld.getFacturaProductoCollection().remove(facturaProducto);
                facturaOld = em.merge(facturaOld);
            }
            if (facturaNew != null && !facturaNew.equals(facturaOld)) {
                facturaNew.getFacturaProductoCollection().add(facturaProducto);
                facturaNew = em.merge(facturaNew);
            }
            if (productoOld != null && !productoOld.equals(productoNew)) {
                productoOld.getFacturaProductoCollection().remove(facturaProducto);
                productoOld = em.merge(productoOld);
            }
            if (productoNew != null && !productoNew.equals(productoOld)) {
                productoNew.getFacturaProductoCollection().add(facturaProducto);
                productoNew = em.merge(productoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                FacturaProductoPK id = facturaProducto.getFacturaProductoPK();
                if (findFacturaProducto(id) == null) {
                    throw new NonexistentEntityException("The facturaProducto with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(FacturaProductoPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FacturaProducto facturaProducto;
            try {
                facturaProducto = em.getReference(FacturaProducto.class, id);
                facturaProducto.getFacturaProductoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The facturaProducto with id " + id + " no longer exists.", enfe);
            }
            Factura factura = facturaProducto.getFactura();
            if (factura != null) {
                factura.getFacturaProductoCollection().remove(facturaProducto);
                factura = em.merge(factura);
            }
            Producto producto = facturaProducto.getProducto();
            if (producto != null) {
                producto.getFacturaProductoCollection().remove(facturaProducto);
                producto = em.merge(producto);
            }
            em.remove(facturaProducto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<FacturaProducto> findFacturaProductoEntities() {
        return findFacturaProductoEntities(true, -1, -1);
    }

    public List<FacturaProducto> findFacturaProductoEntities(int maxResults, int firstResult) {
        return findFacturaProductoEntities(false, maxResults, firstResult);
    }

    private List<FacturaProducto> findFacturaProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(FacturaProducto.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public FacturaProducto findFacturaProducto(FacturaProductoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FacturaProducto.class, id);
        } finally {
            em.close();
        }
    }

    public int getFacturaProductoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<FacturaProducto> rt = cq.from(FacturaProducto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
