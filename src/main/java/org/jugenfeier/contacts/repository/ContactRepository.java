package org.jugenfeier.contacts.repository;


import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.jugenfeier.contacts.model.Contact;
import org.jugenfeier.contacts.repository.base.SoftDeleteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactRepository extends
        SoftDeleteRepository<Contact, Integer>,
        JpaSpecificationExecutor<Contact> {

    @Query(value = "SELECT * FROM contact c WHERE c.id = :id", nativeQuery = true)
    Optional<Contact> findByIdAndIncludeDeleted(@NonNull @Param(("id")) Integer id);

    @Query(value = "SELECT * FROM contact c", nativeQuery = true)
    List<Contact> findAllAndIncludeDeleted();

    @Query(value = "SELECT * FROM contact c WHERE c.deleted IS NOT NULL", nativeQuery = true)
    List<Contact> findAllDeleted();

    @Query(value = "SELECT * FROM contact c WHERE c.deleted IS NULL", nativeQuery = true)
    List<Contact> findAllExcludeDeleted();
}
