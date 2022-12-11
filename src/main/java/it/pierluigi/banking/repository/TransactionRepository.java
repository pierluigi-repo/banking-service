package it.pierluigi.banking.repository;

import it.pierluigi.banking.model.entity.TransactionRequestEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Basic spring data CRUD interface for transactions requests
 */
@Repository
public interface TransactionRepository extends CrudRepository<TransactionRequestEntity, Long> {

}