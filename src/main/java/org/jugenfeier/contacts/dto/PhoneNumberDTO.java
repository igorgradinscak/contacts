package org.jugenfeier.contacts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.jugenfeier.contacts.model.PhoneNumber;

@Data
public class PhoneNumberDTO {

    @Schema(description = "Phone number ID", example = "1")
    private Integer id;

    @Schema(description = "Phone number callNumber", example = "+385")
    private String callNumber;

    @Schema(description = "Phone number telephoneNumber", example = "91-123-1234")
    private String telephoneNumber;

    public PhoneNumberDTO(final PhoneNumber phoneNumber) {
        this.id = phoneNumber.getId();
        this.callNumber = phoneNumber.getCallNumber();
        this.telephoneNumber = phoneNumber.getTelephoneNumber();
    }
}
