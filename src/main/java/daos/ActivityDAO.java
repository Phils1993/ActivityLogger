package daos;

import dtos.ActivityDTO;
import entities.Activity;
import exceptions.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class ActivityDAO implements IDAO <ActivityDTO, Integer>{
    private final EntityManagerFactory emf;
    public ActivityDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }


    @Override
    public ActivityDTO create(ActivityDTO activityDTO) {
        return null;
    }

    @Override
    public boolean update(ActivityDTO activityDTO) {
        return false;
    }

    @Override
    public boolean delete(ActivityDTO activityDTO) {
        return false;
    }

    @Override
    public ActivityDTO find(Integer id) {
        return null;
    }

    @Override
    public List<ActivityDTO> getAll() {
        return List.of();
    }
}
