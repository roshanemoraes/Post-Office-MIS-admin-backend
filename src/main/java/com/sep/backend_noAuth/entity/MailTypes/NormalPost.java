package com.sep.backend_noAuth.entity.MailTypes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
//@AllArgsConstructor
//@NoArgsConstructor
@Builder
@Document(collection="Mail")
public class NormalPost {

}
