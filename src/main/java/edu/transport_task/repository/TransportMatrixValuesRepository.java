package edu.transport_task.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import edu.transport_task.entity.ChargesPlane;
import edu.transport_task.entity.TransportMatrixValues;

import java.util.List;

@Transactional(propagation = Propagation.MANDATORY)
public interface TransportMatrixValuesRepository extends CrudRepository<TransportMatrixValues, Long> {

    List<TransportMatrixValues> findAll();

}
