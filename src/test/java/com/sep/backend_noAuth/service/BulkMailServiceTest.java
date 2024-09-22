package com.sep.backend_noAuth.service;

import com.sep.backend_noAuth.dto.InvoiceDto;
import com.sep.backend_noAuth.entity.Postage.PostageBulkMail;
import com.sep.backend_noAuth.repository.Postage.BulkMailPostageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BulkMailServiceTest {

    @Mock
    private BulkMailPostageRepository bulkMailPostageRepository;

    @InjectMocks
    private BulkMailService bulkMailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getDiscount_ForMailCount1000AndAbove_ShouldReturnCorrectDiscount() {
        PostageBulkMail mockPostage = new PostageBulkMail();
        mockPostage.setMinCount(1000);
        mockPostage.setDiscount(20);

        when(bulkMailPostageRepository.findByMinCount(1000)).thenReturn(mockPostage);

        int discount = bulkMailService.buildInvoiceDto(1000).getDiscount();

        assertEquals(20, discount);
        verify(bulkMailPostageRepository, times(1)).findByMinCount(1000);
    }

    @Test
    void getDiscount_ForMailCount750To999_ShouldReturnCorrectDiscount() {
        PostageBulkMail mockPostage = new PostageBulkMail();
        mockPostage.setMinCount(750);
        mockPostage.setDiscount(15);

        when(bulkMailPostageRepository.findByMinCount(750)).thenReturn(mockPostage);

        int discount = bulkMailService.buildInvoiceDto(800).getDiscount();

        assertEquals(15, discount);
        verify(bulkMailPostageRepository, times(1)).findByMinCount(750);
    }

    @Test
    void getDiscount_ForMailCount500To749_ShouldReturnCorrectDiscount() {
        PostageBulkMail mockPostage = new PostageBulkMail();
        mockPostage.setMinCount(500);
        mockPostage.setDiscount(10);

        when(bulkMailPostageRepository.findByMinCount(500)).thenReturn(mockPostage);

        int discount = bulkMailService.buildInvoiceDto(600).getDiscount();

        assertEquals(10, discount);
        verify(bulkMailPostageRepository, times(1)).findByMinCount(500);
    }

    @Test
    void getDiscount_ForMailCountBelow500_ShouldReturnCorrectDiscount() {
        PostageBulkMail mockPostage = new PostageBulkMail();
        mockPostage.setMinCount(200);
        mockPostage.setDiscount(5);

        when(bulkMailPostageRepository.findByMinCount(200)).thenReturn(mockPostage);

        int discount = bulkMailService.buildInvoiceDto(300).getDiscount();

        assertEquals(5, discount);
        verify(bulkMailPostageRepository, times(1)).findByMinCount(200);
    }

    @Test
    void buildInvoiceDto_ShouldReturnCorrectInvoice() {
        PostageBulkMail mockPostage = new PostageBulkMail();
        mockPostage.setMinCount(1000);
        mockPostage.setDiscount(20);

        when(bulkMailPostageRepository.findByMinCount(1000)).thenReturn(mockPostage);

        InvoiceDto invoiceDto = bulkMailService.buildInvoiceDto(1000);

        assertEquals(1000, invoiceDto.getMailCount());
        assertEquals(20, invoiceDto.getDiscount());
    }
}
