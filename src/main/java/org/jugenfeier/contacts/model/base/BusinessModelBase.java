package org.jugenfeier.contacts.model.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.*;

import java.time.LocalDateTime;

@MappedSuperclass
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
public class BusinessModelBase extends SoftDeletedModelBase {

    public final static String FIELD_NAME_CREATED = "created";
    public final static String FIELD_NAME_UPDATED = "updated";

    @Column(name = "created", nullable = false)
    protected LocalDateTime created;

    @Column(name = "updated", nullable = false)
    protected LocalDateTime updated;

    @PrePersist
    public void doPreSave() {
        created = LocalDateTime.now();
        updated = LocalDateTime.now();
    }

    @PreUpdate
    public void doPreUpdate(){
        updated = LocalDateTime.now();
    }
}
