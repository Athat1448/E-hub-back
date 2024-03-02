package athat.ehubback.service;

import java.util.ArrayList;
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
import athat.ehubback.model.VariantOption;
import athat.ehubback.model.Variants;
import athat.ehubback.repository.ProductRepository;
import athat.ehubback.repository.StoreRepository;
import athat.ehubback.repository.VariantOptionRepository;
import athat.ehubback.repository.VariantsRepository;


@Service
public class ProductService {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private VariantsRepository variantsRepository;

    @Autowired
    private VariantOptionRepository variantOptionRepository;

    public List<Product> getProducts(){
        List<Product> products = productRepository.findAll();
        return products;
    }

    public List<Product> updateProductsFromLine(){
        List<Product> products = getProductsFromLine();
        List<Variants> variants = getVariants(products);

        List<Variants> variantsToSave = new ArrayList<>();
        List<VariantOption> variantOptionsToSave = new ArrayList<>();
        List<Product> productsToSave = new ArrayList<>();

        for (Product product : products) {
            if (!productRepository.existsByLineId(product.getLineId())) {
                productsToSave.add(product);
                variantsToSave.addAll(product.getVariants());
            }
        }
    
        for (Variants variant : variants) {
            if (!variantsRepository.existsByLineId(variant.getLineId())) {
                variantsToSave.add(variant);
                variantOptionsToSave.addAll(variant.getOptions());
            }
        }

        variantsRepository.saveAll(variantsToSave);
        variantOptionRepository.saveAll(variantOptionsToSave);

        productRepository.saveAll(productsToSave);
        return products;
    }

    private List<Product> getProductsFromLine() {
        String uri = "https://developers-oaplus.line.biz/myshop/v1/products";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-API-KEY", storeRepository.findByName("Atamarind").getLineApiKey());// API key
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

    private List<Variants> getVariants(List<Product> products) {
        List<Variants> variants = new ArrayList<>();
        for (Product product : products) {
            List<Variants> productVariants = product.getVariants();
            for (Variants variant : productVariants) {
                variant.setLineId(String.valueOf(variant.getId()));
            }
            variants.addAll(productVariants);
        }
        return variants;
    }
    
}
