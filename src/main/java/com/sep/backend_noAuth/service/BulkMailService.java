package com.sep.backend_noAuth.service;

import com.sep.backend_noAuth.dto.InvoiceDto;
import com.sep.backend_noAuth.repository.Postage.BulkMailPostageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BulkMailService {

    @Autowired
    private BulkMailPostageRepository bulkMailPostageRepository;

    private Integer getDiscount(int mailCount){
        if(mailCount >= 1000){
            return bulkMailPostageRepository.findByMinCount(1000).getDiscount();
        }
        else if(mailCount >= 750){
            return bulkMailPostageRepository.findByMinCount(750).getDiscount();
        }else if(mailCount >= 500){
            return bulkMailPostageRepository.findByMinCount(500).getDiscount();
        }else {
            return bulkMailPostageRepository.findByMinCount(200).getDiscount();
        }
    }
    public InvoiceDto buildInvoiceDto(int mailCount){
        return  new InvoiceDto(mailCount, getDiscount(mailCount));
    }

}
