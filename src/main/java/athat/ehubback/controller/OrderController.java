package athat.ehubback.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import athat.ehubback.model.LineOrder;
import athat.ehubback.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/order")
@AllArgsConstructor
public class OrderController {

    private OrderService orderService;

    @GetMapping("/getorders")
    public List<LineOrder> getOrders(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);
        List<LineOrder> orders = orderService.getOrders(token);
        return orders;
    }

    @GetMapping("/getorder")
    public LineOrder getOrder(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);
        Long id = Long.parseLong(request.getHeader("Id"));
        LineOrder order = orderService.getOrder(token, id);
        return order;
    }

    @GetMapping("/getordersbystatus")
    public List<LineOrder> getOrdersByStatus(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);
        String status = request.getHeader("Status");
        List<LineOrder> orders = orderService.getLineOrdersByStatus(token, status);
        return orders;
    }
}
