package athat.ehubback.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "lineOrderItem")
public class LineOrderItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String barcode;
    private double discountedPrice;
    private String imageURL;
    private String name;
    private double price;
    private long productId;
    private int quantity;
    private String sku;
    private long variantId;
    private double weight;

    @ManyToOne
    @JoinColumn(name = "line_order_id")
    private LineOrder lineOrder;
}
