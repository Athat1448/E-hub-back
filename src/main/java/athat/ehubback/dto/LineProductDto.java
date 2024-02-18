package athat.ehubback.dto;

import java.util.List;

import athat.ehubback.model.Product;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LineProductDto {

  private List<Product> data;

}
