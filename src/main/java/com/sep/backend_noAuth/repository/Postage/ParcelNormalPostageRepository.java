package com.sep.backend_noAuth.repository.Postage;

import com.sep.backend_noAuth.entity.Postage.PostageParcelNormal;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ParcelNormalPostageRepository extends MongoRepository<PostageParcelNormal,String> {
}
