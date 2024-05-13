package br.com.patitas.app.service;

import br.com.patitas.app.model.Schedule;
import br.com.patitas.app.model.dto.ScheduleCreationDTO;

import java.util.List;

public interface ScheduleService {

    Schedule createSchedule(ScheduleCreationDTO scheduleCreationDTO);

    Schedule findById(Long id);

    List<Schedule> findAll();

    void deleteById(Long id);

}
