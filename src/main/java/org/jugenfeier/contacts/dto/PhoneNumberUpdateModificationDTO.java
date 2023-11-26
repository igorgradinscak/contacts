package org.jugenfeier.contacts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NonNull;
import org.jugenfeier.contacts.util.Validator;

@Data
public class PhoneNumberUpdateModificationDTO {
    @Schema(description = "id", example = "1")
    private Integer id;

    @Schema(description = "Call Number", example = "+385")
    private String callNumber;

    @Schema(description = "Telephone number", example = "91-123-1234")
    private String telephoneNumber;

    public PhoneNumberUpdateModificationDTO(
            final Integer id,
            @NonNull final String callNumber,
            @NonNull final String telephoneNumber
    ) {
        this.id = id;
        this.callNumber = callNumber;
        this.telephoneNumber = telephoneNumber;
    }

    /**
     * Checks whether the phone number associated with this entity is valid.
     *
     * <p>
     * This method validates both the call number and telephone number associated with the phone number entity
     * using the {@link Validator#validateCallNumber(String)} and {@link Validator#validateTelephoneNumber(String)} methods.
     * It returns true if both numbers are considered valid; otherwise, it returns false.
     * </p>
     *
     * @return {@code true} if both the call number and telephone number are valid; {@code false} otherwise.
     */
    @Schema(hidden = true)
    public boolean isPhoneNumberValid() {

        boolean isCallNumberValid = Validator.validateCallNumber(callNumber);
        boolean isTelephoneNumberValid = Validator.validateTelephoneNumber(telephoneNumber);

        return isCallNumberValid && isTelephoneNumberValid;
    }
}
