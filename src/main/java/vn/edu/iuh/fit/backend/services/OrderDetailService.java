package vn.edu.iuh.fit.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.backend.models.OrderDetail;
import vn.edu.iuh.fit.backend.repositories.OrderDetailRepository;

@Service
public class OrderDetailService {
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    public OrderDetailService(OrderDetailRepository orderDetailRepository) {
        this.orderDetailRepository = orderDetailRepository;
    }
    public Page<OrderDetail> findAll(int pageNo, int sizeNo, String sortBy, String sortDirection){

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of( pageNo, sizeNo, sort);
        return orderDetailRepository.findAll(pageable);
    }
}
