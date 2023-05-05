package cart.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PasswordTest {

    @DisplayName("Password 로 형식에 맞지 않는 데이터를 입력 받았을 때 예외 발생 (7자, 특수문자 안들어감, 21자)")
    @ParameterizedTest
    @ValueSource(strings = {"sksk11!", "sksksksk1", "ssssssssssssssssss11!"})
    void createPasswordFail(String input) {
        assertThatThrownBy(() -> Password.from(input))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("올바르지 않은 비밀번호 형식입니다.");
    }

    @DisplayName("Password 로 형식에 맞는 데이터를 입력 받았을 때 성공적으로 객체 생성 (8자, 20자)")
    @ParameterizedTest
    @ValueSource(strings = {"sksk583!", "ssssssssssssssssa11!"})
    void createPasswordSuccess(String input) {
        Password password = Password.from(input);

        assertThat(password.getValue()).isEqualTo(input);
    }
}
