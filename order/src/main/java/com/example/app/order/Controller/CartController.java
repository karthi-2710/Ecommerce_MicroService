package com.example.app.order.Controller;

import com.example.app.order.Model.CartItem;
import com.example.app.order.Service.CartService;
import com.example.app.order.Service.OrderService;
import com.example.app.order.dto.CartRequest;
import com.example.app.order.dto.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping("/post")
    public ResponseEntity<String>addCart(@RequestHeader("X-User-ID")String studentId,
                                          @RequestBody CartRequest request){
        if(!cartService.addCart(studentId,request)){
            return ResponseEntity.badRequest().body("Product is Out of Stock or User not found");
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/del/{productId}")
    public ResponseEntity<Void> removeCart(@RequestHeader("X-User-ID")String studentId,@PathVariable String productId ){
        boolean deleted =cartService.deleteItemFromCart(studentId,productId);
        if(!deleted){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }


    @GetMapping("/get")
    public ResponseEntity<List<CartItem>> getAllCartItems(@RequestHeader("X-User-ID")String studentId){
        return ResponseEntity.ok(cartService.getAllCartItems(studentId));
    }


}
