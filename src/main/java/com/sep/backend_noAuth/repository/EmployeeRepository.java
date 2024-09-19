package com.sep.backend_noAuth.repository;

import com.sep.backend_noAuth.entity.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String> {
    Employee findById(Long id);
}
