package cart.controller;

import cart.authentication.Auth;
import cart.dto.CartDto;
import cart.dto.CartRequestDto;
import cart.dto.CartResponseDto;
import cart.service.CartService;
import cart.vo.Email;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/carts")
    public ResponseEntity<List<CartResponseDto>> cartsList(Auth auth) {
        return ResponseEntity.ok()
                .body(cartService.findAll(Email.from(auth.getEmail())));
    }

    @PostMapping("/carts")
    public ResponseEntity<Void> addProductToCart(Auth auth, @RequestBody @Valid CartRequestDto cartRequestDto) {
        CartDto cartDto = new CartDto(Email.from(auth.getEmail()), cartRequestDto.getProductId());
        cartService.save(cartDto);
        return ResponseEntity.created(URI.create("/cart"))
                .build();
    }

    @DeleteMapping("/carts/{id}")
    public ResponseEntity<Void> removeProductInCart(@PathVariable Long id, Auth auth) {
        cartService.removeById(id, Email.from(auth.getEmail()));
        return ResponseEntity.noContent()
                .build();
    }
    
}
