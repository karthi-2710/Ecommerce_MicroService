package com.example.app.product.Controller;

import com.example.app.product.Service.ProductService;
import com.example.app.product.dto.ProductRequest;
import com.example.app.product.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pro")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/post")
    public ResponseEntity<ProductResponse> addProduct(@RequestBody ProductRequest p){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productService.addProduct(p));
    }

    @GetMapping("/get")
    public ResponseEntity<List<ProductResponse>> getAllProducts(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id){
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<ProductResponse> updProduct(@RequestBody ProductRequest p, @PathVariable Long id){
//        return ResponseEntity.status(HttpStatus.ACCEPTED).body(productService.updProduct(p,id));
        return productService.updProduct(p,id).map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }

    @DeleteMapping("/del/{id}")
    public ResponseEntity<String> delProduct(@PathVariable Long id){
        return ResponseEntity.ok(productService.delProduct(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>> searchProducts(@RequestParam String keywrd){
        return ResponseEntity.ok(productService.searchProducts(keywrd));
    }

}
