package athat.ehubback.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import athat.ehubback.dto.LineOrderDto;
import athat.ehubback.model.LineOrder;
import athat.ehubback.model.Store;
import athat.ehubback.repository.LineOrderRepository;
import athat.ehubback.repository.StoreRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class OrderService {
    
    StoreRepository storeRepository;
    LineOrderRepository orderRepository;
    StoreService storeService;

    public List<LineOrder> getOrdersFromLine(Store store){
        String uri = "https://developers-oaplus.line.biz/myshop/v1/orders";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-API-KEY", storeRepository.findByName(store.getName()).getLineApiKey());
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<LineOrderDto> response = restTemplate.exchange(uri, HttpMethod.GET, entity, LineOrderDto.class);
        LineOrderDto lineOrderDto = response.getBody();
        List<LineOrder> Orders = lineOrderDto.getData();
        return Orders;
    }

    public List<LineOrder> getOrders(String token){
        Store store = storeService.getStore(token);
        List<LineOrder> orders = getOrdersFromLine(store);
        return orders;
    }

    public LineOrder getOrder(String token, Long id){
        Store store = storeService.getStore(token);
        List<LineOrder> orders = getOrdersFromLine(store);
        LineOrder order = orders.stream().filter(o -> o.getId().equals(id)).findFirst().orElse(null);
        return order;
    }

    public List<LineOrder> getLineOrdersByStatus(String token, String status){
        Store store = storeService.getStore(token);
        List<LineOrder> orders = getOrdersFromLine(store);
        List<LineOrder> showOrders = new ArrayList<>();
        for (LineOrder order : orders) {
            if(order.getOrderStatus().equals(status)){
                showOrders.add(order);
            }
        }
        return showOrders;
    }
}
