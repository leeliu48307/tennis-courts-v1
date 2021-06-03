package com.tenniscourts.schedules;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
	
	static final String sQuery="";

    List<Schedule> findByTennisCourt_IdOrderByStartDateTime(Long id);
    
    @Query(value=sQuery, nativeQuery=true)
    List<Schedule> findBySchedule_By_StartDate_and_EndDate(LocalDateTime start_date, LocalDateTime end_date);
}