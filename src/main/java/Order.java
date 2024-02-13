import lombok.With;

import java.time.LocalDateTime;
import java.util.List;

public record Order(
        String id,
        List<Product> products,

        @With OrderStatus orderStatus,

        LocalDateTime timestamp
) {
}
