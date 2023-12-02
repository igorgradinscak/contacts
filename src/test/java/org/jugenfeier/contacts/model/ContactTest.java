package org.jugenfeier.contacts.model;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ContactTest {

    @Test
    void testGettersAndSetters() {
        // Given
        Contact contact = createSampleContact(1);

        // When
        Integer id = contact.getId();
        String username = contact.getUsername();
        String firstName = contact.getFirstName();
        String lastName = contact.getLastName();
        String email = contact.getEmail();
        List<PhoneNumber> phoneNumberList = contact.getPhoneNumberList();

        // Then
        assertEquals(1, id);
        assertEquals("john.doe", username);
        assertEquals("John", firstName);
        assertEquals("Doe", lastName);
        assertEquals("john.doe@example.com", email);
        assertEquals(Collections.emptyList(), phoneNumberList);
    }

    @Test
    void testEquals() {
        Contact contact1 = createSampleContact(1);
        Contact contact2 = createSampleContact(1);
        Contact contact3 = createSampleContact(2);

        assertEquals(contact1, contact2);
        assertNotEquals(contact1, contact3);
    }

    @Test
    void testToString() {
        Contact contact = createSampleContact(1);

        String toStringResult = contact.toString();

        assertTrue(toStringResult.contains("id=1"));
        assertTrue(toStringResult.contains("username=john.doe"));
        assertTrue(toStringResult.contains("firstName=John"));
        assertTrue(toStringResult.contains("lastName=Doe"));
        assertTrue(toStringResult.contains("email=john.doe@example.com"));
        assertFalse(toStringResult.contains("phoneNumberList=[]"));
    }

    private Contact createSampleContact(int id) {
        return Contact.builder()
                .id(id)
                .username("john.doe")
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phoneNumberList(Collections.emptyList())
                .build();
    }
}
