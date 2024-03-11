package athat.ehubback.dto;

import java.util.List;

import athat.ehubback.model.LineOrder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LineOrderDto {
    
    private List<LineOrder> data;
}
