package edu.transport_task.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import edu.transport_task.entity.Consumer;

import java.util.List;

@Transactional(propagation = Propagation.MANDATORY)
public interface ConsumerRepository extends CrudRepository<Consumer, Long> {

    List<Consumer> findAll();

}
