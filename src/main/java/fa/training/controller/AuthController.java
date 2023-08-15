package fa.training.controller;

import fa.training.entities.Roles;
import fa.training.entities.UserRoles;
import fa.training.entities.UserRolesId;
import fa.training.entities.Users;
import fa.training.entities.enums.RoleEnum;
import fa.training.repositories.UserRolesRepository;
import fa.training.repositories.UsersRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    UserRolesRepository userRolesRepository;

    @GetMapping("/login")
    public String loginUI(Model model) {
//        model.addAttribute()
        return "login";
    }

    @PostMapping("/register")
    public String registerToDB(
            @ModelAttribute("userRegister") Users users,
            RedirectAttributes redirectAttributes
    ) {
        // 1. encrypt password
        String rawPassword =users.getPassword();
        String encodePassword =passwordEncoder.encode(rawPassword);
        users.setPassword(encodePassword);
        usersRepository.save(users);

        //2.Setting role default
        Roles roles = new Roles();
        roles.setId(RoleEnum.USER.getId());

        UserRoles userRoles = new UserRoles();
        UserRolesId userRolesId = new UserRolesId();
        userRolesId.setUserId(users.getId());
        userRolesId.setRoleId(RoleEnum.USER.getId());

        userRoles.setUsers(users);
        userRoles.setRoles(roles);
        userRoles.setId(userRolesId);

        userRolesRepository.save(userRoles);

        redirectAttributes.addFlashAttribute("msgSuccess","Register is successful!");
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession){
        httpSession.invalidate();
        return "redirect:/login";
    }
}
