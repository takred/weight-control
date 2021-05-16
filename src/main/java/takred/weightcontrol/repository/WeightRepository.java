package takred.weightcontrol.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import takred.weightcontrol.entity.Weight;

import java.util.List;
import java.util.UUID;

@Repository
public interface WeightRepository extends JpaRepository<Weight, UUID> {

    List<Weight> findByUserNameOrderByMeasurementDate(String userName);

}
