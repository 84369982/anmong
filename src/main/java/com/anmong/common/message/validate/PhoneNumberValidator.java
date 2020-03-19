package com.anmong.common.message.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {
    private boolean allowEmpty;

    public PhoneNumberValidator() {
    }

    public void initialize(PhoneNumber constraintAnnotation) {
        this.allowEmpty = constraintAnnotation.allowEmpty();
    }

    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (this.allowEmpty) {
            if (value == null || "".equals(value.trim())) {
                return true;
            }
        } else if (value == null || "".equals(value.trim())) {
            return false;
        }

        return value.matches("^1[3-9]\\d{9}$");
    }
}
