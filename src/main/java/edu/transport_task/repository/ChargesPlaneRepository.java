package edu.transport_task.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import edu.transport_task.entity.ChargesPlane;

import java.util.List;

@Transactional(propagation = Propagation.MANDATORY)
public interface ChargesPlaneRepository extends CrudRepository<ChargesPlane, Long> {

    List<ChargesPlane> findAll();

}
