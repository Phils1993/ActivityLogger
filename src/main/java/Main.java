import dtos.ActivityDTO;
import dtos.CityInfoDTO;
import dtos.WeatherInfoDTO;
import enums.ExerciseType;
import services.ActivityServices;
import services.CityServices;
import services.WeatherServices;

public class Main {
    public static void main(String[] args) {

        WeatherServices weatherService = new WeatherServices();
        CityServices cityService = new CityServices();

        // get city info
        System.out.println("Get city info");
        CityInfoDTO cityInfoDTO = cityService.getCityInfo("Lyngby");
        System.out.println(cityInfoDTO);

        // get weather info (using city lat/lon)
        if (cityInfoDTO != null) {
            System.out.println("Get weather info");
            WeatherInfoDTO weatherInfoDTO = weatherService.getWeatherInfo(
                    cityInfoDTO.getLatitude(),
                    cityInfoDTO.getLongitude()
            );
            System.out.println(weatherInfoDTO);
        }

        // create a new activity
        ActivityServices activityServices = new ActivityServices(weatherService, cityService);
        System.out.println("Get activity info");
        ActivityDTO morningRun = activityServices.createActivity(
                ExerciseType.RUN,
                "København",
                22,
                5,
                "just a chill morning run"
        );
        System.out.println(morningRun);
    }
}

