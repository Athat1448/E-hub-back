package athat.ehubback.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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

    @RequestMapping("/products")
    @ResponseBody
    private List<Product> getProductsFromApi() {
        String uri = "https://developers-oaplus.line.biz/myshop/v1/products";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-API-KEY", "YTE3ZDM1NTUtZDM2OS00MDBmLTlkNjMtNTUxY2U1Y2I2YmUx");
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ParameterizedTypeReference<List<Product>> productListType = new ParameterizedTypeReference<List<Product>>(){};
        ResponseEntity<List<Product>> response = restTemplate.exchange(uri, HttpMethod.GET, entity, productListType);
        List<Product> products = response.getBody();
        System.out.println(products);
        productService.saveProduct(products);

        return products;
    }
}
