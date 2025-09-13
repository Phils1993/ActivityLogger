package services;

import daos.ActivityDAO;
import dtos.*;
import entities.Activity;
import entities.CityInfo;
import entities.WeatherInfo;

import java.util.List;
import java.util.stream.Collectors;

public class ActivityServices {

    private final ActivityDAO activityDAO;

    public ActivityServices(ActivityDAO activityDAO) {
        this.activityDAO = activityDAO;
    }

    // -----------------------
    // Overloaded method: create from ActivityDTO
    // -----------------------
    public ActivityDTO createActivity(ActivityDTO dto) {
        Activity entity = dtoToEntity(dto);
        Activity persisted = activityDAO.create(entity);
        return entityToDTO(persisted);
    }

    // -----------------------
    // Overloaded method: create from Activity entity
    // -----------------------
    public ActivityDTO createActivity(Activity entity) {
        Activity persisted = activityDAO.create(entity);
        return entityToDTO(persisted);
    }

    // -----------------------
    // Convert DTO → Entity
    // -----------------------
    private Activity dtoToEntity(ActivityDTO dto) {
        Activity entity = new Activity();

        entity.setExecutionDate(dto.getExecutionDate());
        entity.setExerciseType(dto.getExerciseType());
        entity.setTimeOfDay(dto.getTimeOfDay());
        entity.setDuration(dto.getDuration());
        entity.setDistance(dto.getDistance());
        entity.setComment(dto.getComment());

        if (dto.getCityInfoDTO() != null) {
            CityInfoDTO cityDTO = dto.getCityInfoDTO();
            CityInfo city = new CityInfo();
            city.setName(cityDTO.getName());
            city.setLatitude(cityDTO.getLatitude());
            city.setLongitude(cityDTO.getLongitude());
            city.setElevation(cityDTO.getElevation());
            city.addActivity(entity); // link
        }

        if (dto.getWeatherInfoDTO() != null && dto.getWeatherInfoDTO().getCurrentWeatherUnits() != null) {
            CurrentWeatherUnitsDTO weatherDTO = dto.getWeatherInfoDTO().getCurrentWeatherUnits();
            WeatherInfo weather = new WeatherInfo();

            weather.setTime(weatherDTO.getTime());
            weather.setTemperature(parseDouble(weatherDTO.getTemperature()));
            weather.setWindspeed(parseDouble(weatherDTO.getWindspeed()));
            weather.setWinddirection(parseInt(weatherDTO.getWinddirection()));
            weather.setIsDay(parseInt(weatherDTO.getIsDay()));
            weather.setWeathercode(parseInt(weatherDTO.getWeathercode()));

            entity.setWeatherInfo(weather); // link
        }

        return entity;
    }

    // -----------------------
    // Convert Entity → DTO
    // -----------------------
    private ActivityDTO entityToDTO(Activity entity) {
        CityInfoDTO cityDTO = null;
        if (entity.getCityInfo() != null) {
            cityDTO = CityInfoDTO.builder()
                    .id(entity.getId())
                    .name(entity.getCityInfo().getName())
                    .latitude(entity.getCityInfo().getLatitude())
                    .longitude(entity.getCityInfo().getLongitude())
                    .elevation(entity.getCityInfo().getElevation())
                    .timezone(entity.getCityInfo().getTimezone())
                    .population(entity.getCityInfo().getPopulation())
                    .postcodes(entity.getCityInfo().getPostcodes())
                    .country(entity.getCityInfo().getCountry())
                    .build();
        }

        CurrentWeatherDTO weatherDTO = null;
        if (entity.getWeatherInfo() != null) {
            weatherDTO = CurrentWeatherDTO.builder()
                    .time(entity.getWeatherInfo().getTime())
                    .temperature(entity.getWeatherInfo().getTemperature())
                    .windspeed(entity.getWeatherInfo().getWindspeed())
                    .winddirection(entity.getWeatherInfo().getWinddirection())
                    .isDay(entity.getWeatherInfo().getIsDay())
                    .weathercode(entity.getWeatherInfo().getWeathercode())
                    .interval(0)
                    .build();
        }

        WeatherInfoDTO weatherInfoDTO = WeatherInfoDTO.builder()
                .currentWeatherUnits(null) // optional: fill if needed
                .currentWeather(weatherDTO)
                .build();

        return ActivityDTO.builder()
                .executionDate(entity.getExecutionDate())
                .exerciseType(entity.getExerciseType())
                .timeOfDay(entity.getTimeOfDay())
                .duration(entity.getDuration())
                .distance(entity.getDistance())
                .comment(entity.getComment())
                .cityInfoDTO(cityDTO)
                .weatherInfoDTO(weatherInfoDTO)
                .build();
    }

    // -----------------------
    // Helper methods for parsing
    // -----------------------
    private double parseDouble(String value) {
        try {
            return value != null ? Double.parseDouble(value) : 0;
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private int parseInt(String value) {
        try {
            return value != null ? Integer.parseInt(value) : 0;
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    // -----------------------
    // Batch creation from list of DTOs
    // -----------------------
    public List<ActivityDTO> createActivities(List<ActivityDTO> dtos) {
        return dtos.stream()
                .map(this::createActivity)
                .collect(Collectors.toList());
    }


    public Activity findActivityById(int id) {
        return activityDAO.find(id);
    }

    public void updateActivity(Activity activity) {
        activityDAO.update(activity);
    }

    public void deleteActivity(Activity activity) {
        activityDAO.delete(activity);
    }

    public List<Activity> getAllActivities() {
        return activityDAO.getAll();
    }
}
