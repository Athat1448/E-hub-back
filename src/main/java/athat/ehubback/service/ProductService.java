package athat.ehubback.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import athat.ehubback.model.Product;
import athat.ehubback.repository.ProductRepository;


@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product getProduct(){
        Product product = productRepository.findAllProduct();
        return product;
    }

    public Product saveProduct(Product product){
        productRepository.save(product);
        return product;
    }
}
