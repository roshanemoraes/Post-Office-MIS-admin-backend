package com.sep.backend_noAuth.service;

import com.sep.backend_noAuth.dto.PostTypes.GovParcelPostDto;
import com.sep.backend_noAuth.dto.PostTypes.NormalCourierPostDto;
import com.sep.backend_noAuth.dto.PostTypes.NormalParcelPostDto;
import com.sep.backend_noAuth.dto.PostTypes.NormalPostDto;
import com.sep.backend_noAuth.entity.Address;
import com.sep.backend_noAuth.entity.Mail;
import com.sep.backend_noAuth.entity.MailTypes.GovernmentParcelPost;
import com.sep.backend_noAuth.entity.MailTypes.NormalCourierPost;
import com.sep.backend_noAuth.entity.MailTypes.NormalParcelPost;
import com.sep.backend_noAuth.entity.MailTypes.NormalPost;
import com.sep.backend_noAuth.repository.AddressRepository;
import com.sep.backend_noAuth.repository.MailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class MailRegistrationService {

    @Autowired
    private MailRepository mailRepository;
    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;
    @Autowired
    private AddressRepository addressRepository;

    public ResponseEntity<String> registerNormalPost(NormalPostDto dto){
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
        String date = currentDate.format(formatter);

        String[] addressArray = dto.getRecipientAddress().split("\\s*,\\s*");
        Optional<Address> recipientAddress = addressRepository.findByHouseNumberAndZoneAndCity(
                addressArray[0],
                addressArray[1],
                addressArray[2]);
        if(recipientAddress.isPresent()){
            Mail mail = new NormalPost();
            mail.setMailId(String.valueOf(sequenceGeneratorService.getSequenceNumber(Mail.SEQUENCE_NAME)));
            mail.setMailType("normal-post");
            mail.setCustomerId(dto.getSenderId());
            mail.setStatus("pending");
            mail.setDestinationAddress(dto.getRecipientAddress());
            mail.setRecipientName(dto.getRecipientName());
            mail.setRecipientId(dto.getRecipientId());
            mail.setDatePosted(date);
            mail.setDateDelivered(null);
            mail.setZone(addressArray[1]);
            mail.setCity(addressArray[2]);
            mail.setAddressId(recipientAddress.get().getAddressId());
            mail.setIn_area(addressArray[2].equals("Kochchikade"));
            mail.setPostage(dto.getPostage());

            ((NormalPost) mail).setSenderType(!dto.getSenderId().isEmpty() ? "registered" : "unregistered");

            mailRepository.save(mail);
            return ResponseEntity.ok("Mail Saved Successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Recipient Address Not Found");
    }

    public ResponseEntity<String> registerNormalParcelPost(NormalParcelPostDto dto){
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
        String date = currentDate.format(formatter);

        String[] addressArray = dto.getRecipientAddress().split("\\s*,\\s*");
        Optional<Address> recipientAddress = addressRepository.findByHouseNumberAndZoneAndCity(
                addressArray[0],
                addressArray[1],
                addressArray[2]);
        if(recipientAddress.isPresent()){
            Mail mail = new NormalParcelPost();
            mail.setMailId(String.valueOf(sequenceGeneratorService.getSequenceNumber(Mail.SEQUENCE_NAME)));
            mail.setMailType("normal-parcel");
            mail.setCustomerId(dto.getSenderId());
            mail.setStatus("pending");
            mail.setDestinationAddress(dto.getRecipientAddress());
            mail.setRecipientName(dto.getRecipientName());
            mail.setRecipientId(dto.getRecipientId());
            mail.setDatePosted(date);
            mail.setDateDelivered(null);
            mail.setZone(addressArray[1]);
            mail.setCity(addressArray[2]);
            mail.setAddressId(recipientAddress.get().getAddressId());
            mail.setIn_area(addressArray[2].equals("Kochchikade"));
            mail.setPostage(dto.getPostage());

            ((NormalParcelPost) mail).setPackageType(dto.getPackageType());

            mailRepository.save(mail);
            return ResponseEntity.ok("Mail Saved Successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Recipient Address Not Found");
    }
    public ResponseEntity<String> registerGovParcelPost(GovParcelPostDto dto){
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
        String date = currentDate.format(formatter);

        String[] addressArray = dto.getRecipientAddress().split("\\s*,\\s*");
        Optional<Address> recipientAddress = addressRepository.findByHouseNumberAndZoneAndCity(
                addressArray[0],
                addressArray[1],
                addressArray[2]);
        if(recipientAddress.isPresent()){
            Mail mail = new GovernmentParcelPost();
            mail.setMailId(String.valueOf(sequenceGeneratorService.getSequenceNumber(Mail.SEQUENCE_NAME)));
            mail.setMailType("gov-parcel");
            mail.setCustomerId(dto.getSenderId());
            mail.setStatus("pending");
            mail.setDestinationAddress(dto.getRecipientAddress());
            mail.setRecipientName(dto.getRecipientName());
            mail.setRecipientId(dto.getRecipientId());
            mail.setDatePosted(date);
            mail.setDateDelivered(null);
            mail.setZone(addressArray[1]);
            mail.setCity(addressArray[2]);
            mail.setAddressId(recipientAddress.get().getAddressId());
            mail.setIn_area(addressArray[2].equals("Kochchikade"));
            mail.setPostage(dto.getPostage());

            ((GovernmentParcelPost) mail).setMinistry(dto.getMinistry());

            mailRepository.save(mail);
            return ResponseEntity.ok("Mail Saved Successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Recipient Address Not Found");
    }

    public ResponseEntity<String> registerNormalCourierPost(NormalCourierPostDto dto){
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
        String date = currentDate.format(formatter);

        String[] addressArray = dto.getRecipientAddress().split("\\s*,\\s*");
        Optional<Address> recipientAddress = addressRepository.findByHouseNumberAndZoneAndCity(
                addressArray[0],
                addressArray[1],
                addressArray[2]);
        if(recipientAddress.isPresent()){
            Mail mail = new NormalCourierPost();
            mail.setMailId(String.valueOf(sequenceGeneratorService.getSequenceNumber(Mail.SEQUENCE_NAME)));
            mail.setMailType("normal-courier");
            mail.setCustomerId(dto.getSenderId());
            mail.setStatus("pending");
            mail.setDestinationAddress(dto.getRecipientAddress());
            mail.setRecipientName(dto.getRecipientName());
            mail.setRecipientId(dto.getRecipientId());
            mail.setDatePosted(date);
            mail.setDateDelivered(null);
            mail.setZone(addressArray[1]);
            mail.setCity(addressArray[2]);
            mail.setAddressId(recipientAddress.get().getAddressId());
            mail.setIn_area(addressArray[2].equals("Kochchikade"));
            mail.setPostage(dto.getPostage());
            ((NormalCourierPost) mail).setCourierService(dto.getCourierProvider());

            mailRepository.save(mail);
            return ResponseEntity.ok("Mail Saved Successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Recipient Address Not Found");
    }
}
