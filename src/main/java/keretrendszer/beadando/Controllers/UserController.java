package keretrendszer.beadando.Controllers;

import keretrendszer.beadando.Models.Role;
import keretrendszer.beadando.Models.User;
import keretrendszer.beadando.Repositories.RolesRepo;
import keretrendszer.beadando.Repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    @Autowired
    RolesRepo roles;
    @GetMapping("/register")
    public String showRegForm(Model model){
        model.addAttribute("title", "Regisztráció");
        model.addAttribute("user", new User());
        model.addAttribute("roles", roles.findAll());
        return "register";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute User user, Model m){
        if(user.getUsername().equals("admin") && user.getPassword().equals("admin")){
            m.addAttribute("title", "Üdvözlő oldal");
            return "main";
        }

        if(user.getUsername().equals("stacker") && user.getPassword().equals("stacker")){
            m.addAttribute("title", "feltöltés");
            return "upload_computer";
        }
        m.addAttribute("title", "Hiba bejelentkezéskor");
        m.addAttribute("failMsg", "Invalid login credentials!");
        System.out.println("wow belelépett");
        return "login";
    }

    @Autowired
    UserRepo users;

    @Autowired
    private PasswordEncoder bcryptEncoder;
    @PostMapping("/register")
    public String addUser(@ModelAttribute User user, Model m){
        User regUser = new User();
        regUser.setUsername(user.getUsername());
        regUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        regUser.setRole(user.getRole());
        try{
            users.save(regUser);
        }catch(Exception e){
            m.addAttribute("failMsg", "Hiba" + e.getMessage());
            return "register";
        }

        m.addAttribute("succMsg", String.format("Sikeres regisztráció, %s. Kérlek, jelentkezz be a folytatáshoz!", regUser.getUsername()));
        return "login";
    }
}
