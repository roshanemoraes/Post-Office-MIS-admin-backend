package com.sep.backend_noAuth.entity;

import com.sep.backend_noAuth.dto.PostmanAssignmentPlanDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Document(collection = "Postman-Assignment-Log")
public class PostmanAssignmentLog {

    @Transient
    public static final String SEQUENCE_NAME = "postman_assignment_log_sequence";

    @Id
    private String logId;
    private String date;
    private List<PostmanAssignmentPlanDto> plan;
    private String type;

}
