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
public class TokenVerificadoJpaController implements Serializable {

    public TokenVerificadoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TokenVerificado tokenVerificado) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(tokenVerificado);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTokenVerificado(tokenVerificado.getToken()) != null) {
                throw new PreexistingEntityException("TokenVerificado " + tokenVerificado + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TokenVerificado tokenVerificado) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            tokenVerificado = em.merge(tokenVerificado);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = tokenVerificado.getToken();
                if (findTokenVerificado(id) == null) {
                    throw new NonexistentEntityException("The tokenVerificado with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TokenVerificado tokenVerificado;
            try {
                tokenVerificado = em.getReference(TokenVerificado.class, id);
                tokenVerificado.getToken();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tokenVerificado with id " + id + " no longer exists.", enfe);
            }
            em.remove(tokenVerificado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TokenVerificado> findTokenVerificadoEntities() {
        return findTokenVerificadoEntities(true, -1, -1);
    }

    public List<TokenVerificado> findTokenVerificadoEntities(int maxResults, int firstResult) {
        return findTokenVerificadoEntities(false, maxResults, firstResult);
    }

    private List<TokenVerificado> findTokenVerificadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TokenVerificado.class));
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

    public TokenVerificado findTokenVerificado(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TokenVerificado.class, id);
        } finally {
            em.close();
        }
    }

    public int getTokenVerificadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TokenVerificado> rt = cq.from(TokenVerificado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
