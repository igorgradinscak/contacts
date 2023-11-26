package org.jugenfeier.contacts.service;

import org.jugenfeier.contacts.dto.ContactModificationDTO;
import org.jugenfeier.contacts.dto.ContactUpdateModificationDTO;
import org.jugenfeier.contacts.model.Contact;

import java.util.List;
import java.util.NoSuchElementException;

public interface IContactService {

    List<Contact> getAllContactsWithoutDeleted();
    Contact createNewContact(ContactModificationDTO contactDTO) throws Exception;
    Contact updateContact(ContactUpdateModificationDTO contactUpdateModificationDTO, int contactId);
    void deleteContact(int contactId) throws NoSuchElementException;
}
