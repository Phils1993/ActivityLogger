package services;

import dtos.ActivityDTO;
import dtos.CityInfoDTO;
import dtos.WeatherInfoDTO;
import enums.ExerciseType;

import java.time.LocalDate;
import java.time.LocalTime;

public class ActivityServices {

    private WeatherServices weatherServices;
    private CityServices cityServices;


    public ActivityServices(WeatherServices weatherServices, CityServices cityServices) {
        this.weatherServices = weatherServices;
        this.cityServices = cityServices;
    }

    /*
     * Creates an ActivityDTO enriched with weather and city data.
     *
     * @param exerciseType type of exercise (ex. RUNNING, CYCLING, etc.)
     * @param city         city name (ex. "Copenhagen")
     * @param duration     duration in minutes
     * @param distance     distance in km
     * @param comment      optional comment
     * @return enriched ActivityDTO
     */

    public ActivityDTO createActivity(ExerciseType exerciseType, String city, int duration, double distance, String comment) {
        WeatherInfoDTO weatherInfoDTO = weatherServices.getWeatherInfo(city);
        CityInfoDTO[] cityInfo = cityServices.getCityInfo(city);

        return ActivityDTO.builder()
                .executionDate(LocalDate.now())
                .exerciseType(exerciseType)
                .timeOfDay(LocalTime.now())
                .duration(duration)
                .distance(distance)
                .comment(comment)
                .weatherInfoDTO(weatherInfoDTO)
                .cityInfoDTO((cityInfo !=null && cityInfo.length > 0) ? cityInfo[0] : null)
                .build();
    }

}
