package com.sep.backend_noAuth.entity.MailTypes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sep.backend_noAuth.entity.Mail;
import lombok.Data;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "Mail")
@TypeAlias("normal-courier")
public class NormalCourierPost extends Mail {
    private String courierService;

    public NormalCourierPost(){
        super();
        this.setMailType("normal-courier");
    }
    public String getCourierService() {
        return courierService;
    }
    public void setCourierService(String courierService) {
        this.courierService = courierService;
    }

}
