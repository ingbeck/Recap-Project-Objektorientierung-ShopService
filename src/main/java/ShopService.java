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

        Order newOrder = new Order(UUID.randomUUID().toString(), products, OrderStatus.PROCESSING);

        return orderRepo.addOrder(newOrder);
    }

    public List<Order> listAllOrdersByStatus(OrderStatus orderStatus) {

        return orderRepo.getOrders().stream()
                .filter(order -> order.orderStatus().equals(orderStatus))
                .toList();
    }

    public OrderRepo getOrderRepo() {
        return orderRepo;
    }
}
