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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import peluposbd.exceptions.IllegalOrphanException;
import peluposbd.exceptions.NonexistentEntityException;

/**
 *
 * @author alumno
 */
public class ProductoJpaController implements Serializable {

    public ProductoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Producto producto) {
        if (producto.getFacturaProductoCollection() == null) {
            producto.setFacturaProductoCollection(new ArrayList<FacturaProducto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<FacturaProducto> attachedFacturaProductoCollection = new ArrayList<FacturaProducto>();
            for (FacturaProducto facturaProductoCollectionFacturaProductoToAttach : producto.getFacturaProductoCollection()) {
                facturaProductoCollectionFacturaProductoToAttach = em.getReference(facturaProductoCollectionFacturaProductoToAttach.getClass(), facturaProductoCollectionFacturaProductoToAttach.getFacturaProductoPK());
                attachedFacturaProductoCollection.add(facturaProductoCollectionFacturaProductoToAttach);
            }
            producto.setFacturaProductoCollection(attachedFacturaProductoCollection);
            em.persist(producto);
            for (FacturaProducto facturaProductoCollectionFacturaProducto : producto.getFacturaProductoCollection()) {
                Producto oldProductoOfFacturaProductoCollectionFacturaProducto = facturaProductoCollectionFacturaProducto.getProducto();
                facturaProductoCollectionFacturaProducto.setProducto(producto);
                facturaProductoCollectionFacturaProducto = em.merge(facturaProductoCollectionFacturaProducto);
                if (oldProductoOfFacturaProductoCollectionFacturaProducto != null) {
                    oldProductoOfFacturaProductoCollectionFacturaProducto.getFacturaProductoCollection().remove(facturaProductoCollectionFacturaProducto);
                    oldProductoOfFacturaProductoCollectionFacturaProducto = em.merge(oldProductoOfFacturaProductoCollectionFacturaProducto);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Producto producto) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto persistentProducto = em.find(Producto.class, producto.getIdProducto());
            Collection<FacturaProducto> facturaProductoCollectionOld = persistentProducto.getFacturaProductoCollection();
            Collection<FacturaProducto> facturaProductoCollectionNew = producto.getFacturaProductoCollection();
            List<String> illegalOrphanMessages = null;
            for (FacturaProducto facturaProductoCollectionOldFacturaProducto : facturaProductoCollectionOld) {
                if (!facturaProductoCollectionNew.contains(facturaProductoCollectionOldFacturaProducto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain FacturaProducto " + facturaProductoCollectionOldFacturaProducto + " since its producto field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<FacturaProducto> attachedFacturaProductoCollectionNew = new ArrayList<FacturaProducto>();
            for (FacturaProducto facturaProductoCollectionNewFacturaProductoToAttach : facturaProductoCollectionNew) {
                facturaProductoCollectionNewFacturaProductoToAttach = em.getReference(facturaProductoCollectionNewFacturaProductoToAttach.getClass(), facturaProductoCollectionNewFacturaProductoToAttach.getFacturaProductoPK());
                attachedFacturaProductoCollectionNew.add(facturaProductoCollectionNewFacturaProductoToAttach);
            }
            facturaProductoCollectionNew = attachedFacturaProductoCollectionNew;
            producto.setFacturaProductoCollection(facturaProductoCollectionNew);
            producto = em.merge(producto);
            for (FacturaProducto facturaProductoCollectionNewFacturaProducto : facturaProductoCollectionNew) {
                if (!facturaProductoCollectionOld.contains(facturaProductoCollectionNewFacturaProducto)) {
                    Producto oldProductoOfFacturaProductoCollectionNewFacturaProducto = facturaProductoCollectionNewFacturaProducto.getProducto();
                    facturaProductoCollectionNewFacturaProducto.setProducto(producto);
                    facturaProductoCollectionNewFacturaProducto = em.merge(facturaProductoCollectionNewFacturaProducto);
                    if (oldProductoOfFacturaProductoCollectionNewFacturaProducto != null && !oldProductoOfFacturaProductoCollectionNewFacturaProducto.equals(producto)) {
                        oldProductoOfFacturaProductoCollectionNewFacturaProducto.getFacturaProductoCollection().remove(facturaProductoCollectionNewFacturaProducto);
                        oldProductoOfFacturaProductoCollectionNewFacturaProducto = em.merge(oldProductoOfFacturaProductoCollectionNewFacturaProducto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = producto.getIdProducto();
                if (findProducto(id) == null) {
                    throw new NonexistentEntityException("The producto with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto producto;
            try {
                producto = em.getReference(Producto.class, id);
                producto.getIdProducto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The producto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<FacturaProducto> facturaProductoCollectionOrphanCheck = producto.getFacturaProductoCollection();
            for (FacturaProducto facturaProductoCollectionOrphanCheckFacturaProducto : facturaProductoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Producto (" + producto + ") cannot be destroyed since the FacturaProducto " + facturaProductoCollectionOrphanCheckFacturaProducto + " in its facturaProductoCollection field has a non-nullable producto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(producto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Producto> findProductoEntities() {
        return findProductoEntities(true, -1, -1);
    }

    public List<Producto> findProductoEntities(int maxResults, int firstResult) {
        return findProductoEntities(false, maxResults, firstResult);
    }

    private List<Producto> findProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Producto.class));
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

    public Producto findProducto(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Producto.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Producto> rt = cq.from(Producto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
