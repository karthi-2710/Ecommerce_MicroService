package com.example.app.product.Service;

import com.example.app.product.Model.Product;
import com.example.app.product.Repository.ProductRepository;
import com.example.app.product.dto.ProductRequest;
import com.example.app.product.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public ProductResponse addProduct(ProductRequest p) {
        Product product = new Product();
        updateProductFromRequest(product, p);
        Product savedProduct = productRepository.save(product);
        return mapToProductResponse(savedProduct);
    }

    private ProductResponse mapToProductResponse(Product savedProduct) {
        ProductResponse response = new ProductResponse();
        response.setId(savedProduct.getId());
        response.setName(savedProduct.getName());
        response.setDescription(savedProduct.getDescription());
        response.setCategory(savedProduct.getCategory());
        response.setPrice(savedProduct.getPrice());
        response.setStockQuantity(savedProduct.getStockQuantity());
        response.setImageURL(savedProduct.getImageURL());
        response.setActive(savedProduct.getActive());
        return response;
    }

    private void updateProductFromRequest(Product product, ProductRequest p) {
        product.setName(p.getName());
        product.setDescription(p.getDescription());
        product.setCategory(p.getCategory());
        product.setPrice(p.getPrice());
        product.setStockQuantity(p.getStockQuantity());
        product.setImageURL(p.getImageURL());
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream().map(this::mapToProductResponse).collect(Collectors.toList());
    }

    public ProductResponse getProductById(Long id){
        return productRepository.findById(id)
                .map(this::mapToProductResponse)
                .orElseThrow(()->new RuntimeException("Product not found with id:"+id));
    }

    public Optional<ProductResponse> updProduct(ProductRequest p, Long id){
        return productRepository.findById(id).map(existingProduct->{
            updateProductFromRequest(existingProduct,p);
            Product savedProduct=productRepository.save(existingProduct);
            return mapToProductResponse(savedProduct);
        });
    }

    public String delProduct(Long id) {
        Product product =productRepository.findById(id).orElseThrow(()->new RuntimeException("Product not found with id:"+id));
        product.setActive(false);
        productRepository.save(product);
        return "Product is Deactivated";
    }

    public List<ProductResponse> searchProducts(String keywrd) {
        return productRepository.searchProducts(keywrd).stream().map(this::mapToProductResponse).toList();
    }
}