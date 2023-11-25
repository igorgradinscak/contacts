package org.jugenfeier.contacts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NonNull;
import org.jugenfeier.contacts.util.Validator;

@Data
public class PhoneNumberModificationDTO {
    @Schema(description = "id", example = "1")
    private Integer id;

    @Schema(description = "Call Number", example = "+385")
    private String callNumber;

    @Schema(description = "Telephone number", example = "91-123-1234")
    private String telephoneNumber;

    public PhoneNumberModificationDTO(
            final Integer id,
            @NonNull final String callNumber,
            @NonNull final String telephoneNumber
    ) {
        this.id = id;
        this.callNumber = callNumber;
        this.telephoneNumber = telephoneNumber;
    }

    @Schema(hidden = true)
    public boolean isPhoneNumberValid(){
        // Use Stream API to check if all phone numbers are valid
        return Validator.validateCallNumber(callNumber);
    }
}
