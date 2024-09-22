package com.sep.backend_noAuth.repository;

import com.sep.backend_noAuth.entity.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@ActiveProfiles("test")
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void testSaveUser() {
        // Given
        Employee employee = new Employee("1",
                "John Doe",
                "john.doe@example.com",
                "abc",
                "ROLE_TEST",
                "John Doe",
                "20424245",
                "0237582932",
                "2024-07-21");

        // When
        Employee savedEmployee = employeeRepository.save(employee);

        // Then
        assertNotNull(savedEmployee);
        assertEquals("John Doe", savedEmployee.getName());
    }

    @Test
    void testFindUserById() {
        // Given
        Employee user = new Employee("2",
                "Jane Doe",
                "jane.doe@example.com",
                "abc",
                "ROLE_TEST",
                "Jane Doe",
                "20424245",
                "0237582932",
                "2024-07-21");
        employeeRepository.save(user);

        // When
        Optional<Employee> foundUser = employeeRepository.findById("2");

        // Then
        assertTrue(foundUser.isPresent());
        assertEquals("Jane Doe", foundUser.get().getName());
    }

    @Test
    void testUpdateUser() {
        // Given
        Employee user = new Employee("3",
                "Old Name",
                "old.email@example.com",
                "abc",
                "ROLE_TEST",
                "Old Name",
                "20424245",
                "0237582932",
                "2024-07-21");
        employeeRepository.save(user);

        // When
        user.setName("New Name");
        user.setEmail("new.email@example.com");
        Employee updatedUser = employeeRepository.save(user);

        // Then
        assertNotNull(updatedUser);
        assertEquals("New Name", updatedUser.getName());
        assertEquals("new.email@example.com", updatedUser.getEmail());
    }

    @Test
    void testDeleteUser() {
        // Given
        Employee user = new Employee("4",
                "John Smith",
                "john.smith@example.com",
                "abc",
                "ROLE_TEST",
                "Old Name",
                "20424245",
                "0237582932",
                "2024-07-21");
        employeeRepository.save(user);

        // When
        employeeRepository.deleteById("4");

        // Then
        Optional<Employee> deletedUser = employeeRepository.findById("4");
        assertFalse(deletedUser.isPresent());
    }
}
