package athat.ehubback.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import athat.ehubback.model.Product;
import athat.ehubback.service.ProductService;


@RestController
public class ProductController {
    @Autowired
    private ProductService productService ;

    @GetMapping("/api/products")
    public List<Product> getProducts() {
        List<Product> products = productService.getProducts();
        return products;
    }
}
