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
public class ServicioJpaController implements Serializable {

    public ServicioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Servicio servicio) {
        if (servicio.getFacturaServicioCollection() == null) {
            servicio.setFacturaServicioCollection(new ArrayList<FacturaServicio>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleado idEmpleado = servicio.getIdEmpleado();
            if (idEmpleado != null) {
                idEmpleado = em.getReference(idEmpleado.getClass(), idEmpleado.getIdEmpleado());
                servicio.setIdEmpleado(idEmpleado);
            }
            Collection<FacturaServicio> attachedFacturaServicioCollection = new ArrayList<FacturaServicio>();
            for (FacturaServicio facturaServicioCollectionFacturaServicioToAttach : servicio.getFacturaServicioCollection()) {
                facturaServicioCollectionFacturaServicioToAttach = em.getReference(facturaServicioCollectionFacturaServicioToAttach.getClass(), facturaServicioCollectionFacturaServicioToAttach.getFacturaServicioPK());
                attachedFacturaServicioCollection.add(facturaServicioCollectionFacturaServicioToAttach);
            }
            servicio.setFacturaServicioCollection(attachedFacturaServicioCollection);
            em.persist(servicio);
            if (idEmpleado != null) {
                idEmpleado.getServicioCollection().add(servicio);
                idEmpleado = em.merge(idEmpleado);
            }
            for (FacturaServicio facturaServicioCollectionFacturaServicio : servicio.getFacturaServicioCollection()) {
                Servicio oldServicioOfFacturaServicioCollectionFacturaServicio = facturaServicioCollectionFacturaServicio.getServicio();
                facturaServicioCollectionFacturaServicio.setServicio(servicio);
                facturaServicioCollectionFacturaServicio = em.merge(facturaServicioCollectionFacturaServicio);
                if (oldServicioOfFacturaServicioCollectionFacturaServicio != null) {
                    oldServicioOfFacturaServicioCollectionFacturaServicio.getFacturaServicioCollection().remove(facturaServicioCollectionFacturaServicio);
                    oldServicioOfFacturaServicioCollectionFacturaServicio = em.merge(oldServicioOfFacturaServicioCollectionFacturaServicio);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Servicio servicio) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Servicio persistentServicio = em.find(Servicio.class, servicio.getIdServicio());
            Empleado idEmpleadoOld = persistentServicio.getIdEmpleado();
            Empleado idEmpleadoNew = servicio.getIdEmpleado();
            Collection<FacturaServicio> facturaServicioCollectionOld = persistentServicio.getFacturaServicioCollection();
            Collection<FacturaServicio> facturaServicioCollectionNew = servicio.getFacturaServicioCollection();
            List<String> illegalOrphanMessages = null;
            for (FacturaServicio facturaServicioCollectionOldFacturaServicio : facturaServicioCollectionOld) {
                if (!facturaServicioCollectionNew.contains(facturaServicioCollectionOldFacturaServicio)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain FacturaServicio " + facturaServicioCollectionOldFacturaServicio + " since its servicio field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idEmpleadoNew != null) {
                idEmpleadoNew = em.getReference(idEmpleadoNew.getClass(), idEmpleadoNew.getIdEmpleado());
                servicio.setIdEmpleado(idEmpleadoNew);
            }
            Collection<FacturaServicio> attachedFacturaServicioCollectionNew = new ArrayList<FacturaServicio>();
            for (FacturaServicio facturaServicioCollectionNewFacturaServicioToAttach : facturaServicioCollectionNew) {
                facturaServicioCollectionNewFacturaServicioToAttach = em.getReference(facturaServicioCollectionNewFacturaServicioToAttach.getClass(), facturaServicioCollectionNewFacturaServicioToAttach.getFacturaServicioPK());
                attachedFacturaServicioCollectionNew.add(facturaServicioCollectionNewFacturaServicioToAttach);
            }
            facturaServicioCollectionNew = attachedFacturaServicioCollectionNew;
            servicio.setFacturaServicioCollection(facturaServicioCollectionNew);
            servicio = em.merge(servicio);
            if (idEmpleadoOld != null && !idEmpleadoOld.equals(idEmpleadoNew)) {
                idEmpleadoOld.getServicioCollection().remove(servicio);
                idEmpleadoOld = em.merge(idEmpleadoOld);
            }
            if (idEmpleadoNew != null && !idEmpleadoNew.equals(idEmpleadoOld)) {
                idEmpleadoNew.getServicioCollection().add(servicio);
                idEmpleadoNew = em.merge(idEmpleadoNew);
            }
            for (FacturaServicio facturaServicioCollectionNewFacturaServicio : facturaServicioCollectionNew) {
                if (!facturaServicioCollectionOld.contains(facturaServicioCollectionNewFacturaServicio)) {
                    Servicio oldServicioOfFacturaServicioCollectionNewFacturaServicio = facturaServicioCollectionNewFacturaServicio.getServicio();
                    facturaServicioCollectionNewFacturaServicio.setServicio(servicio);
                    facturaServicioCollectionNewFacturaServicio = em.merge(facturaServicioCollectionNewFacturaServicio);
                    if (oldServicioOfFacturaServicioCollectionNewFacturaServicio != null && !oldServicioOfFacturaServicioCollectionNewFacturaServicio.equals(servicio)) {
                        oldServicioOfFacturaServicioCollectionNewFacturaServicio.getFacturaServicioCollection().remove(facturaServicioCollectionNewFacturaServicio);
                        oldServicioOfFacturaServicioCollectionNewFacturaServicio = em.merge(oldServicioOfFacturaServicioCollectionNewFacturaServicio);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = servicio.getIdServicio();
                if (findServicio(id) == null) {
                    throw new NonexistentEntityException("The servicio with id " + id + " no longer exists.");
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
            Servicio servicio;
            try {
                servicio = em.getReference(Servicio.class, id);
                servicio.getIdServicio();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The servicio with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<FacturaServicio> facturaServicioCollectionOrphanCheck = servicio.getFacturaServicioCollection();
            for (FacturaServicio facturaServicioCollectionOrphanCheckFacturaServicio : facturaServicioCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Servicio (" + servicio + ") cannot be destroyed since the FacturaServicio " + facturaServicioCollectionOrphanCheckFacturaServicio + " in its facturaServicioCollection field has a non-nullable servicio field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Empleado idEmpleado = servicio.getIdEmpleado();
            if (idEmpleado != null) {
                idEmpleado.getServicioCollection().remove(servicio);
                idEmpleado = em.merge(idEmpleado);
            }
            em.remove(servicio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Servicio> findServicioEntities() {
        return findServicioEntities(true, -1, -1);
    }

    public List<Servicio> findServicioEntities(int maxResults, int firstResult) {
        return findServicioEntities(false, maxResults, firstResult);
    }

    private List<Servicio> findServicioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Servicio.class));
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

    public Servicio findServicio(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Servicio.class, id);
        } finally {
            em.close();
        }
    }

    public int getServicioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Servicio> rt = cq.from(Servicio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
