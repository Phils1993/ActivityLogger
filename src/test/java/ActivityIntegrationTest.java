import dtos.ActivityDTO;
import dtos.CityInfoDTO;
import dtos.WeatherInfoDTO;
import enums.ExerciseType;
import org.junit.jupiter.api.Test;
import services.ActivityServices;
import services.CityServices;
import services.WeatherServices;

import static org.junit.jupiter.api.Assertions.*;

class ActivityIntegrationTest {

    private final WeatherServices weatherServices = new WeatherServices();
    private final CityServices cityServices = new CityServices();
    private final ActivityServices activityServices = new ActivityServices(weatherServices, cityServices);

    @Test
    void testWeatherServiceReturnsData() {
        WeatherInfoDTO weatherInfo = weatherServices.getWeatherInfo("København");

        assertNotNull(weatherInfo, "Weather info should not be null");
        assertNotNull(weatherInfo.getLocationName(), "Weather info should contain location name");
        assertNotNull(weatherInfo.getCurrentData(), "Weather info should contain current data");
        System.out.println("Weather data: " + weatherInfo);
    }

    @Test
    void testCityServiceReturnsData() {
        CityInfoDTO[] cityInfo = cityServices.getCityInfo("København");

        assertNotNull(cityInfo, "City info should not be null");
        assertTrue(cityInfo.length > 0, "City info should contain at least one entry");
        assertNotNull(cityInfo[0].getPrimaryName(), "City info should contain primary name");
        System.out.println("City data: " + cityInfo[0]);
    }

    @Test
    void testActivityServiceCreatesEnrichedActivity() {
        ActivityDTO activity = activityServices.createActivity(
                ExerciseType.RUN,
                "København",
                45,
                10.0,
                "Integration test run"
        );

        assertNotNull(activity, "Activity should not be null");
        assertEquals(ExerciseType.RUN, activity.getExerciseType());
        assertEquals(45, activity.getDuration());
        assertEquals(10.0, activity.getDistance());

        assertNotNull(activity.getWeatherInfoDTO(), "Weather info should not be null");
        assertNotNull(activity.getCityInfoDTO(), "City info should not be null");
        System.out.println("Activity data: " + activity);
    }
}
