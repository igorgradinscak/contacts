package org.jugenfeier.contacts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
public class ContactUpdateModificationDTO {

    @Schema(description = "Contact username", example = "Username")
    private String username;

    @Schema(description = "Contact first name", example = "John")
    private String firstName;

    @Schema(description = "Contact last name", example = "Doe")
    private String lastName;

    @Schema(description = "Contact email", example = "john.doe@example.com")
    private String email;

    @Schema(description = "Contact phone number")
    private List<PhoneNumberUpdateModificationDTO> phoneNumberList;

    public ContactUpdateModificationDTO(
            @NonNull final String username,
            @NonNull final String firstName,
            @NonNull final String lastName,
            @NonNull final String email,
            @NonNull final List<PhoneNumberUpdateModificationDTO> phoneNumberList
            ) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumberList = phoneNumberList;
    }

    /**
     * Checks whether the request data associated with this entity is valid.
     *
     * <p>
     * This method validates the request data,based on the specified creation mode.
     * It checks if all phone numbers are valid using the {@link PhoneNumberModificationDTO#isPhoneNumberValid()} method.
     * </p>
     *
     * @return {@code true} if the request data is valid; {@code false} otherwise.
     */
    @Schema(hidden = true)
    public boolean isRequestValid(){
        // Use Stream API to check if all phone numbers are valid
        return phoneNumberList.stream()
                .allMatch(PhoneNumberUpdateModificationDTO::isPhoneNumberValid);
    }
}
