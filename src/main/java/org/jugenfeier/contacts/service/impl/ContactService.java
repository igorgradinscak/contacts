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
        return contactRepository.findAllExcludeDeleted();
    }

    @Override
    public Contact createNewContact(ContactModificationDTO contactModificationDTO) throws Exception {
        log.trace("Creating new Contact with data: {}", contactModificationDTO);

        final PhoneNumber phoneNumber = PhoneNumber.builder()
                .callNumber(contactModificationDTO.getPhoneNumber().getCallNumber())
                .telephoneNumber(contactModificationDTO.getPhoneNumber().getTelephoneNumber())
                .build();

        try {
            phoneNumberRepository.save(phoneNumber);
        } catch (Exception e) {
            log.warn("Error while saving new Contact phone number with data: {}, exception: {}", phoneNumber, e.getMessage());
            throw new Exception("Exception while creating new phone number");
        }

        final Contact contact = Contact.builder()
                .username(contactModificationDTO.getUsername())
                .firstName(contactModificationDTO.getFirstName())
                .lastName(contactModificationDTO.getLastName())
                .email(contactModificationDTO.getEmail())
                .phoneNumber(phoneNumber)
                .build();
        log.trace("Saving new Contact: {}", contact);
        return contactRepository.save(contact);
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
        log.trace("Deleting contact: {}", contact);
        phoneNumberRepository.deleteById(contact.getPhoneNumberId());
        contactRepository.delete(contact);
    }
}
