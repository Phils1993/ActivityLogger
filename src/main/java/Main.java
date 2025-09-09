import dtos.ActivityDTO;
import dtos.CityInfoDTO;
import dtos.WeatherInfoDTO;
import enums.ExerciseType;
import services.ActivityServices;
import services.CityServices;
import services.WeatherServices;

import java.sql.Array;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        WeatherServices weatherService = new WeatherServices();
        CityServices cityService = new CityServices();


        // get city info
        System.out.println("Get city info");
        CityInfoDTO[] cityInfoDTO = cityService.getCityInfo("Lyngby");
        System.out.println(cityInfoDTO);
        Arrays.stream(cityInfoDTO).forEach(System.out::println);


        // get weather info
        System.out.println("Get weather info");
        WeatherInfoDTO weatherInfoDTO = weatherService.getWeatherInfo("Ribe");
        System.out.println(weatherInfoDTO);

        System.out.println("Trying getting location: ");
        System.out.println(weatherInfoDTO.getLocationName());


        // create a new activity
        ActivityServices activityServices = new ActivityServices(new WeatherServices(), new CityServices());
        System.out.println("Get activity info");
        ActivityDTO morningRun = activityServices.createActivity(ExerciseType.RUN,
                "København",
                22,
                5,
                "just a chill morning run");
        System.out.println(morningRun);




    }
}
