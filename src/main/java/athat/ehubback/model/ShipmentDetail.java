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
@Table(name = "shipment_detail")
public class ShipmentDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String description;
    private boolean isCod;
    private String name;
    private long shipmentCompanyId;
    private String shipmentCompanyNameEn;
    private String shipmentCompanyNameTh;
    private String trackingNumber;
    private String trackingUrl;
    private String type;

    @OneToOne
    @JoinColumn(name = "line_order_id")
    private LineOrder lineOrder;
}
