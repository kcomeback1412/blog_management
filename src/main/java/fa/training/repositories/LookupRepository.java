package fa.training.repositories;

import fa.training.entities.Lookup;
import fa.training.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LookupRepository extends JpaRepository<Lookup, Integer> {

    List<Lookup> getAllByTypeOrderByPositionAsc(String type);
}