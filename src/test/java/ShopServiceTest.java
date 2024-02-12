import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShopServiceTest {

    @Test
    void addOrderTest() {
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1");

        //WHEN
        Order actual = shopService.addOrder(productsIds);

        //THEN
        Order expected = new Order("-1", List.of(new Product("1", "Apfel")), OrderStatus.PROCESSING);
        assertEquals(expected.products(), actual.products());
        assertNotNull(expected.id());
    }

    @Test
    void addOrderTest_whenInvalidProductId_expectNull() {
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1", "2");

        //WHEN
        Order actual = shopService.addOrder(productsIds);

        //THEN
        assertNull(actual);
    }

    @Test
    void listAllOrdersByStatus_whenOrderStatusPROCESSING_thenReturnListOfAllOrdersWithStatusPROCESSING(){
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1");
        shopService.addOrder(productsIds);
        shopService.addOrder(productsIds);
        shopService.addOrder(productsIds);

        //WHEN
        List<Order> actual = shopService.listAllOrdersByStatus(OrderStatus.PROCESSING);

        //THEN
        List<Order> expected = List.of(new Order("-1", List.of(new Product("1", "Apfel")), OrderStatus.PROCESSING),
                                     new Order("-2", List.of(new Product("1", "Apfel")), OrderStatus.PROCESSING),
                                     new Order("-3", List.of(new Product("1", "Apfel")), OrderStatus.PROCESSING));

        assertEquals(expected, actual);

    }
}
