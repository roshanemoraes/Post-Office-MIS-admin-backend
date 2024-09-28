package com.sep.backend_noAuth.controller.Receptionist;

import com.sep.backend_noAuth.dto.InvoiceDto;
import com.sep.backend_noAuth.entity.BulkMailOrder;
import com.sep.backend_noAuth.entity.Mail;
import com.sep.backend_noAuth.entity.MailTypes.NormalPost;
import com.sep.backend_noAuth.repository.Postage.BulkMailPostageRepository;
import com.sep.backend_noAuth.service.BulkMailService;
import com.sep.backend_noAuth.service.ExcelProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/receptionist/bulk-mail")
public class BulkMailController {

    @Autowired
    BulkMailPostageRepository bulkMailPostageRepository;

    @Autowired
    BulkMailService bulkMailService;


//    @PostMapping("/upload")
//    public ResponseEntity<List<Map<String,Object>>> processExcel(@RequestParam(name = "file") MultipartFile file) throws IOException {
//        List<Map<String,Object>> result =  ExcelProcessService.convertExcelRows(file);
//        return ResponseEntity.ok(result);
//    }
    @PostMapping("/upload")
    public ResponseEntity<InvoiceDto> processExcel(@RequestParam(name = "file") MultipartFile file) {
        try {
            List<Map<String, Object>> result = ExcelProcessService.convertExcelRows(file);
            int mailCount = result.size();

            if (mailCount >= 200) {
                return ResponseEntity.ok(bulkMailService.buildInvoiceDto(mailCount));
            } else {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @PostMapping("/get-postage")
    public ResponseEntity<Integer> getPostage(@RequestParam(required = true) int mailCount){
            if(mailCount >= 1000){
                return ResponseEntity.ok(bulkMailPostageRepository.findByMinCount(1000).getDiscount());
            }
            else if(mailCount >= 750){
                return ResponseEntity.ok(bulkMailPostageRepository.findByMinCount(750).getDiscount());
            }else if(mailCount >= 500){
                return ResponseEntity.ok(bulkMailPostageRepository.findByMinCount(500).getDiscount());
            }else if(mailCount >= 200){
                return ResponseEntity.ok(bulkMailPostageRepository.findByMinCount(200).getDiscount());
            }else{
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(mailCount);
            }

    }

    @PostMapping("/create-order")
    public ResponseEntity<String> createOrder(@RequestParam(name = "file") MultipartFile file) throws IOException {
        List<Map<String,Object>> result =  ExcelProcessService.convertExcelRows(file);
        BulkMailOrder order = new BulkMailOrder();
        order.setOrderId("2");

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
        String date = currentDate.format(formatter);

        order.setOrderDate(date);
//        order.setPostalCost(cost);    //TODO: have to set them. Should receive from the frontend.
//        order.setTotalWeight(weight);
//        order.setCustomerId(customerId);
        order.setTotalWeight("0");
        order.setPostalCost("0");
        order.setCustomerId(4);

        order.setItemCount(result.size());
        order.setStatus("Pending-Sort");

        for(Map<String,Object> row : result){
//            String mailType = (String)row.get("mailType");
//            MailFlyweight mailFlyweight = mailFlyweightFactory.getFlyweight(mailType);
//            String uniqueData = (String) row.get("uniqueData"); // Replace with actual unique data from row
//            mailFlyweight.processMail(uniqueData);
        }
        return ResponseEntity.ok("Order Success.");
    }
}
