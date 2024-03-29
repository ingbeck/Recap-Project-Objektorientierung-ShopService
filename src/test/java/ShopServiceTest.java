import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShopServiceTest {

    @Test
    void addOrderTest() throws ProductNotFoundException {
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1");
        Instant instant = Instant.now();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime timestamp = instant.atZone(zoneId).toLocalDateTime();

        //WHEN
        Order actual = shopService.addOrder(productsIds);

        //THEN
        Order expected = new Order("-1", List.of(new Product("1", "Apfel")), OrderStatus.PROCESSING, timestamp);
        assertEquals(expected.products(), actual.products());
        assertNotNull(expected.id());
    }

    /*@Test
    void addOrderTest_whenInvalidProductId_expectNull() throws ProductNotFoundException{
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1", "2");

        //WHEN
        Order actual = shopService.addOrder(productsIds);

        //THEN
        assertNull(actual);
    }*/

    @Test
    void listAllOrdersByStatus_whenOrderStatusPROCESSING_thenReturnListOfAllOrdersWithStatusPROCESSING() throws ProductNotFoundException {
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1");
        shopService.addOrder(productsIds);
        shopService.addOrder(productsIds);
        shopService.addOrder(productsIds);

        //WHEN
        List<Order> actual = shopService.listAllOrdersByStatus(OrderStatus.PROCESSING);

        //THEN
        List<Order> expected = shopService.getOrderRepo().getOrders().stream()
                .filter(order -> order.orderStatus().equals(OrderStatus.PROCESSING))
                .toList();

        assertEquals(expected, actual);

    }

    @Test
    void addOrder_whenProductNotExist_thenThrowProductNotFoundException() {
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1", "2");

        //WHEN

        //THEN
        assertThrows(ProductNotFoundException.class, () -> shopService.addOrder(productsIds));

    }

    @Test
    void updateOrder() throws ProductNotFoundException{
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1");

        //WHEN
        Order actual = shopService.addOrder(productsIds);
        shopService.updateOrder(actual.id(), OrderStatus.IN_DELIVERY);

        //THEN
        assertEquals(OrderStatus.IN_DELIVERY, shopService.getOrderRepo().getOrderById(actual.id()).orderStatus());

    }


}
