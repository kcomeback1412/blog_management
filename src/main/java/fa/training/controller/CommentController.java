package fa.training.controller;

import fa.training.entities.Comments;
import fa.training.entities.Post;
import fa.training.repositories.CommentRepository;
import fa.training.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
public class CommentController {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    @PostMapping("/home/post/{id}/create-comment")
    public String createCommentToThePost(
            @PathVariable("id") Integer id,
            @ModelAttribute("newComment")Comments comments
        ) {
        comments.setCreateTime(LocalDateTime.now());
        comments.setId(null);
        commentRepository.save(comments);

        return "redirect:/home/post/{id}";
    }
}
