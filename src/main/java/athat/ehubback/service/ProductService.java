package athat.ehubback.service;

import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import athat.ehubback.dto.LineProductDto;
import athat.ehubback.model.Product;
import athat.ehubback.model.Store;
import athat.ehubback.repository.ProductRepository;
import athat.ehubback.repository.StoreRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ProductService {

    private StoreRepository storeRepository;
    private StoreService storeService;
    private ProductRepository productRepository;
    private VariantsService variantsService;
    //private UserService userService;

    public List<Product> getProducts(String token){
        Store store = storeService.getStore(token);
        List<Product> products = getProductsFromLine(store);
        //List<Product> products = productRepository.findByStore(store);
        return products;
    }

    public Product getProduct(String token, Long id){
        Store store = storeService.getStore(token);
        List<Product> products = getProductsFromLine(store);
        //List<Product> products = productRepository.findByStore(store);
        Product product = products.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
        return product;
    }

    @Transactional
    public List<Product> updateProductsFromLine(String token){
        Store store = storeService.getStore(token);
        List<Product> products = getProductsFromLine(store);

        for (Product product : products) {
            if(!productRepository.existsByLineId(String.valueOf(product.getLineId()))) {
                variantsService.saveVariants(product);
                product.setStore(store);
                productRepository.save(product);

                variantsService.updateVariants(product);
            }
        }
        return products;
    }

    private List<Product> getProductsFromLine(Store store) {
        String uri = "https://developers-oaplus.line.biz/myshop/v1/products";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-API-KEY", storeRepository.findByName(store.getName()).getLineApiKey());// API key
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ResponseEntity<LineProductDto> response = restTemplate.exchange(uri, HttpMethod.GET, entity, LineProductDto.class);
        LineProductDto productDto = response.getBody();
        List<Product> products = productDto.getData();
        for (Product product : products) {
            product.setLineId(String.valueOf(product.getId()));
        }
        return products;
    }

    public void createProduct(String token , Object product){

        Store store = storeService.getStore(token);

        String uri = "https://developers-oaplus.line.biz/myshop/v1/products";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-API-KEY", storeRepository.findByName(store.getName()).getLineApiKey());// API key
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> entity = new HttpEntity<>(product, headers);
        System.out.println(entity);
        restTemplate.exchange(uri, HttpMethod.POST, entity, Object.class);
    }

    public void deleteProduct(String token, int productId) throws ResponseStatusException{
        Store store = storeService.getStore(token);
        HttpHeaders headers = new HttpHeaders();

        String uri = "https://developers-oaplus.line.biz/myshop/v1/products/";
        String newUri = String.valueOf(uri)+productId;
        RestTemplate restTemplate = new RestTemplate();

        headers.set("X-API-KEY", storeRepository.findByName(store.getName()).getLineApiKey());// API key
        headers.setContentType(MediaType.APPLICATION_JSON);

        restTemplate.exchange(newUri, HttpMethod.DELETE, null, String.class , headers) ;

        // if(userService.validateUser(token) == "OWNER"){
        //     
        // }
        // else{
        //     throw new ResponseStatusException(HttpStatus.LOCKED, "Only Owner can delete");
        // }
    }
}
