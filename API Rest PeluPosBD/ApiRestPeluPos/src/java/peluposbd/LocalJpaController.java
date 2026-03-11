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
import peluposbd.exceptions.NonexistentEntityException;

/**
 *
 * @author alumno
 */
public class LocalJpaController implements Serializable {

    public LocalJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Local local) {
        if (local.getEmpleadoCollection() == null) {
            local.setEmpleadoCollection(new ArrayList<Empleado>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Empleado> attachedEmpleadoCollection = new ArrayList<Empleado>();
            for (Empleado empleadoCollectionEmpleadoToAttach : local.getEmpleadoCollection()) {
                empleadoCollectionEmpleadoToAttach = em.getReference(empleadoCollectionEmpleadoToAttach.getClass(), empleadoCollectionEmpleadoToAttach.getIdEmpleado());
                attachedEmpleadoCollection.add(empleadoCollectionEmpleadoToAttach);
            }
            local.setEmpleadoCollection(attachedEmpleadoCollection);
            em.persist(local);
            for (Empleado empleadoCollectionEmpleado : local.getEmpleadoCollection()) {
                Local oldIdLocalOfEmpleadoCollectionEmpleado = empleadoCollectionEmpleado.getIdLocal();
                empleadoCollectionEmpleado.setIdLocal(local);
                empleadoCollectionEmpleado = em.merge(empleadoCollectionEmpleado);
                if (oldIdLocalOfEmpleadoCollectionEmpleado != null) {
                    oldIdLocalOfEmpleadoCollectionEmpleado.getEmpleadoCollection().remove(empleadoCollectionEmpleado);
                    oldIdLocalOfEmpleadoCollectionEmpleado = em.merge(oldIdLocalOfEmpleadoCollectionEmpleado);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Local local) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Local persistentLocal = em.find(Local.class, local.getIdLocal());
            Collection<Empleado> empleadoCollectionOld = persistentLocal.getEmpleadoCollection();
            Collection<Empleado> empleadoCollectionNew = local.getEmpleadoCollection();
            Collection<Empleado> attachedEmpleadoCollectionNew = new ArrayList<Empleado>();
            for (Empleado empleadoCollectionNewEmpleadoToAttach : empleadoCollectionNew) {
                empleadoCollectionNewEmpleadoToAttach = em.getReference(empleadoCollectionNewEmpleadoToAttach.getClass(), empleadoCollectionNewEmpleadoToAttach.getIdEmpleado());
                attachedEmpleadoCollectionNew.add(empleadoCollectionNewEmpleadoToAttach);
            }
            empleadoCollectionNew = attachedEmpleadoCollectionNew;
            local.setEmpleadoCollection(empleadoCollectionNew);
            local = em.merge(local);
            for (Empleado empleadoCollectionOldEmpleado : empleadoCollectionOld) {
                if (!empleadoCollectionNew.contains(empleadoCollectionOldEmpleado)) {
                    empleadoCollectionOldEmpleado.setIdLocal(null);
                    empleadoCollectionOldEmpleado = em.merge(empleadoCollectionOldEmpleado);
                }
            }
            for (Empleado empleadoCollectionNewEmpleado : empleadoCollectionNew) {
                if (!empleadoCollectionOld.contains(empleadoCollectionNewEmpleado)) {
                    Local oldIdLocalOfEmpleadoCollectionNewEmpleado = empleadoCollectionNewEmpleado.getIdLocal();
                    empleadoCollectionNewEmpleado.setIdLocal(local);
                    empleadoCollectionNewEmpleado = em.merge(empleadoCollectionNewEmpleado);
                    if (oldIdLocalOfEmpleadoCollectionNewEmpleado != null && !oldIdLocalOfEmpleadoCollectionNewEmpleado.equals(local)) {
                        oldIdLocalOfEmpleadoCollectionNewEmpleado.getEmpleadoCollection().remove(empleadoCollectionNewEmpleado);
                        oldIdLocalOfEmpleadoCollectionNewEmpleado = em.merge(oldIdLocalOfEmpleadoCollectionNewEmpleado);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = local.getIdLocal();
                if (findLocal(id) == null) {
                    throw new NonexistentEntityException("The local with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Local local;
            try {
                local = em.getReference(Local.class, id);
                local.getIdLocal();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The local with id " + id + " no longer exists.", enfe);
            }
            Collection<Empleado> empleadoCollection = local.getEmpleadoCollection();
            for (Empleado empleadoCollectionEmpleado : empleadoCollection) {
                empleadoCollectionEmpleado.setIdLocal(null);
                empleadoCollectionEmpleado = em.merge(empleadoCollectionEmpleado);
            }
            em.remove(local);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Local> findLocalEntities() {
        return findLocalEntities(true, -1, -1);
    }

    public List<Local> findLocalEntities(int maxResults, int firstResult) {
        return findLocalEntities(false, maxResults, firstResult);
    }

    private List<Local> findLocalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Local.class));
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

    public Local findLocal(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Local.class, id);
        } finally {
            em.close();
        }
    }

    public int getLocalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Local> rt = cq.from(Local.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
