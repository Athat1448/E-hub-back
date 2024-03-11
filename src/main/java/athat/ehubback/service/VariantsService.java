package athat.ehubback.service;

import java.util.List;

import athat.ehubback.model.Product;
import athat.ehubback.model.Variants;
import athat.ehubback.repository.VariantsRepository;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class VariantsService {

    VariantsRepository repository;
    VariantOptionService variantOptionService;

    public void saveVariants(Product product) {
        List<Variants> variants = product.getVariants();
        for (Variants variant : variants) {
            if(!repository.existsByLineId(String.valueOf(variant.getId()))) {
                variant.setLineId(String.valueOf(variant.getId()));
                repository.save(variant);
            }
        }
    }

    public void updateVariants(Product product) {
        List<Variants> variants = product.getVariants();
        for (Variants variant : variants) {
                variant.setProduct(product);
                repository.save(variant);
        }
    }
}
