package org.jugenfeier.contacts.service;

import org.jugenfeier.contacts.dto.ContactModificationDTO;
import org.jugenfeier.contacts.model.Contact;

import java.util.List;

public interface IContactService {

    List<Contact> getAllContactsWithoutDeleted();
    Contact createNewContact(ContactModificationDTO contactDTO) throws Exception;
    Contact updateContact(ContactModificationDTO contactModificationDTO, int contactId);
    void deleteContact(int contactId);
}
