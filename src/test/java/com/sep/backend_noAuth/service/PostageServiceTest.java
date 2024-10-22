package com.sep.backend_noAuth.service;

import com.sep.backend_noAuth.entity.Postage.PostageNormalPosts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@SpringBootTest
@ActiveProfiles("test")
class PostageServiceTest {
    @Mock
    private MongoTemplate mongoTemplate;
    @InjectMocks
    private PostageService postageService;
    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void findPostageByWeight() {
        double weight = -100.0;

        PostageNormalPosts mockPost = new PostageNormalPosts();
        mockPost.setMinWeight(200);
        mockPost.setMaxWeight(500);

        when(mongoTemplate.findOne(any(Query.class), eq(PostageNormalPosts.class)))
                .thenReturn(mockPost);

        PostageNormalPosts result = postageService.findNormalPostPostageByWeight(weight);

        assertNotNull(result);
        assertEquals(200.0, result.getMinWeight());
        assertEquals(500.0, result.getMaxWeight());

        verify(mongoTemplate, times(1)).findOne(any(Query.class), eq(PostageNormalPosts.class));

    }
}