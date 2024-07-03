package com.sep.backend_noAuth.repository.Postage;

import com.sep.backend_noAuth.entity.Postage.PostageNormalPosts;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NormalPostPostageRepository extends MongoRepository<PostageNormalPosts,String> {
}
