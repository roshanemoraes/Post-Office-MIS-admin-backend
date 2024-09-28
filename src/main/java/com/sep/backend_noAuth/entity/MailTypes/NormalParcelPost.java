package com.sep.backend_noAuth.entity.MailTypes;

import com.sep.backend_noAuth.entity.Mail;
import lombok.Data;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "Mail")
@TypeAlias("normal-parcel")
public class NormalParcelPost extends Mail {
    private String height;
    private String breadth;
    private String length;

    public NormalParcelPost() {
        super();
        this.setMailType("normal-parcel");
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getBreadth() {
        return breadth;
    }

    public void setBreadth(String breadth) {
        this.breadth = breadth;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }
}
