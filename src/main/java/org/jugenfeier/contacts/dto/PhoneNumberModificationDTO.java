package org.jugenfeier.contacts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NonNull;

@Data
public class PhoneNumberModificationDTO {

    @Schema(description = "Call Number", example = "+385")
    private String callNumber;

    @Schema(description = "Telephone number", example = "91-123-1234")
    private String telephoneNumber;

    public PhoneNumberModificationDTO(
            @NonNull final String callNumber,
            @NonNull final String telephoneNumber
    ) {
        this.callNumber = callNumber;
        this.telephoneNumber = telephoneNumber;
    }
}
