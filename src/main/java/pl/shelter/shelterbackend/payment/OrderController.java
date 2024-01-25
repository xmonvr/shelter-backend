package pl.shelter.shelterbackend.payment;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/order")
@AllArgsConstructor
public class OrderController {

    private OrderService orderService;

    @PostMapping("/create-order")
    public OrderResponse createOrder(@RequestBody OrderRequest orderRequest, HttpServletRequest httpServletRequest) {
        String ipAddress = "";
        if (httpServletRequest != null) {
            ipAddress = httpServletRequest.getHeader("X-FORWARDED-FOR");
            if (ipAddress == null || "".equals(ipAddress)) {
                ipAddress = httpServletRequest.getRemoteAddr();
            }
        }
        return orderService.createOrder(orderRequest, ipAddress);
    }

}
