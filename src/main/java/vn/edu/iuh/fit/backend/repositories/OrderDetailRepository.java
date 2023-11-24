package vn.edu.iuh.fit.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.edu.iuh.fit.backend.models.OrderDetail;
import vn.edu.iuh.fit.backend.pks.OrderDetailPK;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailPK> {
    @Query("select od from OrderDetail od where od.order.order_id = :orderID")
    public List<OrderDetail> findAllByOrder_Order_id(@Param("orderID")long orderID);
}
