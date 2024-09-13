package com.sep.backend_noAuth.entity.MailTypes;


import com.sep.backend_noAuth.entity.Mail;
import lombok.Data;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "Mail")
@TypeAlias("gov-parcel")
public class GovernmentParcelPost extends Mail {
    public String getMinistry() {
        return ministry;
    }

    public void setMinistry(String ministry) {
        this.ministry = ministry;
    }

    private String ministry;

    public GovernmentParcelPost() {
        super();
        this.setMailType("gov-parcel");
    }
}
