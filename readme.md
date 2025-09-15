# Activity Tracker Application

Friday Task 12 sep.
https://3semfall2025.kursusmaterialer.dk/backend/java-deepdive-2/exercises/activity-logger-part2/


---

## Package Structure

```java
package daos;       // Data Access Objects for CRUD
package dtos;       // Data Transfer Objects
package entities;   // Database Entities
package enums;      // Enumerations
package services;   // Business Logic / Service Layer
```

#Entities

````java

@Entity
class Activity {
    int id;
    LocalDate executionDate;
    ExerciseType exerciseType;
    LocalTime timeOfDay;
    int duration;
    double distance;
    String comment;
    CityInfo cityInfo;        // Many-to-One
    WeatherInfo weatherInfo;  // One-to-One
}
`````

``` java
@Entity
class CityInfo {
    int id;
    String name;
    double latitude, longitude, elevation;
    String country, timezone;
    long population;
    List<String> postcodes;
    Set<Activity> activities; // One-to-Many
}
````

`````java

@Entity
class WeatherInfo {
    int id;
    String time;
    double temperature, windspeed;
    int winddirection, isDay, weathercode;
    Activity activity;         // One-to-One mapped
}

``````

# DTO (data transfer object)

````java
class ActivityDTO {
    LocalDate executionDate;
    ExerciseType exerciseType;
    LocalTime timeOfDay;
    int duration;
    double distance;
    String comment;
    CityInfoDTO cityInfoDTO;
    WeatherInfoDTO weatherInfoDTO;
}
````

`````java
class CityInfoDTO {
    int id;
    String name;
    double latitude, longitude, elevation;
    String country, timezone;
    long population;
    List<String> postcodes;
}
``````

````java
class WeatherInfoDTO {
    double latitude, longitude, elevation;
    CurrentWeatherDTO currentWeather;
    CurrentWeatherUnitsDTO currentWeatherUnits;
}

````

````java
class CurrentWeatherDTO {
    String time;
    int interval;
    double temperature, windspeed;
    int winddirection, isDay, weathercode;
}

````

#DAO interface

````java
interface IDAO<T, I> {
    T create(T t);

    boolean update(T t);

    boolean delete(T t);

    T find(I id);

    List<T> getAll();
}

````

````java
class ActivityDAO implements IDAO<Activity, Integer> {
    Activity create(Activity activity);

    boolean update(Activity activity);

    boolean delete(Activity activity);

    Activity find(Integer id);

    List<Activity> getAll();
}

````

````java
class CityInfoDAO implements IDAO<CityInfo, Integer> {
    // Not implemented yet
}

````

````java
class WeatherDAO implements IDAO<WeatherInfo, Integer> {
    // Not implemented yet
}

````

#Services

````java
class ActivityServices {
    ActivityDTO createActivity(ActivityDTO dto);

    ActivityDTO createActivity(Activity entity);

    List<ActivityDTO> createActivities(List<ActivityDTO> dtos);

    Activity findActivityById(int id);

    void updateActivity(Activity activity);

    void deleteActivity(Activity activity);

    List<Activity> getAllActivities();
}

````

````java
class CityServices {
    CityInfoDTO getCityInfo(String cityName);
}

````

````java
class WeatherServices {
    WeatherInfoDTO getWeatherInfo(double latitude, double longitude);
}

````

#Enums

````java
enum ExerciseType {
    RUN,
    SWIM,
    BIKE,
    HIKE,
    WALKING;
}

````

## Usage:

````java
// Initialize services
ActivityServices activityServices = new ActivityServices(new ActivityDAO(entityManagerFactory));
CityServices cityServices = new CityServices();
WeatherServices weatherServices = new WeatherServices();

// Initialize Populator
Populator populator = Populator.builder()
        .activityServices(activityServices)
        .cityServices(cityServices)
        .weatherServices(weatherServices)
        .build();

// -----------------------
// CREATE ACTIVITY
// -----------------------
populator.

createActivityForCity("Berlin",ExerciseType.RUN);

// -----------------------
// UPDATE ACTIVITY
// -----------------------
populator.

updateActivityComment(1,"Evening run instead of morning");
populator.

updateActivityType(1,ExerciseType.BIKE);

// -----------------------
// DELETE ACTIVITY
// -----------------------
populator.

deleteActivity(1);

// -----------------------
// LIST ALL ACTIVITIES
// -----------------------
populator.

listAllActivities();

``````

