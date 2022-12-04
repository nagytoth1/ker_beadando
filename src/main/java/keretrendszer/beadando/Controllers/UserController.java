package keretrendszer.beadando.Controllers;

import keretrendszer.beadando.Models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.naming.AuthenticationException;

@Controller
public class UserController {
    @GetMapping("/login")
    public String showLoginForm(Model model){
        model.addAttribute("title", "Bejelentkezés");
        model.addAttribute("user", new User());
        return "login";
    }
    @GetMapping("/register")
    public String showRegForm(Model model){
        model.addAttribute("title", "Regisztráció");
        model.addAttribute("user", new User());
        return "register";
    }
    @PostMapping("/login")
    public String validateLogin(@ModelAttribute User user){
        if()
        return "redirect:/";
    }
}
