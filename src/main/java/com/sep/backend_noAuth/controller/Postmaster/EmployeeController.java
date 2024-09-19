package com.sep.backend_noAuth.controller.Postmaster;

import com.sep.backend_noAuth.dto.AddEmployeeDto;
import com.sep.backend_noAuth.entity.Employee;
import com.sep.backend_noAuth.repository.EmployeeRepository;
import com.sep.backend_noAuth.service.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/postmaster/employee")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @GetMapping("live-map")
    public List<Map<String, Object>> getEmployees() {
        List<Map<String, Object>> employees = Arrays.asList(
                Map.of(
                        "name", "Ruwan",
                        "deliveredCount", 3,
                        "pendingCount", 5,
                        "lat", 7.215839,
                        "lng", 79.87138
                ),
                Map.of(
                        "name", "Sujith",
                        "deliveredCount", 2,
                        "pendingCount", 7,
                        "lat", 7.194806,
                        "lng", 79.864943
                ),
                Map.of(
                        "name", "Kumara",
                        "deliveredCount", 8,
                        "pendingCount", 1,
                        "lat", 7.198468,
                        "lng", 79.840138
                ),
                Map.of(
                        "name", "Kalpa",
                        "deliveredCount", 5,
                        "pendingCount", 5,
                        "lat", 7.224183,
                        "lng", 79.855931
                )
        );
        return employees;
    }

    @GetMapping("list-employee")
    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }
    @PostMapping("add-employee")
    public ResponseEntity<String> addNewEmployee(@RequestBody AddEmployeeDto employee){
        Employee newEmployee = new Employee();
        newEmployee.setId(String.valueOf(sequenceGeneratorService.getSequenceNumber(Employee.SEQUENCE_NAME)));
        newEmployee.setName(employee.getEmployeeUserName());
        newEmployee.setFullName(employee.getEmployeeFullName());
        newEmployee.setEmail(employee.getEmployeeEmail());
        newEmployee.setNic(employee.getEmployeeNIC());
        newEmployee.setContactNumber(employee.getEmployeeContactNumber());
        newEmployee.setDateJoined(employee.getEmployeeDateJoined());
        newEmployee.setPassword(employee.getAccountPassword());
        newEmployee.setRoles(employee.getRole());
        employeeRepository.save(newEmployee);
        return ResponseEntity.ok("New Employee Added.");
    }
}
