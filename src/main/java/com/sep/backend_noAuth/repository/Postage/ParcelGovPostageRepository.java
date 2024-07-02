package com.sep.backend_noAuth.repository.Postage;

import com.sep.backend_noAuth.entity.Postage.PostageParcelGov;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ParcelGovPostageRepository extends MongoRepository<PostageParcelGov,String> {
}
