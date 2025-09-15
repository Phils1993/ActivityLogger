package daos;

import app.config.HibernateConfig;
import app.daos.ActivityDAO;
import app.entities.Activity;
import app.enums.ExerciseType;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class ActivityDAOTest {

    private static EntityManagerFactory emf;
    private ActivityDAO activityDAO;

    @BeforeAll
    static void init() {
        // Using H2 in-memory database configured in persistence.xml
        emf = HibernateConfig.getEntityManagerFactoryForTest();
    }

    @AfterAll
    static void close() {
        if (emf != null) {
            emf.close();
        }
    }

    @BeforeEach
    void setUp() {
        activityDAO = new ActivityDAO(emf);
    }

    @Test
    void testCreateAndFindActivity() {
        Activity activity = new Activity();
        activity.setExerciseType(ExerciseType.RUN);
        activity.setDuration(30);
        activity.setDistance(5.0);

        Activity saved = activityDAO.create(activity);

        assertNotNull(saved.getId(), "Saved activity should have an ID");

        Activity found = activityDAO.find(saved.getId());
        assertNotNull(found, "Should find the activity by ID");
        assertEquals(ExerciseType.RUN, found.getExerciseType());
        assertEquals(30, found.getDuration());
        assertEquals(5.0, found.getDistance());
    }

    @Test
    void testUpdateActivity() {
        Activity activity = new Activity();
        activity.setExerciseType(ExerciseType.CYCLE);
        activity.setDuration(60);
        activity.setDistance(20.0);

        Activity saved = activityDAO.create(activity);

        saved.setDuration(90);
        boolean updated = activityDAO.update(saved);
        assertTrue(updated, "Update should return true");

        Activity found = activityDAO.find(saved.getId());
        assertEquals(90, found.getDuration(), "Duration should be updated");
    }

    @Test
    void testDeleteActivity() {
        Activity activity = new Activity();
        activity.setExerciseType(ExerciseType.SWIM);
        activity.setDuration(45);
        activity.setDistance(2.0);

        Activity saved = activityDAO.create(activity);
        boolean deleted = activityDAO.delete(saved);
        assertTrue(deleted, "Delete should return true");

        Activity found = activityDAO.find(saved.getId());
        assertNull(found, "Deleted activity should not be found");
    }

    @Test
    void testGetAllActivities() {
        Activity a1 = new Activity();
        a1.setExerciseType(ExerciseType.RUN);
        a1.setDuration(20);
        a1.setDistance(3.0);

        Activity a2 = new Activity();
        a2.setExerciseType(ExerciseType.CYCLE);
        a2.setDuration(50);
        a2.setDistance(15.0);

        activityDAO.create(a1);
        activityDAO.create(a2);

        List<Activity> activities = activityDAO.getAll();
        assertTrue(activities.size() >= 2, "Should retrieve at least 2 activities");
    }
}