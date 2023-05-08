package cart.service;

import cart.dao.CartDao;
import cart.dao.ProductDao;
import cart.dto.CartDto;
import cart.dto.CartResponseDto;
import cart.dto.ProductResponseDto;
import cart.entity.Cart;
import cart.vo.Email;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

    private final CartDao cartDao;
    private final ProductDao productDao;

    public CartService(CartDao cartDao, ProductDao productDao) {
        this.cartDao = cartDao;
        this.productDao = productDao;
    }

    public void save(CartDto cartDto) {
        cartDao.save(cartDto.toEntity());
    }

    public List<CartResponseDto> findAll(Email email) {
        return cartDao.selectAll(email)
                .stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    public CartResponseDto toResponseDto(Cart cart) {
        return new CartResponseDto(
                cart.getId(),
                cart.getEmail(),
                new ProductResponseDto(productDao.findById(cart.getProductId()))
        );
    }

    @Transactional
    public void removeById(Long id, Email email) {
        Cart cart = cartDao.findById(id);
        if (cart == null) {
            throw new IllegalStateException("해당 상품이 장바구니에 존재하지 않습니다.");
        }
        if (cart.canUserWithThisEmailBeDeleted(email)) {
            cartDao.deleteById(id);
            return;
        }
        throw new IllegalStateException("삭제할 권한이 없습니다.");
    }

}
