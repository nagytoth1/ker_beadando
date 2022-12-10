package keretrendszer.beadando.Controllers;

import keretrendszer.beadando.Models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private static User userLoggedIn;

    @GetMapping("/")
    public String showMainPage(Model model){
        model.addAttribute("title", "Főoldal");
        if(userLoggedIn != null)
            model.addAttribute("succMsg", "Sikeres bejelentkezés!");
        model.addAttribute("user", userLoggedIn);
        return "main";
    }
    @GetMapping("/profile")
    public String showProfile(Model model){
        model.addAttribute("user", userLoggedIn);
        return "profile";
    }

    public static User getUserLoggedIn() {
        return userLoggedIn;
    }

    public static void setUserLoggedIn(User value) {
        userLoggedIn = value;
    }
}
