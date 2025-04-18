package com.sky.validation;

import com.sky.annoaction.ValidIdCard;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class idNumberValidator implements ConstraintValidator<ValidIdCard, String> {
    // 初始化方法
    @Override
    public void initialize(ValidIdCard constraintAnnotation) {
        // 可以获取注解上的配置信息
    }

    // 实际验证逻辑
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return false;
        }

        // 验证逻辑实现
        return validateIdCard(value);
    }

    private boolean validateIdCard(String idCard) {
        // 基本格式验证
        String regex = "^[1-9]\\d{5}(18|19|20)\\d{2}(0[1-9]|1[0-2])"
                + "(0[1-9]|[12]\\d|3[01])\\d{3}[0-9Xx]$";
        if (!idCard.matches(regex)) {
            return false;
        }

        // 校验位验证（18位身份证）
        if (idCard.length() == 18) {
            char[] chars = idCard.toUpperCase().toCharArray();
            int[] weight = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
            char[] verifyCodes = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};

            int sum = 0;
            for (int i = 0; i < 17; i++) {
                sum += (chars[i] - '0') * weight[i];
            }
            int mod = sum % 11;
            return chars[17] == verifyCodes[mod];
        }

        return true; // 15位旧身份证不验证校验位
    }
}