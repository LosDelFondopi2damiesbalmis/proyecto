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
public class EmpleadoJpaController implements Serializable {

    public EmpleadoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Empleado empleado) {
        if (empleado.getFacturaCollection() == null) {
            empleado.setFacturaCollection(new ArrayList<Factura>());
        }
        if (empleado.getServicioCollection() == null) {
            empleado.setServicioCollection(new ArrayList<Servicio>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Local idLocal = empleado.getIdLocal();
            if (idLocal != null) {
                idLocal = em.getReference(idLocal.getClass(), idLocal.getIdLocal());
                empleado.setIdLocal(idLocal);
            }
            Usuario usuario = empleado.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getIdUsuario());
                empleado.setUsuario(usuario);
            }
            Collection<Factura> attachedFacturaCollection = new ArrayList<Factura>();
            for (Factura facturaCollectionFacturaToAttach : empleado.getFacturaCollection()) {
                facturaCollectionFacturaToAttach = em.getReference(facturaCollectionFacturaToAttach.getClass(), facturaCollectionFacturaToAttach.getIdFactura());
                attachedFacturaCollection.add(facturaCollectionFacturaToAttach);
            }
            empleado.setFacturaCollection(attachedFacturaCollection);
            Collection<Servicio> attachedServicioCollection = new ArrayList<Servicio>();
            for (Servicio servicioCollectionServicioToAttach : empleado.getServicioCollection()) {
                servicioCollectionServicioToAttach = em.getReference(servicioCollectionServicioToAttach.getClass(), servicioCollectionServicioToAttach.getIdServicio());
                attachedServicioCollection.add(servicioCollectionServicioToAttach);
            }
            empleado.setServicioCollection(attachedServicioCollection);
            em.persist(empleado);
            if (idLocal != null) {
                idLocal.getEmpleadoCollection().add(empleado);
                idLocal = em.merge(idLocal);
            }
            if (usuario != null) {
                Empleado oldIdEmpleadoOfUsuario = usuario.getIdEmpleado();
                if (oldIdEmpleadoOfUsuario != null) {
                    oldIdEmpleadoOfUsuario.setUsuario(null);
                    oldIdEmpleadoOfUsuario = em.merge(oldIdEmpleadoOfUsuario);
                }
                usuario.setIdEmpleado(empleado);
                usuario = em.merge(usuario);
            }
            for (Factura facturaCollectionFactura : empleado.getFacturaCollection()) {
                Empleado oldIdEmpleadoOfFacturaCollectionFactura = facturaCollectionFactura.getIdEmpleado();
                facturaCollectionFactura.setIdEmpleado(empleado);
                facturaCollectionFactura = em.merge(facturaCollectionFactura);
                if (oldIdEmpleadoOfFacturaCollectionFactura != null) {
                    oldIdEmpleadoOfFacturaCollectionFactura.getFacturaCollection().remove(facturaCollectionFactura);
                    oldIdEmpleadoOfFacturaCollectionFactura = em.merge(oldIdEmpleadoOfFacturaCollectionFactura);
                }
            }
            for (Servicio servicioCollectionServicio : empleado.getServicioCollection()) {
                Empleado oldIdEmpleadoOfServicioCollectionServicio = servicioCollectionServicio.getIdEmpleado();
                servicioCollectionServicio.setIdEmpleado(empleado);
                servicioCollectionServicio = em.merge(servicioCollectionServicio);
                if (oldIdEmpleadoOfServicioCollectionServicio != null) {
                    oldIdEmpleadoOfServicioCollectionServicio.getServicioCollection().remove(servicioCollectionServicio);
                    oldIdEmpleadoOfServicioCollectionServicio = em.merge(oldIdEmpleadoOfServicioCollectionServicio);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Empleado empleado) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleado persistentEmpleado = em.find(Empleado.class, empleado.getIdEmpleado());
            Local idLocalOld = persistentEmpleado.getIdLocal();
            Local idLocalNew = empleado.getIdLocal();
            Usuario usuarioOld = persistentEmpleado.getUsuario();
            Usuario usuarioNew = empleado.getUsuario();
            Collection<Factura> facturaCollectionOld = persistentEmpleado.getFacturaCollection();
            Collection<Factura> facturaCollectionNew = empleado.getFacturaCollection();
            Collection<Servicio> servicioCollectionOld = persistentEmpleado.getServicioCollection();
            Collection<Servicio> servicioCollectionNew = empleado.getServicioCollection();
            if (idLocalNew != null) {
                idLocalNew = em.getReference(idLocalNew.getClass(), idLocalNew.getIdLocal());
                empleado.setIdLocal(idLocalNew);
            }
            if (usuarioNew != null) {
                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getIdUsuario());
                empleado.setUsuario(usuarioNew);
            }
            Collection<Factura> attachedFacturaCollectionNew = new ArrayList<Factura>();
            for (Factura facturaCollectionNewFacturaToAttach : facturaCollectionNew) {
                facturaCollectionNewFacturaToAttach = em.getReference(facturaCollectionNewFacturaToAttach.getClass(), facturaCollectionNewFacturaToAttach.getIdFactura());
                attachedFacturaCollectionNew.add(facturaCollectionNewFacturaToAttach);
            }
            facturaCollectionNew = attachedFacturaCollectionNew;
            empleado.setFacturaCollection(facturaCollectionNew);
            Collection<Servicio> attachedServicioCollectionNew = new ArrayList<Servicio>();
            for (Servicio servicioCollectionNewServicioToAttach : servicioCollectionNew) {
                servicioCollectionNewServicioToAttach = em.getReference(servicioCollectionNewServicioToAttach.getClass(), servicioCollectionNewServicioToAttach.getIdServicio());
                attachedServicioCollectionNew.add(servicioCollectionNewServicioToAttach);
            }
            servicioCollectionNew = attachedServicioCollectionNew;
            empleado.setServicioCollection(servicioCollectionNew);
            empleado = em.merge(empleado);
            if (idLocalOld != null && !idLocalOld.equals(idLocalNew)) {
                idLocalOld.getEmpleadoCollection().remove(empleado);
                idLocalOld = em.merge(idLocalOld);
            }
            if (idLocalNew != null && !idLocalNew.equals(idLocalOld)) {
                idLocalNew.getEmpleadoCollection().add(empleado);
                idLocalNew = em.merge(idLocalNew);
            }
            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
                usuarioOld.setIdEmpleado(null);
                usuarioOld = em.merge(usuarioOld);
            }
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                Empleado oldIdEmpleadoOfUsuario = usuarioNew.getIdEmpleado();
                if (oldIdEmpleadoOfUsuario != null) {
                    oldIdEmpleadoOfUsuario.setUsuario(null);
                    oldIdEmpleadoOfUsuario = em.merge(oldIdEmpleadoOfUsuario);
                }
                usuarioNew.setIdEmpleado(empleado);
                usuarioNew = em.merge(usuarioNew);
            }
            for (Factura facturaCollectionOldFactura : facturaCollectionOld) {
                if (!facturaCollectionNew.contains(facturaCollectionOldFactura)) {
                    facturaCollectionOldFactura.setIdEmpleado(null);
                    facturaCollectionOldFactura = em.merge(facturaCollectionOldFactura);
                }
            }
            for (Factura facturaCollectionNewFactura : facturaCollectionNew) {
                if (!facturaCollectionOld.contains(facturaCollectionNewFactura)) {
                    Empleado oldIdEmpleadoOfFacturaCollectionNewFactura = facturaCollectionNewFactura.getIdEmpleado();
                    facturaCollectionNewFactura.setIdEmpleado(empleado);
                    facturaCollectionNewFactura = em.merge(facturaCollectionNewFactura);
                    if (oldIdEmpleadoOfFacturaCollectionNewFactura != null && !oldIdEmpleadoOfFacturaCollectionNewFactura.equals(empleado)) {
                        oldIdEmpleadoOfFacturaCollectionNewFactura.getFacturaCollection().remove(facturaCollectionNewFactura);
                        oldIdEmpleadoOfFacturaCollectionNewFactura = em.merge(oldIdEmpleadoOfFacturaCollectionNewFactura);
                    }
                }
            }
            for (Servicio servicioCollectionOldServicio : servicioCollectionOld) {
                if (!servicioCollectionNew.contains(servicioCollectionOldServicio)) {
                    servicioCollectionOldServicio.setIdEmpleado(null);
                    servicioCollectionOldServicio = em.merge(servicioCollectionOldServicio);
                }
            }
            for (Servicio servicioCollectionNewServicio : servicioCollectionNew) {
                if (!servicioCollectionOld.contains(servicioCollectionNewServicio)) {
                    Empleado oldIdEmpleadoOfServicioCollectionNewServicio = servicioCollectionNewServicio.getIdEmpleado();
                    servicioCollectionNewServicio.setIdEmpleado(empleado);
                    servicioCollectionNewServicio = em.merge(servicioCollectionNewServicio);
                    if (oldIdEmpleadoOfServicioCollectionNewServicio != null && !oldIdEmpleadoOfServicioCollectionNewServicio.equals(empleado)) {
                        oldIdEmpleadoOfServicioCollectionNewServicio.getServicioCollection().remove(servicioCollectionNewServicio);
                        oldIdEmpleadoOfServicioCollectionNewServicio = em.merge(oldIdEmpleadoOfServicioCollectionNewServicio);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = empleado.getIdEmpleado();
                if (findEmpleado(id) == null) {
                    throw new NonexistentEntityException("The empleado with id " + id + " no longer exists.");
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
            Empleado empleado;
            try {
                empleado = em.getReference(Empleado.class, id);
                empleado.getIdEmpleado();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empleado with id " + id + " no longer exists.", enfe);
            }
            Local idLocal = empleado.getIdLocal();
            if (idLocal != null) {
                idLocal.getEmpleadoCollection().remove(empleado);
                idLocal = em.merge(idLocal);
            }
            Usuario usuario = empleado.getUsuario();
            if (usuario != null) {
                usuario.setIdEmpleado(null);
                usuario = em.merge(usuario);
            }
            Collection<Factura> facturaCollection = empleado.getFacturaCollection();
            for (Factura facturaCollectionFactura : facturaCollection) {
                facturaCollectionFactura.setIdEmpleado(null);
                facturaCollectionFactura = em.merge(facturaCollectionFactura);
            }
            Collection<Servicio> servicioCollection = empleado.getServicioCollection();
            for (Servicio servicioCollectionServicio : servicioCollection) {
                servicioCollectionServicio.setIdEmpleado(null);
                servicioCollectionServicio = em.merge(servicioCollectionServicio);
            }
            em.remove(empleado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Empleado> findEmpleadoEntities() {
        return findEmpleadoEntities(true, -1, -1);
    }

    public List<Empleado> findEmpleadoEntities(int maxResults, int firstResult) {
        return findEmpleadoEntities(false, maxResults, firstResult);
    }

    private List<Empleado> findEmpleadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Empleado.class));
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

    public Empleado findEmpleado(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Empleado.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmpleadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Empleado> rt = cq.from(Empleado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
