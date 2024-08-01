package dev.abarmin.spring.controller.web;

import dev.abarmin.spring.controller.web.model.UserForm;
import dev.abarmin.spring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/web/users")
public class UserWebController {
    private final UserRepository userRepository;
    private final UserDetailsManager userManager;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    public String index(Model model,
                        Authentication authentication) {
        model.addAttribute("users", userRepository.findAll());
        return "users/index";
    }

    @GetMapping("/add")
    public String createNew(Model model) {
        model.addAttribute("user", new UserForm());
        return "users/edit";
    }

    @ModelAttribute("authorities")
    public Collection<String> authorities() {
        return List.of("admin", "user");
    }

    @PostMapping
    public String save(@ModelAttribute("user") UserForm userForm,
                       BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "users/edit";
        }

        UserDetails userDetails = User.builder()
                .username(userForm.getUsername())
                .password(passwordEncoder.encode(userForm.getPassword()))
                .authorities(userForm.getAuthorities().toArray(new String[]{}))
                .build();
        if (userManager.userExists(userForm.getUsername())) {
            // update of an existing user
            userManager.updateUser(userDetails);
        } else {
            // create a new user
            userManager.createUser(userDetails);
        }

        return "redirect:/web/users";
    }
}
