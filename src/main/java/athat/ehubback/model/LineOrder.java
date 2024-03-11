package athat.ehubback.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "lineOrder")
public class LineOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String checkoutAt;
    private double discountAmount;
    private boolean isGift;
    private String lastUpdatedAt;
    
    private String orderNumber;
    private String orderStatus;
    private String paidAt;
    private String paymentMethod;
    private String paymentStatus;
    private String remarkBuyer;
    private String remarkRecipient;

    private double shipmentPrice;
    private String shipmentStatus;

    
    private double subtotalPrice;
    private double totalPrice;
    private double weight;

    @OneToMany(mappedBy = "lineOrder")
    private List<LineOrderItem> lineOrderItems;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;
    
    @OneToOne(mappedBy = "lineOrder")
    private ShipmentDetail shipmentDetail;

    @OneToOne(mappedBy = "lineOrder")
    private ShippingAddress shippingAddress;
}