package fa.training.repositories;

import fa.training.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {

    @Query(value = "SELECT T FROM Tag as T ORDER BY T.frequency DESC LIMIT 5")
    List<Tag> getFiveTagsFrequencyMost();

    Tag findByName(String name);


}