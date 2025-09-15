package app.populator;

import app.entities.Activity;
import app.entities.CityInfo;
import app.entities.WeatherInfo;
import app.enums.ExerciseType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;
import app.services.ActivityServices;
import app.services.CityServices;
import app.services.WeatherServices;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@Builder
@ToString
public class Populator {

    private final ActivityServices activityServices;
    private final CityServices cityServices;
    private final WeatherServices weatherServices;

    // -----------------------
    // CREATE ACTIVITY
    // -----------------------
    public void createActivityForCity(String cityName, ExerciseType exerciseType) {
        var cityDTO = cityServices.getCityInfo(cityName);
        if (cityDTO == null) {
            System.out.println("City not found: " + cityName);
            return;
        }

        var weatherDTO = weatherServices.getWeatherInfo(cityDTO.getLatitude(), cityDTO.getLongitude());
        if (weatherDTO == null || weatherDTO.getCurrentWeather() == null) {
            System.out.println("Weather info not available for " + cityName);
            return;
        }

        CityInfo city = CityInfo.builder()
                .name(cityDTO.getName())
                .latitude(cityDTO.getLatitude())
                .longitude(cityDTO.getLongitude())
                .elevation(cityDTO.getElevation())
                .country(cityDTO.getCountry())
                .timezone(cityDTO.getTimezone())
                .population(cityDTO.getPopulation())
                .postcodes(cityDTO.getPostcodes())
                .build();

        WeatherInfo weather = WeatherInfo.builder()
                .time(weatherDTO.getCurrentWeather().getTime())
                .temperature(weatherDTO.getCurrentWeather().getTemperature())
                .windspeed(weatherDTO.getCurrentWeather().getWindspeed())
                .winddirection(weatherDTO.getCurrentWeather().getWinddirection())
                .isDay(weatherDTO.getCurrentWeather().getIsDay())
                .weathercode(weatherDTO.getCurrentWeather().getWeathercode())
                .build();

        Activity activity = Activity.builder()
                .executionDate(LocalDate.now())
                .exerciseType(exerciseType)
                .timeOfDay(LocalTime.now())
                .duration(45)
                .distance(10.0)
                .comment("Morning run in " + cityName)
                .cityInfo(city)
                .weatherInfo(weather)
                .build();

        activityServices.createActivity(activity);
        System.out.println("Activity created successfully in " + cityName);
    }

    // -----------------------
    // UPDATE ACTIVITY Comment
    // -----------------------
    public void updateActivityComment(int activityId, String newComment) {
        Activity activity = activityServices.findActivityById(activityId);
        if (activity != null) {
            activity.setComment(newComment);
            activityServices.updateActivity(activity);
            System.out.println("Activity ID " + activityId + " updated. New comment: " + newComment);
        } else {
            System.out.println("Activity ID " + activityId + " not found.");
        }
    }

    public void updateActivityType(int activityId, ExerciseType exerciseType) {
        Activity activity = activityServices.findActivityById(activityId);
        if (activity != null) {
            activity.setExerciseType(exerciseType);
            activityServices.updateActivity(activity);
            System.out.println("Activity ID " + activityId + " updated. New comment: " + exerciseType);
        } else {
            System.out.println("Activity ID " + activityId + " not found.");
        }
    }

    // -----------------------
    // DELETE ACTIVITY
    // -----------------------
    public void deleteActivity(int activityId) {
        Activity activity = activityServices.findActivityById(activityId);
        if (activity != null) {
            activityServices.deleteActivity(activity);
            System.out.println("Activity ID " + activityId + " deleted successfully.");
        } else {
            System.out.println("Activity ID " + activityId + " not found.");
        }
    }

    // -----------------------
    // LIST ALL ACTIVITIES
    // -----------------------
    public void listAllActivities() {
        List<Activity> activities = activityServices.getAllActivities();

        if (activities.isEmpty()) {
            System.out.println("No activities found.");
            return;
        }

        System.out.println("All activities:");
        activities.stream()
                .forEach(act -> System.out.println(
                        "ID: " + act.getId() +
                                ", City: " + (act.getCityInfo() != null ? act.getCityInfo().getName() : "N/A") +
                                ", Type: " + act.getExerciseType() +
                                ", Date: " + act.getExecutionDate() +
                                ", Comment: " + act.getComment()
                ));
    }
}
