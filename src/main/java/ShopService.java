import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ShopService {
    private ProductRepo productRepo = new ProductRepo();
    private OrderRepo orderRepo = new OrderMapRepo();

    public Order addOrder(List<String> productIds) throws ProductNotFoundException{
        List<Product> products = new ArrayList<>();
        for (String productId : productIds) {
                Optional<Product> productToOrder = productRepo.getProductById(productId);
                if (productToOrder.isEmpty()) {
                    throw new ProductNotFoundException("Product mit der Id: " + productId + " konnte nicht bestellt werden!");
                }
                products.add(productToOrder.get());
        }

        Instant instant = Instant.now();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime timestamp = instant.atZone(zoneId).toLocalDateTime();

        Order newOrder = new Order(UUID.randomUUID().toString(), products, OrderStatus.PROCESSING, timestamp);

        return orderRepo.addOrder(newOrder);
    }

    public List<Order> listAllOrdersByStatus(OrderStatus orderStatus) {

        return orderRepo.getOrders().stream()
                .filter(order -> order.orderStatus().equals(orderStatus))
                .toList();
    }

    public void updateOrder(String orderID, OrderStatus orderStatus){
        Order newOrder = orderRepo.addOrder(orderRepo.getOrderById(orderID).withOrderStatus(orderStatus));
        orderRepo.removeOrder(orderID);
        orderRepo.addOrder(newOrder);
    }

    public OrderRepo getOrderRepo() {
        return orderRepo;
    }
}
