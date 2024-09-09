package com.sep.backend_noAuth.repository;

import com.sep.backend_noAuth.entity.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmployeeRepository extends MongoRepository<Employee,Long> {
}
