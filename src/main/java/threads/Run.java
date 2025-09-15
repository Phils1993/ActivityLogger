package threads;

import config.HibernateConfig;
import daos.ActivityDAO;
import entities.Activity;
import enums.ExerciseType;
import jakarta.persistence.EntityManagerFactory;
import populator.Populator;
import services.ActivityServices;
import services.CityServices;
import services.WeatherServices;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Run {

    // this method runs a thread pool of callables and are being called from main.
    public static void RunThreads() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        ExecutorService executor = Executors.newFixedThreadPool(5);

        // Start timer for measuring execution time
        long startTime = System.nanoTime();

        try {
            // Task 1: Add activities (returns number of activities added)
            Callable<Integer> addTask = () -> {
                addActivities(); // adds some activities
                return 10;       // assume 10 activities were added
            };

            // Submit addTask to executor and wait until it completes
            Future<Integer> addFuture = executor.submit(addTask);
            int addedCount = addFuture.get(); // blocks until addTask finishes

            // After addTask is complete, prepare a list of other tasks
            List<Callable<Object>> tasks = new ArrayList<>();

            // Task 2: Delete some activities
            tasks.add(() -> {
                deleteActivities(5); // deletes 5 activities
                return true;         // return value is just a dummy result
            });

            // Task 3: Update the comment of a specific activity
            tasks.add(() -> {
                updateActivityComment(4, "UPDATED"); // updates activity with id=4
                return true;
            });

            // Task 4: Update the type of a specific activity
            tasks.add(() -> {
                updateActivityType(8, ExerciseType.BIKE); // sets activity 8 type to BIKE
                return true;
            });

            // Task 5: List all activities and return them
            tasks.add(() -> {
                listAllActivities();              // prints/logs activities
                ActivityDAO dao = new ActivityDAO(emf);
                return dao.getAll();              // returns list of all activities
            });

            // Submit all tasks at once and wait for all to complete
            List<Future<Object>> futures = executor.invokeAll(tasks);

            // Process results from each task
            for (Future<Object> future : futures) {
                try {
                    Object result = future.get(); // block until task completes
                    System.out.println("Task result: " + result);
                } catch (ExecutionException e) {
                    e.printStackTrace(); // if a task threw an exception
                }
            }

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            // Shut down executor service
            executor.shutdown();
            try {
                // Wait up to 60 seconds for tasks to finish
                if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                    executor.shutdownNow(); // force shutdown if still running
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }


// Stop timer and calculate total duration
        long endTime = System.nanoTime();
        long durationMillis = (endTime - startTime) / 1_000_000;
        System.out.println("Total execution time: " + durationMillis + " ms");

// Print final state of all activities from database
        ActivityDAO activityDAO = new ActivityDAO(emf);
        List<Activity> activities = activityDAO.getAll();
        activities.forEach(System.out::println);

    }

    // Helper methods instead of creating with @ Builder in main
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

