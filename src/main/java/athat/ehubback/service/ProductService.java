package athat.ehubback.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import athat.ehubback.model.Product;
import athat.ehubback.repository.ProductRepository;


@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getProducts(){
        List<Product> products = productRepository.findAll();
        return products;
    }

    public Product saveProduct(Product product){
        productRepository.save(product);
        return product;
    }
}
