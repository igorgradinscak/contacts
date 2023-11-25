package org.jugenfeier.contacts.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Validator {

    public static boolean validateCallNumber(final String callNumber) {
        log.trace("Validating call number: {}", callNumber);

        // Check if the call number is not blank
        if (callNumber == null || callNumber.isBlank()) {
            log.warn("Call number is blank or null");
            return false;
        }

        // Check if the call number starts with '+' followed by 3 digits
        if (!callNumber.matches("\\+\\d{3}")) {
            log.warn("Call number does not starts with '+' followed by 3 digits: {}", callNumber);
            return false;
        }

        return true;
    }

}
