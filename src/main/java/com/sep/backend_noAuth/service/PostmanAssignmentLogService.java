package com.sep.backend_noAuth.service;

import com.sep.backend_noAuth.entity.PostmanAssignmentLog;
import com.sep.backend_noAuth.repository.PostmanAssignmentLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class PostmanAssignmentLogService {

    @Autowired
    private PostmanAssignmentLogRepository postmanAssignmentLogRepository;

    public String getTodayAssignmentStatus(){
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
        String date = currentDate.format(formatter);

        Optional<PostmanAssignmentLog> log = postmanAssignmentLogRepository.findByDate(date);
        if(log.isPresent())
            return log.get().getType();
        return "none";
    }
}
