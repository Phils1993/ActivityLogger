package daos;

import dtos.ActivityDTO;
import entities.Activity;
import exceptions.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class ActivityDAO implements IDAO <Activity, Integer>{
    private final EntityManagerFactory emf;

    public ActivityDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }


    @Override
    public Activity create(Activity activity) {
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.persist(activity);
            em.getTransaction().commit();
            return activity;
        } catch(ApiException e){
            throw new RuntimeException( "Error creating new activity", e);
        }
    }

    @Override
    public boolean update(Activity activity) {
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.merge(activity);
            em.getTransaction().commit();
            return true;
        }
    }

    @Override
    public boolean delete(Activity activity) {
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            // Merge to attach it to the current session
            Activity attached = em.merge(activity);
            em.remove(attached);

            em.getTransaction().commit();
            return true;
        }
    }

    @Override
    public Activity find(Integer id) {
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.find(Activity.class, id);
            em.getTransaction().commit();
            return em.find(Activity.class, id);
        }
    }

    @Override
    public List<Activity> getAll() {
        try(EntityManager em = emf.createEntityManager()){
            TypedQuery<Activity> query = em.createQuery("select a from Activity a", Activity.class);
            return query.getResultList();
        }
    }
}
