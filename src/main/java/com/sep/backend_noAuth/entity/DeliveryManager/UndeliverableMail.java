package com.sep.backend_noAuth.entity.DeliveryManager;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Document(collection = "undeliverable-mails")
public class UndeliverableMail {
    @Id
    String mailId;
    String status;
    String type;
    String zone;
    String city;
    String reason;
    String deliveredBy;
    String deliverDate;
}
