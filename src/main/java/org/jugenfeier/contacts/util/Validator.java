package org.jugenfeier.contacts.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Validator {

    /**
     * Validates a call number to ensure it meets the required format.
     *
     * <p>
     * This static method validates a call number by performing the following checks:
     * <ul>
     *   <li>Ensures that the call number is not blank or null.</li>
     *   <li>Verifies that the call number starts with '+' followed by 3 digits.</li>
     * </ul>
     * </p>
     *
     * @param callNumber The call number to be validated.
     * @return {@code true} if the call number is valid; {@code false} otherwise.
     */
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

    /**
     * Validates a telephone number to ensure it meets the required format.
     *
     * <p>
     * This static method validates a telephone number by performing the following checks:
     * <ul>
     *   <li>Ensures that the telephone number is not blank or null.</li>
     *   <li>Verifies that the telephone number has the format "xx-xxx-xxxx" where "x" is a digit.</li>
     * </ul>
     * </p>
     *
     * @param telephoneNumber The telephone number to be validated.
     * @return {@code true} if the telephone number is valid; {@code false} otherwise.
     */
    public static boolean validateTelephoneNumber(final String telephoneNumber) {
        log.trace("Validating telephone number: {}", telephoneNumber);

        // Check if the telephone number is not blank
        if (telephoneNumber == null || telephoneNumber.isBlank()) {
            log.warn("Telephone number is blank or null");
            return false;
        }

        // Check if the telephone number has the format "xx-xxx-xxxx"
        if (!telephoneNumber.matches("\\d{2}-\\d{3}-\\d{4}")) {
            log.warn("Telephone number does not have the format 'xx-xxx-xxxx': {}", telephoneNumber);
            return false;
        }

        return true;
    }

}
