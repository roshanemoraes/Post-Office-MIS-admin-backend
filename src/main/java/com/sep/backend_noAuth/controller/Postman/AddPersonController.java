package com.sep.backend_noAuth.controller.Postman;

import com.sep.backend_noAuth.repository.PostmanAssignmentRepository;
import com.sep.backend_noAuth.entity.PostmanAssignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("api/postman/add-person")
public class AddPersonController {

    @Autowired
    private PostmanAssignmentRepository postmanAssignmentRepository;

    @GetMapping("/get-zone")
    public ResponseEntity<String> getZoneByPostmanId(@RequestParam("postmanId") Integer postmanId) {
        PostmanAssignment assignment = postmanAssignmentRepository.findById(postmanId)
                .orElse(null);

        if (assignment != null) {
            return ResponseEntity.ok(assignment.getZone());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
