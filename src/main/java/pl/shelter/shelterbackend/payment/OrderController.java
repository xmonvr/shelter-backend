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
//        https://mkyong.com/java/how-to-get-client-ip-address-in-java/
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

//    @GetMapping("/get-ip")
//    private static String getClientIp(HttpServletRequest request) {
//
//    }

//    @GetMapping("/get-ip2")
//    public String getIpAddress(/*@RequestBody HttpServletRequest httpServletRequest*/) {
////        String ipAddress = httpServletRequest.getRemoteAddr();
//        String ipAddress = httpServletRequest.getHeader("X-FORWARDED-FOR");
//        log.info("adres ip --> " + ipAddress);
//        return ipAddress;
//
//        //dokumentacja https://mkyong.com/java/how-to-get-client-ip-address-in-java/
//    }
//
//    @GetMapping("/getMyIpAddress")
//    public String getMyIpAddress(HttpServletRequest request) {
//        String ipAddress = request.getRemoteAddr();
//        return "Adres IP klienta: " + ipAddress;
//    }
}
