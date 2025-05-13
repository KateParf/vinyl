package com.example.vinyl.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ChangePasswordDto {
    private String oldPassword;
    
    @NotBlank(message = "Пароль не может быть пустыми")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&*!])[A-Za-z\\d@#$%^&*!]{8,20}$", message="Пароль должен соответствовать следующим критериям:\n" +
            "\n" +
            "Длиной не менее 8 и не более 20 символов.\n" +
            "Содержать как минимум одну букву верхнего регистра.\n" +
            "Содержать как минимум одну букву нижнего регистра.\n" +
            "Содержать как минимум одну цифру.\n" +
            "Содержать как минимум один специальный символ (например, @, #, $, %, ^, &, *).\n" +
            "Не содержать пробелов.")
    private String newPassword;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

}
