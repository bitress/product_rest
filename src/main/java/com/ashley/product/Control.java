package com.ashley.product;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class Control {

    private static final Map<String, Product> productRepo = new HashMap<>();

    static {
        Product list = new Product();
        list.setProductId("1");
        list.setProductName("Ice Cream");
        list.setProductPrice("16");
        productRepo.put(list.getProductId(), list);
    }

    //GET Request
    @GetMapping("/products")
    public ResponseEntity<Object> getProductList() {
        return new ResponseEntity<>(productRepo.values(), HttpStatus.OK);
    }

    // POST Request
    @PostMapping("/product/add")
    public ResponseEntity<Object> addProduct(@RequestBody Product product) {
        productRepo.put(product.getProductId(), product);
        return new ResponseEntity<>("Product successfully added", HttpStatus.CREATED);
    }

    // PUT Request for updating a Product
    @PutMapping("/product/{id}")
    public ResponseEntity<Object> updateProduct(
            @PathVariable("id") String id,
            @RequestParam(value = "name", required = false) String updatedProductName,
            @RequestParam(value = "price", required = false) String updateProductPrice
    ) {
        if (productRepo.containsKey(id)) {
            Product oldProductValue = productRepo.get(id);
            if (updatedProductName != null) {
                oldProductValue.setProductName(updatedProductName);
            }
            if (updateProductPrice != null) {
                oldProductValue.setProductPrice(updateProductPrice);
            }
            return new ResponseEntity<>("Product updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Product with ID " + id + " not found", HttpStatus.NOT_FOUND);
        }
    }

    // DELETE Request for deleting a Product
    @DeleteMapping("/product/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable("id") String id) {
        if (productRepo.containsKey(id)) {
            productRepo.remove(id);
            return new ResponseEntity<>("Product deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Product with ID " + id + " not found", HttpStatus.NOT_FOUND);
        }
    }
}
