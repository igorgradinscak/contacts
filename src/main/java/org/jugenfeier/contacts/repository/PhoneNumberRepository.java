package org.jugenfeier.contacts.repository;

import lombok.NonNull;
import org.jugenfeier.contacts.model.PhoneNumber;
import org.jugenfeier.contacts.repository.base.SoftDeleteRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhoneNumberRepository extends SoftDeleteRepository<PhoneNumber,Integer> {

    @Query(value = "SELECT * FROM phone_number pn WHERE pn.id = :id", nativeQuery = true)
    Optional<PhoneNumber> findByIdAndIncludeDeleted(@NonNull @Param(("id")) Integer id);

    @Query(value = "SELECT * FROM phone_number pn WHERE pn.id = :id AND pn.deleted IS NOT NULL", nativeQuery = true)
    Optional<PhoneNumber> findDeletedById(@NonNull @Param(("id")) Integer id);

    @Query(value = "SELECT * FROM phone_number c", nativeQuery = true)
    List<PhoneNumber> findAllAndIncludeDeleted();

    @Query(value = "SELECT * FROM phone_number c WHERE c.deleted IS NOT NULL", nativeQuery = true)
    List<PhoneNumber> findAllDeleted();

    @Query(value = "SELECT * FROM phone_number c WHERE c.deleted IS NULL", nativeQuery = true)
    List<PhoneNumber> findAllExcludeDeleted();
}
