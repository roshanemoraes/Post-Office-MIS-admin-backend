package com.sep.backend_noAuth.entity;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.sep.backend_noAuth.entity.MailTypes.GovernmentParcelPost;
import com.sep.backend_noAuth.entity.MailTypes.NormalCourierPost;
import com.sep.backend_noAuth.entity.MailTypes.NormalParcelPost;
import com.sep.backend_noAuth.entity.MailTypes.NormalPost;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Document(collection = "Mail")
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "mailType"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = NormalPost.class, name = "normal-post"),
        @JsonSubTypes.Type(value = NormalCourierPost.class, name = "normal-courier"),
        @JsonSubTypes.Type(value = GovernmentParcelPost.class, name = "gov-parcel"),
        @JsonSubTypes.Type(value = NormalParcelPost.class, name = "normal-parcel")
})
public abstract class Mail {

    @Transient
    public static final String SEQUENCE_NAME = "mail_sequence";

    @Id
    private String mailId;
    private String mailType;
    private String customerId;
    private String status;
    private String destinationAddress;
    private String recipientName;
    private String recipientId;
    private String datePosted;
    private String dateDelivered;
    private String zone;
    private String city;
    private String addressId;
    private Boolean in_area;
}
