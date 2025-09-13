package daos;

import entities.WeatherInfo;

import java.util.List;

public class WeatherDAO implements IDAO<WeatherInfo,Integer> {
    @Override
    public WeatherInfo create(WeatherInfo weatherInfo) {
        return null;
    }

    @Override
    public boolean update(WeatherInfo weatherInfo) {
        return false;
    }

    @Override
    public boolean delete(WeatherInfo weatherInfo) {
        return false;
    }

    @Override
    public WeatherInfo find(Integer id) {
        return null;
    }

    @Override
    public List<WeatherInfo> getAll() {
        return List.of();
    }
}
