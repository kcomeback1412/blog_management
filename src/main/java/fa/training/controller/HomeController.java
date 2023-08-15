package fa.training.controller;

import fa.training.entities.*;
import fa.training.entities.enums.PostStatus;
import fa.training.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    UsersRepository usersRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    ContactRepository contactRepository;

    @GetMapping(value = {"", "/", "/home"})
    public String homePageUI(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            Model model
    ){
        Sort sort = Sort.by("id").descending();
        Pageable pageable = PageRequest.of(pageNum-1, pageSize, sort);
        Page<Post> postPages = postRepository.getAllByStatus(PostStatus.PUBLISHED.getName(), pageable);
//        Page<Post> postPages = postRepository.findAll(pageable);
        Comments lastComments = commentRepository.getLastComment();
        Users userLogged = usersRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Tag> top5Tags = tagRepository.getFiveTagsFrequencyMost();

        model.addAttribute("userLogged" ,userLogged);
        model.addAttribute("topFiveTags", top5Tags);
        model.addAttribute("postPages", postPages);
        model.addAttribute("lastComment" , lastComments);
        return "index";
    }

//    @GetMapping(value = {"", "/", "/home"})


    @GetMapping("/about")
    public String aboutPageUI() {
        return "about";
    }

    @GetMapping("/contact")
    public String contactPageUI() {
        return "contact";
    }

    @PostMapping("/contact")
    public String createContact(
            @ModelAttribute("newContact") Contact contact,
            RedirectAttributes redirectAttributes
            ){
        contactRepository.save(contact);
        redirectAttributes.addFlashAttribute("msgSuccess", "Send Request Successful");
        return "redirect:/contact";
    }
}
