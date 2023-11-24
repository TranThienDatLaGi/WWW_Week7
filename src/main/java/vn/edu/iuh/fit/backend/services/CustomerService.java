package vn.edu.iuh.fit.backend.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.backend.models.Customer;
import vn.edu.iuh.fit.backend.repositories.CustomerRepository;
@Service
public class CustomerService {
    private CustomerRepository customerRepository;
    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Page<Customer> findAll(int pageNo, int sizeNo, String sortBy, String sortDirection){
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(pageNo, sizeNo, sort);
        return customerRepository.findAll(pageable);
    }
}
