package com.example.app.order.Repository;

import com.example.app.order.Model.CartItem;
//import com.employee.management.Model.Product;
//import com.employee.management.Model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<CartItem, Long> {

    CartItem findByStudentIdAndProductId(String studentId, String productId);

//   void deleteByStudentIdAndProductId(String studentId, String productId);

    List<CartItem> findByStudentId(String studentId);

    void deleteByStudentId(String studentId);
}
