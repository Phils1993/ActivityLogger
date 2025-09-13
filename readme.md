CityInfoResponseDTO → handles the wrapper around "results".

CityInfoDTO → one city entry.

WeatherInfoDTO → full weather response.

CurrentWeatherDTO → actual measured weather values.

CurrentWeatherUnitsDTO → tells you the units for those values.


Relationships:
Relationships

Here’s one clean design:

Activity → Many-to-One → CityInfo

Many activities can happen in the same city.

Example: 10 runs in Copenhagen → 10 activities, all pointing to the same CityInfo.

Activity → One-to-One → WeatherInfo

Each activity has its own weather snapshot.

You don’t reuse weather records across activities (because they depend on time).

CityInfo → no direct link to WeatherInfo

Even though weather “belongs” to a city conceptually, in practice you only care about weather in the context of an activity.

So we model it through Activity.

Activity ↔ CityInfo: Many activities per city (ManyToOne).

Activity ↔ WeatherInfo: One weather snapshot per activity (OneToOne).

CityInfo ↔ WeatherInfo: No direct link (avoids confusion/duplication).