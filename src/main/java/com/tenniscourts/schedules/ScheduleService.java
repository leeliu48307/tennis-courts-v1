package com.tenniscourts.schedules;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.tenniscourts.TennisCourtDTO;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    private final ScheduleMapper scheduleMapper;

    public ScheduleDTO addSchedule(Long tennisCourtId, CreateScheduleRequestDTO createScheduleRequestDTO) {
    
    	ScheduleDTO  scheduleDTO=new ScheduleDTO();
    	
    	LocalDateTime  local_start_time=createScheduleRequestDTO.getStartDateTime(); 
    	LocalDateTime  local_end_time=local_start_time.plusHours(2);
    	
    	scheduleDTO.setTennisCourtId(createScheduleRequestDTO.getTennisCourtId());
    	scheduleDTO.setStartDateTime(local_start_time);
    	scheduleDTO.setEndDateTime(local_end_time);
    	
    	List <ScheduleDTO>   listScheduleDTO=new ArrayList <ScheduleDTO>();
    	listScheduleDTO.add(scheduleDTO);

    	TennisCourtDTO   tennisCourtDTO=new TennisCourtDTO();
    	tennisCourtDTO.setTennisCourtSchedules(listScheduleDTO);
    	tennisCourtDTO.setName("Tennis Court Name");
    	
    	scheduleDTO.setTennisCourt(tennisCourtDTO);
    
    	return scheduleMapper.map(scheduleRepository.saveAndFlush(scheduleMapper.map(scheduleDTO)));
    }

    public List<ScheduleDTO> findSchedulesByDates(LocalDateTime startDate, LocalDateTime endDate) {
    	return scheduleMapper.map(scheduleRepository.findBySchedule_By_StartDate_and_EndDate(startDate, endDate));
    };

    public ScheduleDTO findSchedule(Long scheduleId) {
        return scheduleRepository.findById(scheduleId).map(scheduleMapper::map).orElseThrow(() -> {
            throw new EntityNotFoundException("Schedule is not found.");
        });
    }

    public List<ScheduleDTO> findSchedulesByTennisCourtId(Long tennisCourtId) {
        return scheduleMapper.map(scheduleRepository.findByTennisCourt_IdOrderByStartDateTime(tennisCourtId));
    }
}
