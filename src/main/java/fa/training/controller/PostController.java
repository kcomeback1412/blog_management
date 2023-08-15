package fa.training.controller;

import fa.training.entities.*;
import fa.training.entities.enums.PostStatus;
import fa.training.entities.enums.TypeEnum;
import fa.training.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/home/")
public class PostController {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    LookupRepository lookupRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;



    @GetMapping("/create-post")
    public String createPostUI(
            Model model
    ) {

        Users userLogged = usersRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Lookup> postStatusLookups = lookupRepository.getAllByTypeOrderByPositionAsc(TypeEnum.POST_STATUS.getTypeName());
        List<Tag> top5Tags = tagRepository.getFiveTagsFrequencyMost();
        Comments lastComments = commentRepository.getLastComment();

        model.addAttribute("userLogged" ,userLogged);
        model.addAttribute("topFiveTags", top5Tags);
        model.addAttribute("postStatus", postStatusLookups);
        model.addAttribute("lastComment", lastComments);
        return "post/create-post";
    }

    @PostMapping("/create-post")
    public String createPost(
            @ModelAttribute("postToCreate") Post post
    ) {

        String rawTags = post.getTags();
        rawTags.trim();
        String rawTagToArr[] = rawTags.split(",");
        for (int i = 0; i < rawTagToArr.length; i++) {
            rawTagToArr[i] = rawTagToArr[i].trim();
            Tag findTagInDB = tagRepository.findByName(rawTagToArr[i]);
            if(findTagInDB != null) {
                findTagInDB.setFrequency(findTagInDB.getFrequency() + 1);
                tagRepository.save(findTagInDB);
            } else {
                Tag newTag = Tag.builder()
                        .name(rawTagToArr[i])
                        .frequency(1)
                        .build();
                tagRepository.save(newTag);
            }
        }
        String tags = Arrays.stream(rawTagToArr).collect(Collectors.joining(", "));

        Users author = usersRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());


        post.setTags(tags);
        post.setCreateTime(LocalDate.now());
        post.setUpdateTime(LocalDate.now());
        post.setUsers(author);

        postRepository.save(post);
        return "redirect:/home";
    }

    @GetMapping("/manage-post")
    public String managePostUI(
            Model model
    ) {
        Users userLogged = usersRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Tag> top5Tags = tagRepository.getFiveTagsFrequencyMost();
        List<Post> postIsManaged = postRepository.getAllByUsers(userLogged);
        Comments lastComments = commentRepository.getLastComment();

        model.addAttribute("userLogged" ,userLogged);
        model.addAttribute("topFiveTags", top5Tags);
        model.addAttribute("posts", postIsManaged);
        model.addAttribute("lastComment", lastComments);
        return "post/manage-post";
    }

    @GetMapping("/manage-post/delete/{id}")
    public String deletePost(
            @PathVariable("id") Integer id,
            RedirectAttributes redirectAttributes
    ) {
        postRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("msgSuccess", "Delete post is successfully");
        return "redirect:/home/manage-post";
    }



    @GetMapping("/manage-post/edit/{id}")
    public String editPost(
            @PathVariable("id") Integer id,
            Model model
    ) {
        List<Lookup> postStatusLookups = lookupRepository.getAllByTypeOrderByPositionAsc(TypeEnum.POST_STATUS.getTypeName());
        Users userLogged = usersRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Tag> top5Tags = tagRepository.getFiveTagsFrequencyMost();
        List<Post> postIsManaged = postRepository.getAllByUsers(userLogged);
        Comments lastComments = commentRepository.getLastComment();

        model.addAttribute("postInDB", postRepository.findById(id).orElse(null));
        model.addAttribute("userLogged" ,userLogged);
        model.addAttribute("topFiveTags", top5Tags);
        model.addAttribute("postStatus", postStatusLookups);
        model.addAttribute("posts", postIsManaged);
        model.addAttribute("lastComment", lastComments);
        return "post/edit-post";
    }

    @PostMapping("/manage-post/edit")
    public String editPost(
            @ModelAttribute("post") Post post,
            RedirectAttributes redirectAttributes
    ) {

        String rawTags = post.getTags();
        rawTags.trim();
        String rawTagToArr[] = rawTags.split(",");
        for (int i = 0; i < rawTagToArr.length; i++) {
            rawTagToArr[i] = rawTagToArr[i].trim();
            Tag findTagInDB = tagRepository.findByName(rawTagToArr[i]);
            if(findTagInDB != null) {
                continue;
            } else {
                Tag newTag = Tag.builder()
                        .name(rawTagToArr[i])
                        .frequency(1)
                        .build();
                tagRepository.save(newTag);
            }
        }
        String tags = Arrays.stream(rawTagToArr).collect(Collectors.joining(", "));

        Users author = usersRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());


        post.setTags(tags);
        post.setUpdateTime(LocalDate.now());
        post.setUsers(author);

        postRepository.save(post);

        redirectAttributes.addFlashAttribute("msgSuccess", "Edit post is successfully!");
        return "redirect:/home/manage-post";
    }


    @GetMapping("/post/{id}")
    public String homePageUI(
            @PathVariable("id") Integer id,
            Model model
    ){

//        Page<Post> postPages = postRepository.findAll(pageable);

        Users userLogged = usersRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Tag> top5Tags = tagRepository.getFiveTagsFrequencyMost();
        List<Comments> comments = commentRepository.getTop5ByPostOrderByIdDesc(id);
        Comments lastComments = commentRepository.getLastComment();

        model.addAttribute("userLogged" , userLogged);
        model.addAttribute("topFiveTags", top5Tags);
        model.addAttribute("post",postRepository.findById(id).orElse(null));
        model.addAttribute("comments", comments);
        model.addAttribute("lastComment", lastComments);
        return "post/view-post";
    }

}
