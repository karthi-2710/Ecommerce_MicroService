package com.example.app.order.Service;

import org.springframework.transaction.annotation.Transactional;
import com.example.app.order.Model.CartItem;
//import com.example.app.order.Model.Product;
//import com.example.app.order.Model.Student;
import com.example.app.order.Repository.CartRepository;
//import com.example.app.order.Repository.ProductRepository;
//import com.example.app.order.Repository.StudentRepository;
import com.example.app.order.dto.CartRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {
//    private final ProductRepository productRepository;
//    private final StudentRepository studentRepository;
    private final CartRepository cartRepository;

    public Boolean addCart(String studentId, CartRequest request) {
//        Optional<String> productopt = productRepository.findById(request.getProductId());
//        if (productopt.isEmpty()) {
//            return false;
//        }
//        Product product = productopt.get();
//        if (product.getStockQuantity() < request.getStockQuantity()) {
//            return false;
//        }
//        Optional<Student> studentopt = studentRepository.findById(Long.valueOf(studentId));
//        if (studentopt.isEmpty()) {
//            return false;
//        }
//        Student student = studentopt.get();
        CartItem existingCartItem = cartRepository.findByStudentIdAndProductId(studentId, String.valueOf(request.getProductId())
        );
        if (existingCartItem != null) {
            //Update cart item
            existingCartItem.setQuantity(existingCartItem.getQuantity() + request.getStockQuantity());
//            existingCartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(existingCartItem.getQuantity())));
            existingCartItem.setPrice(BigDecimal.valueOf(1000.00));
            cartRepository.save(existingCartItem);
        } else {
            //Create new cart item
            CartItem cartItem = new CartItem();
            cartItem.setStudentId(studentId);
            cartItem.setProductId(String.valueOf(request.getProductId()));
            cartItem.setQuantity(request.getStockQuantity());
//            cartItem.setPrice(product.getPrice());
            cartItem.setPrice(BigDecimal.valueOf(1000.00));
            cartRepository.save(cartItem);
        }
        return true;
    }

    @Transactional
    public boolean deleteItemFromCart(String studentId, String productId) {
//        Optional<Product> productOpt = productRepository.findById(productId);
//        Optional<Student> studentOpt = studentRepository.findById(Long.valueOf(studentId));
//        CartItem item = cartRepository.findByStudentAndProduct(studentOpt.get(), productOpt.get());
        CartItem item = cartRepository.findByStudentIdAndProductId(studentId, productId);
        if (item != null) {
            cartRepository.delete(item);
            return true;
        }
        return false;
    }

    public List<CartItem> getAllCartItems(String studentId) {
//        return studentRepository.findById(Long.valueOf(studentId))
//                .map(cartRepository::findByStudent).orElseGet(List::of);
        return cartRepository.findByStudentId(studentId);
    }


    @Transactional
    public void clearCart(String studentId) {
//        studentRepository.findById(Long.valueOf(studentId)).ifPresent(student -> {
//            cartRepository.deleteByStudent(student);
//        });
        cartRepository.deleteByStudentId(studentId);
    }
}
