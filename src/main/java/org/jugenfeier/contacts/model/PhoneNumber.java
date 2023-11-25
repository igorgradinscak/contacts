package org.jugenfeier.contacts.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.Where;
import org.jugenfeier.contacts.model.base.SoftDeletedModelBase;

@Entity
@Table(name = "phone_number")
@Where(clause = "deleted is NULL")
@AllArgsConstructor @NoArgsConstructor
@Builder
@Getter @Setter
@ToString
public class PhoneNumber extends SoftDeletedModelBase {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "phone_number_id_sequence_generator")
    @SequenceGenerator(name = "phone_number_id_sequence_generator", sequenceName = "phone_number_id_seq",
            allocationSize = 1)
    private Integer id;

    @Pattern(regexp = "\\+\\d{3}", message = "Call number must start with '+' followed by 3 digits")
    @NotBlank(message = "Call number is required")
    @Column(name = "call_number")
    private String callNumber;

    @Column(name = "telephone_number")
    private String telephoneNumber;

    @Column(name = "contact_id", nullable = false, insertable = false, updatable = false)
    private Integer contactId;

    @ManyToOne
    @JoinColumn(name="contact_id", nullable = false)
    @JsonBackReference
    private Contact contact;
}
