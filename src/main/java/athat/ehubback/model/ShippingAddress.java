package athat.ehubback.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "shipping_address")
public class ShippingAddress {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String address;
    private String country;
    private String district;
    private String email;
    private String phoneNumber;
    private String postalCode;
    private String province;
    private String recipientName;
    private String subDistrict;

    @OneToOne
    @JoinColumn(name = "line_order_id")
    private LineOrder lineOrder;
}
