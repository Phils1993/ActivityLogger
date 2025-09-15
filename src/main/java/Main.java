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
import threads.Run;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static threads.Run.RunThreads;


public class Main {
    public static void main(String[] args) {

        final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

        RunThreads();


        emf.close();
    }


}



