package org.jugenfeier.contacts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NonNull;

@Data
public class ContactModificationDTO {

    @Schema(description = "Contact username", example = "Username")
    private String username;

    @Schema(description = "Contact first name", example = "John")
    private String firstName;

    @Schema(description = "Contact last name", example = "Doe")
    private String lastName;

    @Schema(description = "Contact email", example = "john.doe@example.com")
    private String email;

    @Schema(description = "Contact phone number")
    private PhoneNumberModificationDTO phoneNumber;

    public ContactModificationDTO(
            final String username,
            @NonNull final String firstName,
            @NonNull final String lastName,
            @NonNull final String email,
            @NonNull final PhoneNumberModificationDTO phoneNumber
            ) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    @Schema(hidden = true)
    public boolean isRequestValid(final boolean create){
        if (create && username == null) {
            return false;
        } else if (!create && username == null) {
            return false;
        }

        return true;
    }
}
