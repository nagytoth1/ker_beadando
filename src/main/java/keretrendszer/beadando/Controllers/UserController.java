package keretrendszer.beadando.Controllers;

import keretrendszer.beadando.Models.Role;
import keretrendszer.beadando.Models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.naming.AuthenticationException;

@Controller
public class UserController {
    @GetMapping("/")
    public String showMainPage(Model model){
        model.addAttribute("title", "Főoldal");
        model.addAttribute("user", null);
        return "main";
    }
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
    public String validateLogin(@ModelAttribute User user, Model m){
        if(user.getUsername().equals("admin") && user.getPassword().equals("admin")){
            m.addAttribute("title", "Üdvözlő oldal");
            return "welcome";
        }
        if(user.getUsername().equals("stacker") && user.getPassword().equals("stacker")){
            m.addAttribute("title", "feltöltés");
            return "upload_computer";
        }
        m.addAttribute("title", "Hiba bejelentkezéskor");
        m.addAttribute("errMessage", "Invalid login credentials!");
        return "login";
    }
}
