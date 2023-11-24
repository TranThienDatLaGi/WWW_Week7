package vn.edu.iuh.fit.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.edu.iuh.fit.backend.models.Order;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT o.orderDate as orderDate, count(o.orderDate) as totalOrderNumber from Order o group by o.orderDate")
    public List<OrderCount> statictisOrderByDay();
    @Query("select sum(od.quantity * od.price) from Order o join o.orderDetails od" +
            " where o.orderDate between :beginDate and :endDate group by o.order_id")
    public double getTotalPriceByBeginDateAndEndDate(@Param("beginDate") LocalDateTime beginDate
            , @Param("endDate") LocalDateTime endDate);
    @Query("select sum(od.quantity * od.price) from Order o join o.orderDetails od where o.employee.id = :empID" +
            " group by o.employee.id")
    public double getTotalPriceByEmployeeID(@Param("empID") long employeeID);
}