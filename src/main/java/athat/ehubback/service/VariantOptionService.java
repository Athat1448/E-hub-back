package athat.ehubback.service;


import java.util.List;

import org.springframework.stereotype.Service;

import athat.ehubback.model.Product;
import athat.ehubback.model.VariantOptions;
import athat.ehubback.repository.VariantOptionRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class VariantOptionService {

    VariantOptionRepository repository;

    public void saveVariantOptions(Product product) {
        List<VariantOptions> variantOptions = product.getVariantOptions();
        for(VariantOptions variantOption : variantOptions) {
            repository.save(variantOption);
        }
    }

    public void updateVariantOptions(Product product) {
        List<VariantOptions> variantOptions = product.getVariantOptions();
        for(VariantOptions variantOption : variantOptions) {
            VariantOptions variantOptionToUpdate = repository.findById(variantOption.getId()).orElse(null);
            if (variantOptionToUpdate != null) {
                variantOptionToUpdate.setProduct(product);
                repository.save(variantOptionToUpdate);
            }
        }
    }
}
