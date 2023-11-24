package vn.edu.iuh.fit;

import net.datafaker.Faker;
import net.datafaker.providers.base.Device;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import vn.edu.iuh.fit.backend.enums.EmployeeStatus;
import vn.edu.iuh.fit.backend.enums.ProductStatus;
import vn.edu.iuh.fit.backend.models.*;
import vn.edu.iuh.fit.backend.repositories.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

@SpringBootApplication
public class WwwWeek7Application {
    public static void main(String[] args) {
        SpringApplication.run(WwwWeek7Application.class, args);
    }

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductImageRepository productImageRepository;
//    @Bean
    CommandLineRunner createSampleData(){
        return args -> {
            Faker faker =new Faker();
            Random random  = new Random();
            double randomDoubleInRange = 1000 + (90000 - 1000) * random.nextDouble();

            Device devices = faker.device();
            for (int i = 0; i < 400; i++) {
                Product product =new Product(
                        devices.modelName(),
                        devices.modelName(),
                        "piece",
                        devices.manufacturer(),
                        ProductStatus.ACTIVE
                );
                Product product1= productRepository.save(product);
//                Optional<Product> optionalProduct= productRepository.findById(product1.getProduct_id());

//                ProductPrice productPrice = new ProductPrice();
//                productPrice.setProduct(optionalProduct.get());
//                productPrice.setPrice_date_time(LocalDateTime.now());
//                productPrice.setPrice(1000 + (90000 - 1000) * random.nextDouble());
//                productPrice.setNote(devices.modelName());
//
//                System.out.println(productPrice);
//                productPriceService.saveOrUpdateProduct(productPrice);
                ProductImage productImage=new ProductImage("https://fptshop.com.vn/Uploads/Originals/2023/3/24/638152764193595966_asus-vivobook-flip-tn3402y-bac-dd.jpg",null);
                productImage.setProduct(product);
                productImageRepository.save(productImage);
            }

            for (int i = 0; i < 50; i++) {
                Customer customer= new Customer(
                        faker.name().fullName(),
                        faker.internet().emailAddress(),
                        faker.phoneNumber().cellPhone(),
                        faker.address().fullAddress());
                customerRepository.save(customer);
            }

            for (int i = 0; i < 10; i++) {
                Employee employee= new Employee(faker.name().fullName(),
                        LocalDate.now(),
                        i+1+"@gmail.com",
                        faker.phoneNumber().cellPhone(),
                        faker.address().fullAddress(),
                        EmployeeStatus.ACTIVE
                );

                employeeRepository.save(employee);
            }

            for (int i = 0; i < 700; i++) {

                Order order= new Order(LocalDateTime.now(),
                        employeeRepository.findById((long) (random.nextInt(10)+1)).get(),
                        customerRepository.findById((long) (random.nextInt(50)+1)).get()
                );

                orderRepository.save(order);
            }

//            for (int i = 0; i < 1000; i++) {
//                long order_Id =(long) (random.nextInt(700)+1);
//                long product_id =(long) (random.nextInt(200)+1);
//                OrderDetail orderDetail= new OrderDetail(
//                        randomDoubleInRange,
//                        randomDoubleInRange,
//                        devices.modelName(),
//                        orderRepository.findById(order_Id).get(),
//                        productRepository.findById(product_id).get()
//                );
//                OrderDetailPK orderDetailPK= new OrderDetailPK();
//                orderDetailPK.setOrder( orderRepository.findById(order_Id).get());
//                orderDetailPK.setProduct( productRepository.findById(product_id).get());
//                orderDetailRepository.save(orderDetail);
//            }

        };
    }

}