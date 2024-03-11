package athat.ehubback.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
//import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import athat.ehubback.model.Product;
import athat.ehubback.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@Component
@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {
    
    private ProductService productService ;

    @GetMapping("/getproducts")
    public List<Product> getProducts(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);
        List<Product> products = productService.getProducts(token);
        return products;
    }

    @GetMapping("/getproduct")
    public Product getProduct(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);
        Long id = Long.parseLong(request.getHeader("Id"));
        Product product = productService.getProduct(token, id);
        return product;
    }

    @PostMapping("/update")
    //@Scheduled(fixedDelay = 10000)
    private ResponseEntity<Object> getProductsFromApi(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);
        productService.updateProductsFromLine(token);
        return ResponseEntity.status(200).body("Products have update");
    }

    @PostMapping("/create")
    private ResponseEntity<Object> createProduct(HttpServletRequest request,@RequestBody Object product) throws ResponseStatusException{
        String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);
        productService.createProduct(token, product);
        return ResponseEntity.status(202).body("Product have create");
    }

    @DeleteMapping("/delete")
    private ResponseEntity<Object> deleteProduct(HttpServletRequest request) throws ResponseStatusException{
        String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);
        int id = Integer.parseInt(request.getHeader("Id"));
        productService.deleteProduct(token,id);
        return ResponseEntity.status(202).body("Product have delete");
    }
}
