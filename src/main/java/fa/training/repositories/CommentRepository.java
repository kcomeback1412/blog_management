package fa.training.repositories;

import fa.training.entities.Comments;
import fa.training.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comments, Integer> {

    @Query(value = "SELECT C FROM Comments AS C WHERE C.post.id =?1 ORDER BY C.id DESC LIMIT 5")
    List<Comments> getTop5ByPostOrderByIdDesc (Integer postId);

    List<Comments> getAllByPost(Post post);

    @Query(value = "SELECT C FROM Comments AS C ORDER BY C.id DESC LIMIT 1")
    Comments getLastComment();
}