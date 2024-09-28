package com.sep.backend_noAuth.service;

import com.sep.backend_noAuth.entity.Mail;
import com.sep.backend_noAuth.entity.MailTypes.NormalPost;
import com.sep.backend_noAuth.repository.MailRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MailServiceTest {

    @Mock
    private MailRepository mailRepository;

    @Mock
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private MailService mailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllMailsForStatus() {
        String customerId = "123";
        String status = "pending";
        List<Mail> mockMailList = new ArrayList<>();

        Mail mail = new NormalPost();
        mail.setCustomerId(customerId);
        mail.setStatus(status);
        mockMailList.add(mail);

        when(mongoTemplate.find(any(Query.class), eq(Mail.class))).thenReturn(mockMailList);

        // When
        List<Mail> result = mailService.getAllMailsForStatus(customerId, status);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(customerId, result.get(0).getCustomerId());
        assertEquals(status, result.get(0).getStatus());
        verify(mongoTemplate, times(1)).find(any(Query.class), eq(Mail.class));
    }

    @Test
    void getAllPendingMails() {
        List<Mail> mockMailList = new ArrayList<>();
        Mail mail = new NormalPost();
        mail.setStatus("pending");
        mockMailList.add(mail);

        when(mongoTemplate.find(any(Query.class), eq(Mail.class))).thenReturn(mockMailList);

        // When
        List<Mail> result = mailService.getAllPendingMails();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("pending", result.get(0).getStatus());
        verify(mongoTemplate, times(1)).find(any(Query.class), eq(Mail.class));
    }

    @Test
    void getAllInAreaMails() {
        List<Mail> mockMailList = new ArrayList<>();
        Mail mail = new NormalPost();
        mail.setStatus("pending");
        mail.setIn_area(true);
        mockMailList.add(mail);

        when(mongoTemplate.find(any(Query.class), eq(Mail.class))).thenReturn(mockMailList);

        // When
        List<Mail> result = mailService.getAllInAreaMails();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("pending", result.get(0).getStatus());
        verify(mongoTemplate, times(1)).find(any(Query.class), eq(Mail.class));
    }

    @Test
    void getAllOutAreaMails() {
        List<Mail> mockMailList = new ArrayList<>();
        Mail mail = new NormalPost();
        mail.setStatus("pending");
        mail.setIn_area(false);
        mockMailList.add(mail);

        when(mongoTemplate.find(any(Query.class), eq(Mail.class))).thenReturn(mockMailList);

        // When
        List<Mail> result = mailService.getAllOutAreaMails();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("pending", result.get(0).getStatus());
        verify(mongoTemplate, times(1)).find(any(Query.class), eq(Mail.class));
    }

    @Test
    void getAllPendingInAreaMailsForZone() {
        String zone = "Zone1";
        List<Mail> mockMailList = new ArrayList<>();
        Mail mail = new NormalPost();
        mail.setStatus("pending");
        mail.setIn_area(true);
        mail.setZone(zone);
        mockMailList.add(mail);

        when(mongoTemplate.find(any(Query.class), eq(Mail.class))).thenReturn(mockMailList);

        // When
        List<Mail> result = mailService.getAllPendingInAreaMailsForZone(zone);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("pending", result.get(0).getStatus());
        assertEquals(zone, result.get(0).getZone());
        verify(mongoTemplate, times(1)).find(any(Query.class), eq(Mail.class));
    }
}
