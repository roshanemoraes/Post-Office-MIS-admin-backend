package com.sep.backend_noAuth.repository;

import com.sep.backend_noAuth.entity.Mail;
import com.sep.backend_noAuth.entity.MailTypes.NormalPost;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@ActiveProfiles("test")
public class MailRepositoryTest {

    @Autowired
    private MailRepository mailRepository;

    @BeforeEach
    public void setUp() {
        // Clean up the repository before each test
        mailRepository.deleteAll();
    }

    @Test
    public void testSaveAndFindNormalPost() {
        // Given
        NormalPost normalPost = new NormalPost();
        normalPost.setMailId("mail123");
        normalPost.setCustomerId("customer1");
        normalPost.setStatus("In Transit");
        normalPost.setDestinationAddress("123 Test Street");
        normalPost.setRecipientName("John Doe");
        normalPost.setRecipientId("recip123");
        normalPost.setDatePosted("2023-09-20");
        normalPost.setZone("Zone 1");
        normalPost.setCity("Test City");
        normalPost.setAddressId("addr123");
        normalPost.setIn_area(true);
        normalPost.setTestField("Test Field");

        // When
        mailRepository.save(normalPost);
        Mail savedMail = mailRepository.findByMailId("mail123");

        // Then
        assertNotNull(savedMail);
        assertEquals("normal-post", savedMail.getMailType());
        assertTrue(savedMail instanceof NormalPost);
        assertEquals("customer1", savedMail.getCustomerId());
        assertEquals("Test Field", ((NormalPost) savedMail).getTestField());
    }

    @Test
    public void testFindByCustomerId() {
        // Given
        NormalPost normalPost = new NormalPost();
        normalPost.setMailId("mail124");
        normalPost.setCustomerId("customer2");
        normalPost.setStatus("Delivered");
        normalPost.setDestinationAddress("456 Sample Street");
        normalPost.setRecipientName("Jane Smith");
        normalPost.setRecipientId("recip124");
        normalPost.setDatePosted("2023-09-21");
        normalPost.setZone("Zone 2");
        normalPost.setCity("Sample City");
        normalPost.setAddressId("addr124");
        normalPost.setIn_area(false);
        normalPost.setTestField("Test Field 2");

        mailRepository.save(normalPost);

        // When
        List<Mail> mails = mailRepository.findByCustomerId("customer2");

        // Then
        assertFalse(mails.isEmpty());
        assertEquals(1, mails.size());
        assertEquals("customer2", mails.get(0).getCustomerId());
        assertEquals("normal-post", mails.get(0).getMailType());
    }

    @Test
    public void testFindByDatePostedAndMailType() {
        // Given
        NormalPost normalPost = new NormalPost();
        normalPost.setMailId("mail125");
        normalPost.setCustomerId("customer3");
        normalPost.setStatus("Pending");
        normalPost.setDestinationAddress("789 Another Street");
        normalPost.setRecipientName("Jack Black");
        normalPost.setRecipientId("recip125");
        normalPost.setDatePosted("2023-09-22");
        normalPost.setZone("Zone 3");
        normalPost.setCity("Another City");
        normalPost.setAddressId("addr125");
        normalPost.setIn_area(true);
        normalPost.setTestField("Test Field 3");

        mailRepository.save(normalPost);

        // When
        List<Mail> mails = mailRepository.findByDatePostedAndMailType("2023-09-22", "normal-post");

        // Then
        assertFalse(mails.isEmpty());
        assertEquals(1, mails.size());
        assertEquals("mail125", mails.get(0).getMailId());
        assertEquals("normal-post", mails.get(0).getMailType());
    }

    @Test
    public void testDeleteMail() {
        // Given
        NormalPost normalPost = new NormalPost();
        normalPost.setMailId("mail126");
        normalPost.setCustomerId("customer4");
        normalPost.setStatus("Shipped");

        mailRepository.save(normalPost);

        // When
        mailRepository.deleteById("mail126");

        // Then
        assertNull(mailRepository.findByMailId("mail126"));
    }
}
