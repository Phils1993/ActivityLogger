import config.HibernateConfig;
import daos.ActivityDAO;
import enums.ExerciseType;
import jakarta.persistence.EntityManagerFactory;
import populator.Populator;
import services.ActivityServices;
import services.CityServices;
import services.WeatherServices;

public class Main {
    public static void main(String[] args) {

        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

        // DAOs
        ActivityDAO activityDAO = new ActivityDAO(emf);
        CityServices cityServices = new CityServices();
        WeatherServices weatherServices = new WeatherServices();

        // Services
        ActivityServices activityServices = new ActivityServices(activityDAO);

        Populator populator = Populator.builder()
                .activityServices(activityServices)
                .cityServices(cityServices)
                .weatherServices(weatherServices)
                .build();

// Create a single activity for København
        try {
            populator.createActivityForCity("København", ExerciseType.BIKE);
            populator.createActivityForCity("Århus", ExerciseType.RUN);
            populator.createActivityForCity("Odense", ExerciseType.BIKE);
            populator.createActivityForCity("Roskilde",  ExerciseType.SWIM);
            populator.createActivityForCity("Aalborg",  ExerciseType.SWIM);
            populator.createActivityForCity("Esbjerg", ExerciseType.HIKE);
            populator.createActivityForCity("Randers", ExerciseType.HIKE);
            populator.createActivityForCity("Kolding", ExerciseType.BIKE);
            populator.createActivityForCity("Horsens", ExerciseType.WALKING);
            populator.createActivityForCity("Vejle", ExerciseType.RUN);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        populator.deleteActivity(5);

        populator.updateActivityComment(4,"UPDATED");

        populator.listAllActivities();

        populator.updateActivityType(8, ExerciseType.BIKE);


        emf.close();


    }

}

