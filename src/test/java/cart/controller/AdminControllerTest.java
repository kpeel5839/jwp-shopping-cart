package cart.controller;

import static org.hamcrest.Matchers.containsString;

import cart.dto.ProductRequestDto;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AdminControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void beforeEach() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("관리자 페이지 호출 확인")
    void admin() {
        RestAssured.given().log().all()
                .accept(MediaType.TEXT_HTML_VALUE)
                .when().get("/admin")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body(containsString("<title>관리자 페이지</title>"));
    }

    @Test
    @DisplayName("상품 추가 확인")
    void addProduct() {
        ProductRequestDto productRequestDto = new ProductRequestDto("밋엉", 10000, "미성씨");

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productRequestDto)
                .accept(MediaType.TEXT_HTML_VALUE)
                .when().post("/admin/products")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body(containsString("<title>관리자 페이지</title>"));
    }

    @Test
    @DisplayName("상품 수정 확인")
    void modifyProduct() {
        ProductRequestDto productRequestDto = new ProductRequestDto("샐러드", 10000, "밋밋엉");

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productRequestDto)
                .accept(MediaType.TEXT_HTML_VALUE)
                .when().put("/admin/products/2")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body(containsString("<title>관리자 페이지</title>"));
    }

    @Test
    @DisplayName("상품 삭제 확인")
    void removeProduct() {
        RestAssured.given().log().all()
                .accept(MediaType.TEXT_HTML_VALUE)
                .when().delete("/admin/products/2")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body(containsString("<title>관리자 페이지</title>"));
    }

    @AfterEach
    void afterEach() {
        jdbcTemplate.update("TRUNCATE TABLE product");
    }

}
