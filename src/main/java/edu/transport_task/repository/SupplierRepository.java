package edu.transport_task.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import edu.transport_task.entity.Supplier;

import java.util.List;

@Transactional(propagation = Propagation.MANDATORY)
public interface SupplierRepository extends CrudRepository<Supplier, Long> {

    List<Supplier> findAll();

}
