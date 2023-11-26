package org.jugenfeier.contacts.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jugenfeier.contacts.dto.ContactDTO;
import org.jugenfeier.contacts.dto.ContactModificationDTO;
import org.jugenfeier.contacts.dto.ContactUpdateModificationDTO;
import org.jugenfeier.contacts.service.IContactService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/contact")
@AllArgsConstructor
@Slf4j
@Tag(name = "Contact system", description = "Contact System API Endpoints")
public class ContactController{
    private final IContactService contactService;

    @Operation(summary = "Get all contacts")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Contact successfully fetch"),
    })
    @GetMapping()
    public ResponseEntity<List<ContactDTO>> getAllContacts(){
        log.debug("Request received: getAllContacts()");

        return ResponseEntity.ok(contactService.getAllContactsWithoutDeleted()
                .stream()
                .map(ContactDTO::new)
                .collect(Collectors.toList()));
    }

    @Operation(summary = "Create new contact")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Contact successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content)
    })
    @PostMapping()
    public ResponseEntity<ContactDTO> createNewContact(
            @Valid @RequestBody final ContactModificationDTO contactModificationDTO) {
        log.debug("Request received: createNewContact()");
        log.trace("Body: {}", contactModificationDTO);

        if (!contactModificationDTO.isRequestValid()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            return ResponseEntity.ok(new ContactDTO(
                    contactService.createNewContact(contactModificationDTO)));
        } catch (final Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Update contact by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Contact successfully updated"),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content),
            @ApiResponse(responseCode = "404", description = "Contact not found", content = @Content)
    })
    @PutMapping("{contactId}")
    public ResponseEntity<ContactDTO> updateContactById(
            @PathVariable("contactId") int contactId,
            @Valid @RequestBody final ContactUpdateModificationDTO contactUpdateModificationDTO) {
        log.debug("Request received: updateContactById()");
        if (contactId == 0) {
            log.warn("Contact ID is not set (equals 0)");
            return ResponseEntity.badRequest().build();
        }
        log.trace("Contact ID: {} Body: {}", contactId, contactUpdateModificationDTO);

        if (!contactUpdateModificationDTO.isRequestValid()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            return ResponseEntity.ok(new ContactDTO(
                    contactService.updateContact(contactUpdateModificationDTO, contactId)));
        } catch (final NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (final Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Delete contact")
    @Parameter(name = "contactId", description = "Contact ID", example = "1 ")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Delete successful", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content),
            @ApiResponse(responseCode = "404", description = "Contact not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @DeleteMapping("/{contactId}")
    public ResponseEntity<String> deleteContact(
            @PathVariable("contactId") final int contactId
    ){
        log.debug("Request received: deleteContact()");
        if (contactId == 0) {
            log.warn("Contact ID is not set (equals 0)");
            return ResponseEntity.badRequest().build();
        }

        log.trace("Contact id: {}", contactId);

        try {
            contactService.deleteContact(contactId);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e){
            return ResponseEntity.notFound().build();
        } catch (final Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }
}
