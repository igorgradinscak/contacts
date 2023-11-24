package org.jugenfeier.contacts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NonNull;
import org.jugenfeier.contacts.model.Contact;
import org.jugenfeier.contacts.model.PhoneNumber;

@Data
public class ContactDTO {

    @Schema(description = "Directory ID", example = "2")
    private Integer id;

    @Schema(description = "Contact username", example = "Username")
    private String username;

    @Schema(description = "Contact first name", example = "John")
    private String firstName;

    @Schema(description = "Contact last name", example = "Doe")
    private String lastName;

    @Schema(description = "Contact email", example = "john.doe@example.com")
    private String email;

    @Schema(description = "Phone number")
    private PhoneNumberDTO phoneNumber;

    public ContactDTO(final Contact contact) {
        this.id = contact.getId();
        this.username = contact.getUsername();
        this.firstName = contact.getFirstName();
        this.lastName = contact.getLastName();
        this.email = contact.getEmail();
        this.phoneNumber = new PhoneNumberDTO(contact.getPhoneNumber());
    }
}
