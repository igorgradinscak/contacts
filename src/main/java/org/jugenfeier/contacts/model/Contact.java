package org.jugenfeier.contacts.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;
import org.jugenfeier.contacts.model.base.BusinessModelBase;

import java.util.List;

@Entity
@Table(name="contact")
@Where(clause = "deleted is NULL")
@AllArgsConstructor @NoArgsConstructor
@Builder
@Getter @Setter
@ToString(exclude = "phoneNumberList")
public class Contact extends BusinessModelBase {
    public final static String FIELD_NAME_ID = "id";
    public final static String FIELD_NAME_USERNAME = "username";
    public final static String FIELD_NAME_FIRST_NAME = "firstName";
    public final static String FIELD_NAME_LAST_NAME = "lastName";
    public final static String FIELD_NAME_EMAIL = "email";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contact_id_sequence_generator")
    @SequenceGenerator(name = "contact_id_sequence_generator", sequenceName = "contact_id_seq",
        allocationSize = 1)
    private Integer id;

    @Column(nullable = false, length = 256)
    private String username;

    @Column(name = "first_name", length = 256)
    private String firstName;

    @Column(name = "last_name", length = 256)
    private String lastName;

    @Column(length = 256)
    private String email;

    @OneToMany (mappedBy = "contact", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<PhoneNumber> phoneNumberList;
}
