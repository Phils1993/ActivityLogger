import config.HibernateConfig;
import daos.ActivityDAO;
import entities.Activity;
import enums.ExerciseType;
import exceptions.ApiException;
import jakarta.persistence.EntityManagerFactory;
import populator.Populator;
import services.ActivityServices;
import services.CityServices;
import services.WeatherServices;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;


public class Main {
    public static void main(String[] args) {
        final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        ExecutorService executor = Executors.newFixedThreadPool(5);

        long startTime = System.nanoTime();

        try {
            // Task 1: Add activities (Callable<Integer> returns number of activities added)
            Callable<Integer> addTask = () -> {
                addActivities();
                return 10;
            };

            Future<Integer> addFuture = executor.submit(addTask);
            int addedCount = addFuture.get(); // wait for completion

            // After addTask completes, create other tasks
            List<Callable<Object>> tasks = new ArrayList<>();

            // Task 2: Delete activity
            tasks.add(() -> {
                deleteActivities(5);
                return true;
            });

            // Task 3: Update comment
            tasks.add(() -> {
                updateActivityComment(4, "UPDATED");
                return true;
            });

            // Task 4: Update type
            tasks.add(() -> {
                updateActivityType( 8, ExerciseType.BIKE);
                return true;
            });

            // Task 5: List all activities
            tasks.add(() -> {
                listAllActivities();
                ActivityDAO dao = new ActivityDAO(emf);
                return dao.getAll();
            });

            // Submit all tasks
            List<Future<Object>> futures = executor.invokeAll(tasks);

            // Process results
            for (Future<Object> future : futures) {
                try {
                    Object result = future.get();
                    System.out.println("Task result: " + result);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
            try {
                if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }

        // Stop timer
        long endTime = System.nanoTime();
        long durationMillis = (endTime - startTime) / 1_000_000;
        System.out.println("Total execution time: " + durationMillis + " ms");

        // Print final state of activities
        ActivityDAO activityDAO = new ActivityDAO(emf);
        List<Activity> activities = activityDAO.getAll();
        activities.forEach(System.out::println);

        emf.close();
    }

    public static void addActivities() {
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
        try {
            populator.createActivityForCity("Silkeborg", ExerciseType.BIKE);
            populator.createActivityForCity("Århus", ExerciseType.RUN);
            populator.createActivityForCity("Odense", ExerciseType.BIKE);
            populator.createActivityForCity("Roskilde", ExerciseType.SWIM);
            populator.createActivityForCity("Aalborg", ExerciseType.SWIM);
            populator.createActivityForCity("Esbjerg", ExerciseType.HIKE);
            populator.createActivityForCity("Randers", ExerciseType.HIKE);
            populator.createActivityForCity("Kolding", ExerciseType.BIKE);
            populator.createActivityForCity("Horsens", ExerciseType.WALKING);
            populator.createActivityForCity("Vejle", ExerciseType.RUN);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void deleteActivities(int activityId) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        ActivityDAO activityDAO = new ActivityDAO(emf);
        CityServices cityServices = new CityServices();
        WeatherServices weatherServices = new WeatherServices();
        ActivityServices activityServices = new ActivityServices(activityDAO);
        Populator populator = new Populator(activityServices, cityServices, weatherServices);
        populator.deleteActivity(activityId);

    }

    public static void updateActivityComment(int activityId, String newComment) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        ActivityDAO activityDAO = new ActivityDAO(emf);
        CityServices cityServices = new CityServices();
        WeatherServices weatherServices = new WeatherServices();
        ActivityServices activityServices = new ActivityServices(activityDAO);
        Populator populator = new Populator(activityServices, cityServices, weatherServices);
        populator.updateActivityComment(activityId, newComment);

    }

    public static void updateActivityType(int activityId, ExerciseType newExerciseType) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        ActivityDAO activityDAO = new ActivityDAO(emf);
        CityServices cityServices = new CityServices();
        WeatherServices weatherServices = new WeatherServices();
        ActivityServices activityServices = new ActivityServices(activityDAO);
        Populator populator = new Populator(activityServices, cityServices, weatherServices);
        populator.updateActivityType(activityId, newExerciseType);
    }

    public static void listAllActivities() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        ActivityDAO activityDAO = new ActivityDAO(emf);
        CityServices cityServices = new CityServices();
        WeatherServices weatherServices = new WeatherServices();
        ActivityServices activityServices = new ActivityServices(activityDAO);
        Populator populator = new Populator(activityServices, cityServices, weatherServices);
        populator.listAllActivities();

    }
}



