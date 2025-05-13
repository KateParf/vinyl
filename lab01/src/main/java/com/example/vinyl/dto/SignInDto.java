package com.example.vinyl.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
public class SignInDto {

    @NotBlank(message = "Логин не может быть пустыми")
    @Pattern(regexp = "^(?![0-9])([A-Za-z0-9_]{3,15})$", message="Логин должен соответствовать следующим требованиям:\n" +
            "\n" +
            "Длиной от 3 до 15 символов.\n" +
            "Содержать только буквы латинского алфавита (верхнего и нижнего регистра), цифры или символ подчеркивания (_).\n" +
            "Не начинаться с цифры.\n" +
            "Не содержать пробелов.")
    private String login;
    @NotBlank(message = "Пароль не может быть пустыми")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&*!])[A-Za-z\\d@#$%^&*!]{8,20}$", message="Пароль должен соответствовать следующим критериям:\n" +
            "\n" +
            "Длиной не менее 8 и не более 20 символов.\n" +
            "Содержать как минимум одну букву верхнего регистра.\n" +
            "Содержать как минимум одну букву нижнего регистра.\n" +
            "Содержать как минимум одну цифру.\n" +
            "Содержать как минимум один специальный символ (например, @, #, $, %, ^, &, *).\n" +
            "Не содержать пробелов.")
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

