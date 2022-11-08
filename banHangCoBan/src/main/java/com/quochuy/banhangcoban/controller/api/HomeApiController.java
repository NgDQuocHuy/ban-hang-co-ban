package com.quochuy.banhangcoban.controller.api;

import com.quochuy.banhangcoban.model.Product;
import com.quochuy.banhangcoban.service.product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class HomeApiController {
    @Autowired
    private IProductService productService;

    @GetMapping
    public ResponseEntity<?> findAllByDeletedIsFalse() {

        List<Product> products = productService.findAll();

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> findProductById(@PathVariable long productId) {
        Optional<Product> productOpt = productService.findById(productId);

        if (!productOpt.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Product product = productOpt.get();

        return new ResponseEntity<>(product, HttpStatus.OK);
    }
}
