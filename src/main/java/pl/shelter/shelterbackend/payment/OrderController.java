package pl.shelter.shelterbackend.payment;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/order")
@AllArgsConstructor
@Slf4j
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
        log.info("controller OrderRequest --> " + orderRequest);
        return orderService.createOrder(orderRequest, ipAddress);
    }

}
