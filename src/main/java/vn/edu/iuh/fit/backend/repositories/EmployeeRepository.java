package vn.edu.iuh.fit.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.iuh.fit.backend.enums.EmployeeStatus;
import vn.edu.iuh.fit.backend.models.Employee;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    public List<Employee> findAllByStatusNot(EmployeeStatus employeeStatus);
}