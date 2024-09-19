package com.sep.backend_noAuth.entity.MailTypes;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sep.backend_noAuth.entity.Mail;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.TypeAlias;

@Data
@Document(collection = "Mail")
@TypeAlias("normal-post")
public class NormalPost extends Mail {
    private String testField;

    public NormalPost() {
        super();
        this.setMailType("normal-post");
    }
    public String getTestField() {
        return testField;
    }

    public void setTestField(String testField) {
        this.testField = testField;
    }


}
