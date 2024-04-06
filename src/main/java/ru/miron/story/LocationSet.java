package ru.miron.story;

import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class LocationSet extends StoryObject {
    private Set<Place> placesInside;
    private Set<LocationSet> locationSets;

    public LocationSet(String name) {
        this(name, new HashSet<>(), new HashSet<>());
    }

    public LocationSet(String name, Set<Place> placesInside, Set<LocationSet> locationSets) {
        super(name);
        this.placesInside = new HashSet<>();
        this.locationSets = new HashSet<>();
        setPlacesInside(placesInside);
        setLocationSets(locationSets);
    }

    public void addLocationSet(LocationSet locationSet) {
        if (contains(locationSet)) {
            throw new IllegalStateException("location is already inside");
        }
        if (locationSet.equals(this)) {
            throw new IllegalStateException("Can not be inside self");
        }
        locationSets.add(locationSet);
    }

    public boolean removeLocationSet(LocationSet locationSet) {
        if (locationSet.equals(this)) {
            throw new IllegalStateException("Can not remove self");
        }
        for (var curLocationSet : locationSets) {
            if (curLocationSet.equals(locationSet)) {
                locationSets.remove(locationSet);
                return true;
            }
            var removedInside = curLocationSet.removeLocationSet(locationSet);
            if (removedInside) {
                return true;
            }
        }
        return false;
    }

    public void setLocationSets(Set<LocationSet> locationSets) {
        for (var locationSet : locationSets) {
            for (var innerPlace : placesInside) {
                if (locationSet.contains(innerPlace)) {
                    throw new IllegalStateException("Can not add location set with place which is inside this location set");
                }
            }
            if (locationSet.contains(this)) {
                throw new IllegalStateException("Can not be inside location set which is set as inner location set");
            }
            if (locationSet.equals(this)) {
                throw new IllegalStateException("Can not be inside self");
            }
        }
        this.locationSets = locationSets;
    }

    public void setPlacesInside(Set<Place> placesInside) {
        for (var placeInside : placesInside) {
            for (var curLocationSet : locationSets) {
                if (curLocationSet.contains(placeInside)) {
                    throw new IllegalStateException("Some of them inside inner location sets");
                }
            }
        }
        this.placesInside = placesInside;
    }
    public void addPlace(Place place) {
        if (contains(place)) {
            throw new IllegalStateException("Place is already inside");
        }
        placesInside.add(place);
    }

    public boolean removePlaceInside(Place place) {
        if (placesInside.contains(place)) {
            placesInside.remove(place);
            return true;
        }
        for (var locationSet : locationSets) {
            var removedInside = locationSet.removePlaceInside(place);
            if (removedInside) {
                return true;
            }
        }
        return false;
    }


    public boolean contains(LocationSet locationSet) {
        for (var curLocationSet : locationSets) {
            if (curLocationSet.equals(locationSet) || curLocationSet.contains(locationSet)) {
                return true;
            }
        }
        return false;
    }

    public boolean contains(Place place) {
        for (var curLocationSet : locationSets) {
            if (curLocationSet.contains(place)) {
                return true;
            }
        }
        for (var curPlace : placesInside) {
            if (curPlace.equals(place)) {
                return true;
            }
        }
        return false;
    }

    public Set<Human> getHumansInside() {
        Set<Human> humansInside = new HashSet<>();
        for (var place : placesInside) {
            humansInside.addAll(place.getHumanInside());
        }
        for (var location : locationSets) {
            humansInside.addAll(location.getHumansInside());
        }
        return humansInside;
    }
}

