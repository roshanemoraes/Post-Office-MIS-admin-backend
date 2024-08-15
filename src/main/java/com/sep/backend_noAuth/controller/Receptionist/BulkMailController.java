package com.sep.backend_noAuth.controller.Receptionist;

import com.sep.backend_noAuth.entity.BulkMailOrder;
import com.sep.backend_noAuth.entity.Mail;
import com.sep.backend_noAuth.service.ExcelProcessService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/receptionist/bulk-mail")
public class BulkMailController {

//    @PostMapping("/upload")
//    public ResponseEntity<List<Map<String,Object>>> processExcel(@RequestParam(name = "file") MultipartFile file) throws IOException {
//        List<Map<String,Object>> result =  ExcelProcessService.convertExcelRows(file);
//        return ResponseEntity.ok(result);
//    }
    @PostMapping("/upload")
    public ResponseEntity<Integer> processExcel(@RequestParam(name = "file") MultipartFile file) {
        try {
            List<Map<String, Object>> result = ExcelProcessService.convertExcelRows(file);
            if (result.size() >= 3) {
                return ResponseEntity.ok(result.size());
            } else {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(result.size());
            }
        } catch (IllegalArgumentException e) {
            // Return a 400 Bad Request with the exception message for validation errors
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(0);
        } catch (IOException e) {
            // Return a 500 Internal Server Error for IO exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(0);
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

        for(int i=0;i< result.size(); i++){
            Mail mail = new Mail();
            //TODO: This is under construction...
        }
        return ResponseEntity.ok("Order Success.");
    }
}
