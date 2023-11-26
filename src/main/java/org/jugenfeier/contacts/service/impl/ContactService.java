package org.jugenfeier.contacts.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jugenfeier.contacts.dto.ContactModificationDTO;
import org.jugenfeier.contacts.dto.ContactUpdateModificationDTO;
import org.jugenfeier.contacts.dto.PhoneNumberModificationDTO;
import org.jugenfeier.contacts.dto.PhoneNumberUpdateModificationDTO;
import org.jugenfeier.contacts.model.Contact;
import org.jugenfeier.contacts.model.PhoneNumber;
import org.jugenfeier.contacts.repository.ContactRepository;
import org.jugenfeier.contacts.repository.PhoneNumberRepository;
import org.jugenfeier.contacts.service.IContactService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@AllArgsConstructor
@Slf4j
public class ContactService implements IContactService {

    private final ContactRepository contactRepository;
    private final PhoneNumberRepository phoneNumberRepository;

    /**
     * Retrieves a list of all contacts excluding those marked as deleted.
     *
     * @return A list of contacts excluding those marked as deleted.
     */
    @Override
    public List<Contact> getAllContactsWithoutDeleted(){
        return contactRepository.findAll();
    }

    /**
     * Creates a new contact based on the provided ContactModificationDTO.
     *
     * <p>
     * This method takes a ContactModificationDTO as input and creates a new contact using the provided data.
     * It saves the basic contact information, including the username, first name, last name, and email.
     * Additionally, it associates the contact with phone numbers provided in the ContactModificationDTO.
     * The contact entity is then saved to the repository along with the associated phone numbers.
     * </p>
     *
     * @param contactModificationDTO The data for creating a new contact.
     * @return The created contact entity.
     * @throws Exception If there is an error during the contact creation or phone number saving process.
     */
    @Override
    public Contact createNewContact(ContactModificationDTO contactModificationDTO) {
        log.trace("Creating new Contact with data: {}", contactModificationDTO);

        Contact contact = Contact.builder()
                .username(contactModificationDTO.getUsername())
                .firstName(contactModificationDTO.getFirstName())
                .lastName(contactModificationDTO.getLastName())
                .email(contactModificationDTO.getEmail())
                .build();
        log.trace("Saving new Contact: {}", contact);
        contact = contactRepository.save(contact);

        // Save the phone numbers and update the contact with the saved list
        Contact finalContact = contact;
        List<PhoneNumber> phoneNumberList = getAndSavePhoneNumberList(contactModificationDTO.getPhoneNumberList(), finalContact);
        contact.setPhoneNumberList(phoneNumberList);
        contact = contactRepository.save(contact);

        return contact;
    }

    /**
     * Updates an existing contact based on the provided {@link ContactUpdateModificationDTO}.
     *
     * <p>This method updates the contact's properties, such as username, first name, last name, and email,
     * based on the data provided in the {@code contactUpdateModificationDTO}. It also updates the associated
     * phone numbers using the {@link #getAndUpdatePhoneNumberList(List, Contact)} method.</p>
     *
     * @param contactUpdateModificationDTO The data to update the contact.
     * @param contactId The ID of the contact to be updated.
     * @return The updated contact entity.
     * @throws NoSuchElementException If the contact with the specified ID is not found.
     * @throws RuntimeException If there is an error while updating the contact or associated phone numbers.
     */
    @Override
    public Contact updateContact(ContactUpdateModificationDTO contactUpdateModificationDTO, int contactId) {
         log.trace("Updating contact (ID: {}) with data: {}", contactId, contactUpdateModificationDTO);

         final Contact contact = contactRepository.findById(contactId).orElseThrow();
         contact.setUsername(contactUpdateModificationDTO.getUsername());
         contact.setFirstName(contactUpdateModificationDTO.getFirstName());
         contact.setLastName(contactUpdateModificationDTO.getLastName());
         contact.setEmail(contactUpdateModificationDTO.getEmail());

         contact.setPhoneNumberList(getAndUpdatePhoneNumberList(
                 contactUpdateModificationDTO.getPhoneNumberList(), contact));

         log.trace("Saving updated Contact: {}", contact);
         return contactRepository.save(contact);
    }

    /**
     * Deletes a contact and its associated phone numbers based on the provided contact ID.
     * If the contact does not exist, a NoSuchElementException is thrown.
     *
     * @param contactId The ID of the contact to be deleted.
     * @throws NoSuchElementException If no contact with the specified ID is found.
     */
    @Override
    public void deleteContact(int contactId) throws NoSuchElementException {
        final Contact contact = contactRepository.findById(contactId).orElseThrow();
        contact.getPhoneNumberList().forEach(phoneNumber -> {
            log.trace("Deleting phone number: {}", phoneNumber);
            phoneNumberRepository.deleteById(phoneNumber.getId());
        });
        log.trace("Deleting contact: {}", contact);
        contactRepository.delete(contact);
    }

    /**
     * Creates a new contact phone number based on the provided ContactModificationDTO
     * and provide list of all created phone numbers.
     *
     * @param phoneNumberModificationDTOList The data for creating new phone numbers.
     * @param finalContact The existing contact to be updated.
     * @return The list of created phone numbers.
     * @throws RuntimeException If there is an error while creating and saving phone number.
     */
    private List<PhoneNumber> getAndSavePhoneNumberList(List<PhoneNumberModificationDTO> phoneNumberModificationDTOList, Contact finalContact) {
        return phoneNumberModificationDTOList.stream()
                .map(phone -> {
                    PhoneNumber phoneNumber = PhoneNumber.builder()
                                    .callNumber(phone.getCallNumber())
                                    .telephoneNumber(phone.getTelephoneNumber())
                                    .contact(finalContact)
                                    .build();
                    try {
                        assert phoneNumber != null;
                        phoneNumberRepository.save(phoneNumber);
                        return phoneNumber;
                    } catch (Exception e) {
                        log.warn("Error while saving Contact phone number with data: {}, exception: {}", phone, e.getMessage());
                        throw new RuntimeException("Exception while creating new phone number");
                    }
                })
                .toList();
    }

    /**
     * Updates an existing contact phone list, or create new phone if it doesn't exist.
     *
     * @param phoneNumberUpdateModificationDTOList The data for creating or updating a phone number.
     * @param contact The existing contact to be updated.
     * @return The list of updated and created phone numbers.
     * @throws RuntimeException If there is an error while creating or updating the phone numbers.
     */
    private List<PhoneNumber> getAndUpdatePhoneNumberList(List<PhoneNumberUpdateModificationDTO> phoneNumberUpdateModificationDTOList, Contact contact) {
        return phoneNumberUpdateModificationDTOList.stream()
                .map(phone -> {
                    if (phone.getId() != null) {
                        return phoneNumberRepository.findById(phone.getId())
                                .filter(existingPhoneNumber -> Objects.equals(existingPhoneNumber.getContactId(), contact.getId()))
                                .map(existingPhoneNumber -> {
                                    existingPhoneNumber.setCallNumber(phone.getCallNumber());
                                    existingPhoneNumber.setTelephoneNumber(phone.getTelephoneNumber());
                                    return existingPhoneNumber;
                                })
                                .orElse(null);
                    } else {
                        return PhoneNumber.builder()
                                .callNumber(phone.getCallNumber())
                                .telephoneNumber(phone.getTelephoneNumber())
                                .contact(contact)
                                .build();
                    }
                })
                .filter(Objects::nonNull)
                .peek(phoneNumber -> {
                    try {
                        phoneNumberRepository.save(phoneNumber);
                    } catch (Exception e) {
                        log.warn("Error while saving Contact phone number with data: {}, exception: {}", phoneNumber, e.getMessage());
                        throw new RuntimeException("Exception while updating phone number");
                    }
                })
                .toList();
    }
}
