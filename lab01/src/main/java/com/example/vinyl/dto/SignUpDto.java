package com.example.vinyl.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;


@Validated
public class SignUpDto {
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

    @NotBlank(message = "Адрес эл. почты не должен быть пустым")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message="Адрес электронной почты должен соответствовать шаблону:\"example@mail.com\"")
    private String email;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
