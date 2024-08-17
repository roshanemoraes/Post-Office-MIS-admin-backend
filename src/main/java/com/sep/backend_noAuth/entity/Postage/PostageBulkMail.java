package com.sep.backend_noAuth.entity.Postage;

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
@Document(collection = "Bulk-Mail-Postage")
public class PostageBulkMail {
    @Id
    String Id;
    int minCount;
    int discount;
}
