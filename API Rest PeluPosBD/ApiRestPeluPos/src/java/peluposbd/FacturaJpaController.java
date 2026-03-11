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
public class FacturaJpaController implements Serializable {

    public FacturaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Factura factura) {
        if (factura.getFacturaProductoCollection() == null) {
            factura.setFacturaProductoCollection(new ArrayList<FacturaProducto>());
        }
        if (factura.getFacturaServicioCollection() == null) {
            factura.setFacturaServicioCollection(new ArrayList<FacturaServicio>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente idCliente = factura.getIdCliente();
            if (idCliente != null) {
                idCliente = em.getReference(idCliente.getClass(), idCliente.getIdCliente());
                factura.setIdCliente(idCliente);
            }
            Empleado idEmpleado = factura.getIdEmpleado();
            if (idEmpleado != null) {
                idEmpleado = em.getReference(idEmpleado.getClass(), idEmpleado.getIdEmpleado());
                factura.setIdEmpleado(idEmpleado);
            }
            Collection<FacturaProducto> attachedFacturaProductoCollection = new ArrayList<FacturaProducto>();
            for (FacturaProducto facturaProductoCollectionFacturaProductoToAttach : factura.getFacturaProductoCollection()) {
                facturaProductoCollectionFacturaProductoToAttach = em.getReference(facturaProductoCollectionFacturaProductoToAttach.getClass(), facturaProductoCollectionFacturaProductoToAttach.getFacturaProductoPK());
                attachedFacturaProductoCollection.add(facturaProductoCollectionFacturaProductoToAttach);
            }
            factura.setFacturaProductoCollection(attachedFacturaProductoCollection);
            Collection<FacturaServicio> attachedFacturaServicioCollection = new ArrayList<FacturaServicio>();
            for (FacturaServicio facturaServicioCollectionFacturaServicioToAttach : factura.getFacturaServicioCollection()) {
                facturaServicioCollectionFacturaServicioToAttach = em.getReference(facturaServicioCollectionFacturaServicioToAttach.getClass(), facturaServicioCollectionFacturaServicioToAttach.getFacturaServicioPK());
                attachedFacturaServicioCollection.add(facturaServicioCollectionFacturaServicioToAttach);
            }
            factura.setFacturaServicioCollection(attachedFacturaServicioCollection);
            em.persist(factura);
            if (idCliente != null) {
                idCliente.getFacturaCollection().add(factura);
                idCliente = em.merge(idCliente);
            }
            if (idEmpleado != null) {
                idEmpleado.getFacturaCollection().add(factura);
                idEmpleado = em.merge(idEmpleado);
            }
            for (FacturaProducto facturaProductoCollectionFacturaProducto : factura.getFacturaProductoCollection()) {
                Factura oldFacturaOfFacturaProductoCollectionFacturaProducto = facturaProductoCollectionFacturaProducto.getFactura();
                facturaProductoCollectionFacturaProducto.setFactura(factura);
                facturaProductoCollectionFacturaProducto = em.merge(facturaProductoCollectionFacturaProducto);
                if (oldFacturaOfFacturaProductoCollectionFacturaProducto != null) {
                    oldFacturaOfFacturaProductoCollectionFacturaProducto.getFacturaProductoCollection().remove(facturaProductoCollectionFacturaProducto);
                    oldFacturaOfFacturaProductoCollectionFacturaProducto = em.merge(oldFacturaOfFacturaProductoCollectionFacturaProducto);
                }
            }
            for (FacturaServicio facturaServicioCollectionFacturaServicio : factura.getFacturaServicioCollection()) {
                Factura oldFacturaOfFacturaServicioCollectionFacturaServicio = facturaServicioCollectionFacturaServicio.getFactura();
                facturaServicioCollectionFacturaServicio.setFactura(factura);
                facturaServicioCollectionFacturaServicio = em.merge(facturaServicioCollectionFacturaServicio);
                if (oldFacturaOfFacturaServicioCollectionFacturaServicio != null) {
                    oldFacturaOfFacturaServicioCollectionFacturaServicio.getFacturaServicioCollection().remove(facturaServicioCollectionFacturaServicio);
                    oldFacturaOfFacturaServicioCollectionFacturaServicio = em.merge(oldFacturaOfFacturaServicioCollectionFacturaServicio);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Factura factura) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Factura persistentFactura = em.find(Factura.class, factura.getIdFactura());
            Cliente idClienteOld = persistentFactura.getIdCliente();
            Cliente idClienteNew = factura.getIdCliente();
            Empleado idEmpleadoOld = persistentFactura.getIdEmpleado();
            Empleado idEmpleadoNew = factura.getIdEmpleado();
            Collection<FacturaProducto> facturaProductoCollectionOld = persistentFactura.getFacturaProductoCollection();
            Collection<FacturaProducto> facturaProductoCollectionNew = factura.getFacturaProductoCollection();
            Collection<FacturaServicio> facturaServicioCollectionOld = persistentFactura.getFacturaServicioCollection();
            Collection<FacturaServicio> facturaServicioCollectionNew = factura.getFacturaServicioCollection();
            List<String> illegalOrphanMessages = null;
            for (FacturaProducto facturaProductoCollectionOldFacturaProducto : facturaProductoCollectionOld) {
                if (!facturaProductoCollectionNew.contains(facturaProductoCollectionOldFacturaProducto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain FacturaProducto " + facturaProductoCollectionOldFacturaProducto + " since its factura field is not nullable.");
                }
            }
            for (FacturaServicio facturaServicioCollectionOldFacturaServicio : facturaServicioCollectionOld) {
                if (!facturaServicioCollectionNew.contains(facturaServicioCollectionOldFacturaServicio)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain FacturaServicio " + facturaServicioCollectionOldFacturaServicio + " since its factura field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idClienteNew != null) {
                idClienteNew = em.getReference(idClienteNew.getClass(), idClienteNew.getIdCliente());
                factura.setIdCliente(idClienteNew);
            }
            if (idEmpleadoNew != null) {
                idEmpleadoNew = em.getReference(idEmpleadoNew.getClass(), idEmpleadoNew.getIdEmpleado());
                factura.setIdEmpleado(idEmpleadoNew);
            }
            Collection<FacturaProducto> attachedFacturaProductoCollectionNew = new ArrayList<FacturaProducto>();
            for (FacturaProducto facturaProductoCollectionNewFacturaProductoToAttach : facturaProductoCollectionNew) {
                facturaProductoCollectionNewFacturaProductoToAttach = em.getReference(facturaProductoCollectionNewFacturaProductoToAttach.getClass(), facturaProductoCollectionNewFacturaProductoToAttach.getFacturaProductoPK());
                attachedFacturaProductoCollectionNew.add(facturaProductoCollectionNewFacturaProductoToAttach);
            }
            facturaProductoCollectionNew = attachedFacturaProductoCollectionNew;
            factura.setFacturaProductoCollection(facturaProductoCollectionNew);
            Collection<FacturaServicio> attachedFacturaServicioCollectionNew = new ArrayList<FacturaServicio>();
            for (FacturaServicio facturaServicioCollectionNewFacturaServicioToAttach : facturaServicioCollectionNew) {
                facturaServicioCollectionNewFacturaServicioToAttach = em.getReference(facturaServicioCollectionNewFacturaServicioToAttach.getClass(), facturaServicioCollectionNewFacturaServicioToAttach.getFacturaServicioPK());
                attachedFacturaServicioCollectionNew.add(facturaServicioCollectionNewFacturaServicioToAttach);
            }
            facturaServicioCollectionNew = attachedFacturaServicioCollectionNew;
            factura.setFacturaServicioCollection(facturaServicioCollectionNew);
            factura = em.merge(factura);
            if (idClienteOld != null && !idClienteOld.equals(idClienteNew)) {
                idClienteOld.getFacturaCollection().remove(factura);
                idClienteOld = em.merge(idClienteOld);
            }
            if (idClienteNew != null && !idClienteNew.equals(idClienteOld)) {
                idClienteNew.getFacturaCollection().add(factura);
                idClienteNew = em.merge(idClienteNew);
            }
            if (idEmpleadoOld != null && !idEmpleadoOld.equals(idEmpleadoNew)) {
                idEmpleadoOld.getFacturaCollection().remove(factura);
                idEmpleadoOld = em.merge(idEmpleadoOld);
            }
            if (idEmpleadoNew != null && !idEmpleadoNew.equals(idEmpleadoOld)) {
                idEmpleadoNew.getFacturaCollection().add(factura);
                idEmpleadoNew = em.merge(idEmpleadoNew);
            }
            for (FacturaProducto facturaProductoCollectionNewFacturaProducto : facturaProductoCollectionNew) {
                if (!facturaProductoCollectionOld.contains(facturaProductoCollectionNewFacturaProducto)) {
                    Factura oldFacturaOfFacturaProductoCollectionNewFacturaProducto = facturaProductoCollectionNewFacturaProducto.getFactura();
                    facturaProductoCollectionNewFacturaProducto.setFactura(factura);
                    facturaProductoCollectionNewFacturaProducto = em.merge(facturaProductoCollectionNewFacturaProducto);
                    if (oldFacturaOfFacturaProductoCollectionNewFacturaProducto != null && !oldFacturaOfFacturaProductoCollectionNewFacturaProducto.equals(factura)) {
                        oldFacturaOfFacturaProductoCollectionNewFacturaProducto.getFacturaProductoCollection().remove(facturaProductoCollectionNewFacturaProducto);
                        oldFacturaOfFacturaProductoCollectionNewFacturaProducto = em.merge(oldFacturaOfFacturaProductoCollectionNewFacturaProducto);
                    }
                }
            }
            for (FacturaServicio facturaServicioCollectionNewFacturaServicio : facturaServicioCollectionNew) {
                if (!facturaServicioCollectionOld.contains(facturaServicioCollectionNewFacturaServicio)) {
                    Factura oldFacturaOfFacturaServicioCollectionNewFacturaServicio = facturaServicioCollectionNewFacturaServicio.getFactura();
                    facturaServicioCollectionNewFacturaServicio.setFactura(factura);
                    facturaServicioCollectionNewFacturaServicio = em.merge(facturaServicioCollectionNewFacturaServicio);
                    if (oldFacturaOfFacturaServicioCollectionNewFacturaServicio != null && !oldFacturaOfFacturaServicioCollectionNewFacturaServicio.equals(factura)) {
                        oldFacturaOfFacturaServicioCollectionNewFacturaServicio.getFacturaServicioCollection().remove(facturaServicioCollectionNewFacturaServicio);
                        oldFacturaOfFacturaServicioCollectionNewFacturaServicio = em.merge(oldFacturaOfFacturaServicioCollectionNewFacturaServicio);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = factura.getIdFactura();
                if (findFactura(id) == null) {
                    throw new NonexistentEntityException("The factura with id " + id + " no longer exists.");
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
            Factura factura;
            try {
                factura = em.getReference(Factura.class, id);
                factura.getIdFactura();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The factura with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<FacturaProducto> facturaProductoCollectionOrphanCheck = factura.getFacturaProductoCollection();
            for (FacturaProducto facturaProductoCollectionOrphanCheckFacturaProducto : facturaProductoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Factura (" + factura + ") cannot be destroyed since the FacturaProducto " + facturaProductoCollectionOrphanCheckFacturaProducto + " in its facturaProductoCollection field has a non-nullable factura field.");
            }
            Collection<FacturaServicio> facturaServicioCollectionOrphanCheck = factura.getFacturaServicioCollection();
            for (FacturaServicio facturaServicioCollectionOrphanCheckFacturaServicio : facturaServicioCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Factura (" + factura + ") cannot be destroyed since the FacturaServicio " + facturaServicioCollectionOrphanCheckFacturaServicio + " in its facturaServicioCollection field has a non-nullable factura field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Cliente idCliente = factura.getIdCliente();
            if (idCliente != null) {
                idCliente.getFacturaCollection().remove(factura);
                idCliente = em.merge(idCliente);
            }
            Empleado idEmpleado = factura.getIdEmpleado();
            if (idEmpleado != null) {
                idEmpleado.getFacturaCollection().remove(factura);
                idEmpleado = em.merge(idEmpleado);
            }
            em.remove(factura);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Factura> findFacturaEntities() {
        return findFacturaEntities(true, -1, -1);
    }

    public List<Factura> findFacturaEntities(int maxResults, int firstResult) {
        return findFacturaEntities(false, maxResults, firstResult);
    }

    private List<Factura> findFacturaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Factura.class));
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

    public Factura findFactura(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Factura.class, id);
        } finally {
            em.close();
        }
    }

    public int getFacturaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Factura> rt = cq.from(Factura.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
