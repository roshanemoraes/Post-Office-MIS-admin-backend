package com.sep.backend_noAuth.service.integrationTest;

import com.sep.backend_noAuth.dto.InvoiceDto;
import com.sep.backend_noAuth.entity.Postage.PostageBulkMail;
import com.sep.backend_noAuth.repository.Postage.BulkMailPostageRepository;
import com.sep.backend_noAuth.service.BulkMailService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
class BulkMailServiceIntegrationTest {

    @Autowired
    private BulkMailService bulkMailService;

    @Autowired
    private BulkMailPostageRepository bulkMailPostageRepository;

    @BeforeEach
    void setUp() {
        bulkMailPostageRepository.deleteAll();
        // Setup initial data for integration tests
        PostageBulkMail postageBulkMail1 = new PostageBulkMail();
        postageBulkMail1.setMinCount(1000);
        postageBulkMail1.setDiscount(13);
        bulkMailPostageRepository.save(postageBulkMail1);

        PostageBulkMail postageBulkMail2 = new PostageBulkMail();
        postageBulkMail2.setMinCount(750);
        postageBulkMail2.setDiscount(12);
        bulkMailPostageRepository.save(postageBulkMail2);

        PostageBulkMail postageBulkMail3 = new PostageBulkMail();
        postageBulkMail3.setMinCount(500);
        postageBulkMail3.setDiscount(11);
        bulkMailPostageRepository.save(postageBulkMail3);

        PostageBulkMail postageBulkMail4 = new PostageBulkMail();
        postageBulkMail4.setMinCount(200);
        postageBulkMail4.setDiscount(10);
        bulkMailPostageRepository.save(postageBulkMail4);
    }

    @AfterEach
    void tearDown() {
        bulkMailPostageRepository.deleteAll();

        PostageBulkMail postageBulkMail1 = new PostageBulkMail();
        postageBulkMail1.setMinCount(1000);
        postageBulkMail1.setDiscount(8);
        bulkMailPostageRepository.save(postageBulkMail1);

        PostageBulkMail postageBulkMail2 = new PostageBulkMail();
        postageBulkMail2.setMinCount(750);
        postageBulkMail2.setDiscount(7);
        bulkMailPostageRepository.save(postageBulkMail2);

        PostageBulkMail postageBulkMail3 = new PostageBulkMail();
        postageBulkMail3.setMinCount(500);
        postageBulkMail3.setDiscount(6);
        bulkMailPostageRepository.save(postageBulkMail3);

        PostageBulkMail postageBulkMail4 = new PostageBulkMail();
        postageBulkMail4.setMinCount(200);
        postageBulkMail4.setDiscount(5);
        bulkMailPostageRepository.save(postageBulkMail4);
    }

    @Test
    void getDiscount_ForMailCount1000AndAbove_ShouldReturnCorrectDiscount() {
        InvoiceDto invoiceDto = bulkMailService.buildInvoiceDto(1000);

        assertEquals(13, invoiceDto.getDiscount());
        assertEquals(1000, invoiceDto.getMailCount());
    }

    @Test
    void getDiscount_ForMailCount750To999_ShouldReturnCorrectDiscount() {
        InvoiceDto invoiceDto = bulkMailService.buildInvoiceDto(800);

        assertEquals(12, invoiceDto.getDiscount());
        assertEquals(800, invoiceDto.getMailCount());
    }

    @Test
    void getDiscount_ForMailCount500To749_ShouldReturnCorrectDiscount() {
        InvoiceDto invoiceDto = bulkMailService.buildInvoiceDto(600);

        assertEquals(11, invoiceDto.getDiscount());
        assertEquals(600, invoiceDto.getMailCount());
    }

    @Test
    void getDiscount_ForMailCountBelow500_ShouldReturnCorrectDiscount() {
        InvoiceDto invoiceDto = bulkMailService.buildInvoiceDto(300);

        assertEquals(10, invoiceDto.getDiscount());
        assertEquals(300, invoiceDto.getMailCount());
    }


}
