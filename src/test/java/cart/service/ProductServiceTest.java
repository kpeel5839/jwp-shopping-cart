package cart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;

import cart.dao.ProductDao;
import cart.dto.ProductResponseDto;
import cart.entity.Product;
import java.util.List;

import cart.vo.Name;
import cart.vo.Price;
import cart.vo.Url;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ProductServiceTest {

    private ProductService productService;
    private ProductDao productDao;

    @BeforeEach
    void beforeEach() {
        productDao = Mockito.mock(ProductDao.class);
        productService = new ProductService(productDao);
    }

    @Test
    @DisplayName("상품 목록 조회")
    void findAll() {
        given(productDao.selectAll())
                .willReturn(List.of(
                        buildProduct(1, "재연", 10000, "재연씨"),
                        buildProduct(2, "미성", 10000, "미성씨")
                ));

        List<ProductResponseDto> products = productService.findAll();

        assertAll(
                () ->  assertThat(products.size()).isEqualTo(2),
                () ->  assertThat(products.get(0).getId()).isEqualTo(1),
                () ->  assertThat(products.get(1).getId()).isEqualTo(2)
        );
    }

    private Product buildProduct(int id, String name, int price, String imageUrl) {
        return new Product.Builder()
                .id(id)
                .name(Name.of(name))
                .price(Price.of(price))
                .imageUrl(Url.of(imageUrl))
                .build();
    }

}
