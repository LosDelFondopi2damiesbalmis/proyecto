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
public class FacturaServicioJpaController implements Serializable {

    public FacturaServicioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(FacturaServicio facturaServicio) throws PreexistingEntityException, Exception {
        if (facturaServicio.getFacturaServicioPK() == null) {
            facturaServicio.setFacturaServicioPK(new FacturaServicioPK());
        }
        facturaServicio.getFacturaServicioPK().setIdServicio(facturaServicio.getServicio().getIdServicio());
        facturaServicio.getFacturaServicioPK().setIdFactura(facturaServicio.getFactura().getIdFactura());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Factura factura = facturaServicio.getFactura();
            if (factura != null) {
                factura = em.getReference(factura.getClass(), factura.getIdFactura());
                facturaServicio.setFactura(factura);
            }
            Servicio servicio = facturaServicio.getServicio();
            if (servicio != null) {
                servicio = em.getReference(servicio.getClass(), servicio.getIdServicio());
                facturaServicio.setServicio(servicio);
            }
            em.persist(facturaServicio);
            if (factura != null) {
                factura.getFacturaServicioCollection().add(facturaServicio);
                factura = em.merge(factura);
            }
            if (servicio != null) {
                servicio.getFacturaServicioCollection().add(facturaServicio);
                servicio = em.merge(servicio);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFacturaServicio(facturaServicio.getFacturaServicioPK()) != null) {
                throw new PreexistingEntityException("FacturaServicio " + facturaServicio + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(FacturaServicio facturaServicio) throws NonexistentEntityException, Exception {
        facturaServicio.getFacturaServicioPK().setIdServicio(facturaServicio.getServicio().getIdServicio());
        facturaServicio.getFacturaServicioPK().setIdFactura(facturaServicio.getFactura().getIdFactura());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FacturaServicio persistentFacturaServicio = em.find(FacturaServicio.class, facturaServicio.getFacturaServicioPK());
            Factura facturaOld = persistentFacturaServicio.getFactura();
            Factura facturaNew = facturaServicio.getFactura();
            Servicio servicioOld = persistentFacturaServicio.getServicio();
            Servicio servicioNew = facturaServicio.getServicio();
            if (facturaNew != null) {
                facturaNew = em.getReference(facturaNew.getClass(), facturaNew.getIdFactura());
                facturaServicio.setFactura(facturaNew);
            }
            if (servicioNew != null) {
                servicioNew = em.getReference(servicioNew.getClass(), servicioNew.getIdServicio());
                facturaServicio.setServicio(servicioNew);
            }
            facturaServicio = em.merge(facturaServicio);
            if (facturaOld != null && !facturaOld.equals(facturaNew)) {
                facturaOld.getFacturaServicioCollection().remove(facturaServicio);
                facturaOld = em.merge(facturaOld);
            }
            if (facturaNew != null && !facturaNew.equals(facturaOld)) {
                facturaNew.getFacturaServicioCollection().add(facturaServicio);
                facturaNew = em.merge(facturaNew);
            }
            if (servicioOld != null && !servicioOld.equals(servicioNew)) {
                servicioOld.getFacturaServicioCollection().remove(facturaServicio);
                servicioOld = em.merge(servicioOld);
            }
            if (servicioNew != null && !servicioNew.equals(servicioOld)) {
                servicioNew.getFacturaServicioCollection().add(facturaServicio);
                servicioNew = em.merge(servicioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                FacturaServicioPK id = facturaServicio.getFacturaServicioPK();
                if (findFacturaServicio(id) == null) {
                    throw new NonexistentEntityException("The facturaServicio with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(FacturaServicioPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FacturaServicio facturaServicio;
            try {
                facturaServicio = em.getReference(FacturaServicio.class, id);
                facturaServicio.getFacturaServicioPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The facturaServicio with id " + id + " no longer exists.", enfe);
            }
            Factura factura = facturaServicio.getFactura();
            if (factura != null) {
                factura.getFacturaServicioCollection().remove(facturaServicio);
                factura = em.merge(factura);
            }
            Servicio servicio = facturaServicio.getServicio();
            if (servicio != null) {
                servicio.getFacturaServicioCollection().remove(facturaServicio);
                servicio = em.merge(servicio);
            }
            em.remove(facturaServicio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<FacturaServicio> findFacturaServicioEntities() {
        return findFacturaServicioEntities(true, -1, -1);
    }

    public List<FacturaServicio> findFacturaServicioEntities(int maxResults, int firstResult) {
        return findFacturaServicioEntities(false, maxResults, firstResult);
    }

    private List<FacturaServicio> findFacturaServicioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(FacturaServicio.class));
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

    public FacturaServicio findFacturaServicio(FacturaServicioPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FacturaServicio.class, id);
        } finally {
            em.close();
        }
    }

    public int getFacturaServicioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<FacturaServicio> rt = cq.from(FacturaServicio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
