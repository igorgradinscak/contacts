package org.jugenfeier.contacts.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jugenfeier.contacts.dto.ContactModificationDTO;
import org.jugenfeier.contacts.model.Contact;
import org.jugenfeier.contacts.model.PhoneNumber;
import org.jugenfeier.contacts.repository.ContactRepository;
import org.jugenfeier.contacts.repository.PhoneNumberRepository;
import org.jugenfeier.contacts.service.IContactService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ContactService implements IContactService {

    private final ContactRepository contactRepository;
    private final PhoneNumberRepository phoneNumberRepository;

    @Override
    public List<Contact> getAllContactsWithoutDeleted(){
        return contactRepository.findAll();
    }

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

        Contact finalContact = contact;
        List<PhoneNumber> phoneNumberList = contactModificationDTO.getPhoneNumberList().stream()
                .map(phone -> {
                    try {
                        PhoneNumber phoneNumber = PhoneNumber.builder()
                                .callNumber(phone.getCallNumber())
                                .telephoneNumber(phone.getTelephoneNumber())
                                .contact(finalContact)
                                .build();
                        phoneNumberRepository.save(phoneNumber);
                        return phoneNumber;
                    } catch (Exception e) {
                        log.warn("Error while saving new Contact phone number with data: {}, exception: {}", phone, e.getMessage());
                        throw new RuntimeException("Exception while creating new phone number");
                    }
                })
                .toList();

        // Update the Contact with the saved PhoneNumber list
        contact.setPhoneNumberList(phoneNumberList);
        contact = contactRepository.save(contact);

        return contact;
    }

    @Override
    public Contact updateContact(ContactModificationDTO contactModificationDTO, int contactId) {
         log.trace("Updating contact (ID: {}) with data: {}", contactId, contactModificationDTO);

         final Contact contact = contactRepository.findById(contactId).orElseThrow();
         contact.setUsername(contactModificationDTO.getUsername());
         contact.setFirstName(contactModificationDTO.getFirstName());
         contact.setLastName(contactModificationDTO.getLastName());
         contact.setEmail(contactModificationDTO.getEmail());

         log.trace("Saving updated Contact: {}", contact);
         return contactRepository.save(contact);
    }

    @Override
    public void deleteContact(int contactId) {
        final Contact contact = contactRepository.findById(contactId).orElseThrow();
        contact.getPhoneNumberList().forEach(phoneNumber -> {
            log.trace("Deleting phone number: {}", phoneNumber);
            phoneNumberRepository.deleteById(phoneNumber.getId());
        });
        log.trace("Deleting contact: {}", contact);
        contactRepository.delete(contact);
    }
}
