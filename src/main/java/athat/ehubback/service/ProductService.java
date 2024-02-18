package athat.ehubback.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import athat.ehubback.dto.LineProductDto;
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

    public List<Product> updateProductsFromLine(){
        List<Product> products = getProductsFromLine();
        productRepository.saveAll(products);
        return products;
    }

    private List<Product> getProductsFromLine() {
        String uri = "https://developers-oaplus.line.biz/myshop/v1/products";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-API-KEY", "");// API key
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ResponseEntity<LineProductDto> response = restTemplate.exchange(uri, HttpMethod.GET, entity, LineProductDto.class);
        LineProductDto productDto = response.getBody();
        List<Product> products = productDto.getData();
        return products;
    }
}
