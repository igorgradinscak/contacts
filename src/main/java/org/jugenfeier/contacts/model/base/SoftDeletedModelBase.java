package org.jugenfeier.contacts.model.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.*;

import java.time.LocalDateTime;

@MappedSuperclass
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
public class SoftDeletedModelBase {
    public final static String FIELD_NAME_DELETED = "deleted";

    @Column(name="deleted")
    protected LocalDateTime deleted;
}
