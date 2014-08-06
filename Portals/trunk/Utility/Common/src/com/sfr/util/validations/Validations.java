package com.sfr.util.validations;

public class Validations {
    public Validations() {
        super();
    }

    public boolean validateForEmptyValue(String value) {
        boolean flag = false;
        if (value != null && !value.trim().isEmpty() &&
            !value.trim().equals("")) {
            flag = true;
        } else {
            flag = false;
        }
        return flag;
    }

    public Boolean validateNumberofDigits(String input, Integer noOfDigits) {
        return input.matches("\\d{1," + noOfDigits + "}");
    }

    public boolean validateEmail(String email) {
        return (email.matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$"));
    }
}
