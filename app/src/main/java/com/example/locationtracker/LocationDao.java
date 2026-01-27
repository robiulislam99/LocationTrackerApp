package com.example.locationtracker;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface LocationDao {

    @Insert
    void insertLocation(LocationEntity location);

    @Query("SELECT * FROM locations ORDER BY timestamp DESC")
    List<LocationEntity> getAllLocations();

    @Query("SELECT * FROM locations ORDER BY timestamp DESC LIMIT 1")
    LocationEntity getLatestLocation();

    @Query("DELETE FROM locations")
    void deleteAllLocations();

    @Query("SELECT COUNT(*) FROM locations")
    int getLocationCount();
}