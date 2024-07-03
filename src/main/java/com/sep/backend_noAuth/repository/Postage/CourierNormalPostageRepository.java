package com.sep.backend_noAuth.repository.Postage;

import com.sep.backend_noAuth.entity.Postage.PostageCourierNormal;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CourierNormalPostageRepository extends MongoRepository<PostageCourierNormal,String> {
}
