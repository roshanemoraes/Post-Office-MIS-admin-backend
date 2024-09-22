package com.sep.backend_noAuth.repository.IntegrationTest;

import com.sep.backend_noAuth.entity.Mail;
import com.sep.backend_noAuth.entity.MailTypes.NormalPost;
import com.sep.backend_noAuth.repository.MailRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
public class MailRepositoryIntegrationTest {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:5.0.9").withReuse(true);

    @Autowired
    private MailRepository mailRepository;

    @BeforeAll
    public static void setupMongoContainer() {
        // Dynamically set the MongoDB URI for the Testcontainers instance
        System.setProperty("spring.data.mongodb.uri", mongoDBContainer.getReplicaSetUrl());
    }

    @BeforeEach
    public void setUp() {
        mailRepository.deleteAll();
    }

    @Test
    public void testSaveAndFindMail() {
        // Given
        NormalPost normalPost = new NormalPost();
        normalPost.setMailId("mail123");
        normalPost.setCustomerId("customer123");
        normalPost.setStatus("In Transit");
        normalPost.setDestinationAddress("123 Test St");
        normalPost.setRecipientName("John Doe");
        normalPost.setDatePosted("2023-09-22");

        // When
        mailRepository.save(normalPost);
        Mail savedMail = mailRepository.findByMailId("mail123");

        // Then
        assertNotNull(savedMail);
        assertEquals("mail123", savedMail.getMailId());
        assertEquals("In Transit", savedMail.getStatus());
        assertEquals("John Doe", savedMail.getRecipientName());
    }

    @Test
    public void testFindByCustomerId() {
        // Given
        NormalPost normalPost1 = new NormalPost();
        normalPost1.setMailId("mail001");
        normalPost1.setCustomerId("customerA");
        normalPost1.setStatus("Delivered");

        NormalPost normalPost2 = new NormalPost();
        normalPost2.setMailId("mail002");
        normalPost2.setCustomerId("customerA");
        normalPost2.setStatus("In Transit");

        mailRepository.save(normalPost1);
        mailRepository.save(normalPost2);

        // When
        List<Mail> customerMail = mailRepository.findByCustomerId("customerA");

        // Then
        assertEquals(2, customerMail.size());
    }

    @Test
    public void testDeleteMail() {
        // Given
        NormalPost normalPost = new NormalPost();
        normalPost.setMailId("mail124");
        normalPost.setCustomerId("customer124");
        normalPost.setStatus("Pending");

        mailRepository.save(normalPost);

        // When
        mailRepository.deleteById("mail124");

        // Then
        assertFalse(mailRepository.findById("mail124").isPresent());
    }
}