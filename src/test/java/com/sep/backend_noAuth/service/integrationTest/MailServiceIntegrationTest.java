package com.sep.backend_noAuth.service.integrationTest;

import com.sep.backend_noAuth.entity.Mail;
import com.sep.backend_noAuth.entity.MailTypes.NormalPost;
import com.sep.backend_noAuth.repository.MailRepository;
import com.sep.backend_noAuth.service.MailService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class MailServiceIntegrationTest {

    @Autowired
    private MailService mailService;

    @Autowired
    private MailRepository mailRepository;

    private Mail testMail;

    @BeforeEach
    void setUp() {
        // Setting up test data for each integration test
        testMail = new NormalPost();
        testMail.setCustomerId("123");
        testMail.setStatus("pending");
        testMail.setIn_area(true);
        testMail.setZone("Zone1");
        mailRepository.save(testMail);
    }

    @AfterEach
    void tearDown() {
        // Clean up the database after each test
        mailRepository.delete(testMail);
    }

    @Test
    void getAllMailsForStatus_ShouldReturnMailsWithCorrectStatusAndCustomer() {
        // When
        List<Mail> result = mailService.getAllMailsForStatus("123", "pending");

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("123", result.get(0).getCustomerId());
        assertEquals("pending", result.get(0).getStatus());
    }

    @Test
    void getAllPendingMails_ShouldReturnMailsWithPendingStatus() {
        // When
        List<Mail> result = mailService.getAllPendingMails();

        // Then
        assertNotNull(result);
        assertEquals(6, result.size());
        assertEquals("pending", result.get(0).getStatus());
    }

    @Test
    void getAllInAreaMails_ShouldReturnMailsInArea() {
        // When
        List<Mail> result = mailService.getAllInAreaMails();

        // Then
        assertNotNull(result);
        assertEquals(6, result.size());
        assertEquals(result.get(0).getIn_area().booleanValue(),true);
    }

    @Test
    void getAllOutAreaMails_ShouldReturnMailsOutOfArea() {
        // Given
        testMail.setIn_area(false);
        mailRepository.save(testMail);

        // When
        List<Mail> result = mailService.getAllOutAreaMails();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(result.get(0).getIn_area().booleanValue(),false);
    }

    @Test
    void getAllPendingInAreaMailsForZone_ShouldReturnPendingMailsInAreaForGivenZone() {
        // When
        List<Mail> result = mailService.getAllPendingInAreaMailsForZone("Zone1");

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("pending", result.get(0).getStatus());
        assertEquals("Zone1", result.get(0).getZone());
        assertEquals(result.get(0).getIn_area().booleanValue(),true);
    }
}
