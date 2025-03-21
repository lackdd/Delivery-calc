package com.lackdd.deliverycalc.repositories;

import com.lackdd.deliverycalc.entities.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, Long> {

    @Query("select w from Weather w where w.timestamp > :oneHourAgo")
    List<Weather> findByLatestDate(@Param("oneHourAgo") LocalDateTime oneHourAgo);
}
