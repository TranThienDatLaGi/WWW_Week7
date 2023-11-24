package vn.edu.iuh.fit.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.backend.enums.EmployeeStatus;
import vn.edu.iuh.fit.backend.models.Employee;
import vn.edu.iuh.fit.backend.repositories.EmployeeRepository;

@Service
public class EmployeeService {
    private EmployeeRepository employeeRepository;
    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public void hiddenEmployee(long id){
        Employee employee = employeeRepository.findById(id).orElse(null);
        if (employee == null){
            return;
        }
        employee.setStatus(EmployeeStatus.TERMINATED);
        employeeRepository.save(employee);

    }

    public Page<Employee> findAll(int pageNo, int sizeNo, String sortBy, String sortDirection){
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(pageNo, sizeNo, sort);
        return employeeRepository.findAll(pageable);
    }

}
