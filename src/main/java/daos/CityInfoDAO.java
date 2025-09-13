package daos;

import entities.CityInfo;

import java.util.List;

public class CityInfoDAO implements IDAO<CityInfo,Integer> {

    @Override
    public CityInfo create(CityInfo cityInfo) {
        return null;
    }

    @Override
    public boolean update(CityInfo cityInfo) {
        return false;
    }

    @Override
    public boolean delete(CityInfo cityInfo) {
        return false;
    }

    @Override
    public CityInfo find(Integer id) {
        return null;
    }

    @Override
    public List<CityInfo> getAll() {
        return List.of();
    }
}
