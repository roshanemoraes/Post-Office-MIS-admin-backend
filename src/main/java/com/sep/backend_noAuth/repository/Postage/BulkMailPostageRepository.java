package com.sep.backend_noAuth.repository.Postage;

import com.sep.backend_noAuth.entity.Postage.PostageBulkMail;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BulkMailPostageRepository extends MongoRepository<PostageBulkMail,String> {
    PostageBulkMail findByMinCount(int mailCount);
}
