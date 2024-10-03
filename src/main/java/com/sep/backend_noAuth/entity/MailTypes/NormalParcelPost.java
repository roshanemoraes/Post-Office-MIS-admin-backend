package com.sep.backend_noAuth.entity.MailTypes;

import com.sep.backend_noAuth.entity.Mail;
import lombok.Data;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "Mail")
@TypeAlias("normal-parcel")
public class NormalParcelPost extends Mail {
    private String packageType;

    public NormalParcelPost() {
        super();
        this.setMailType("normal-parcel");
    }
    public String getPackageType() {
        return packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }


}
