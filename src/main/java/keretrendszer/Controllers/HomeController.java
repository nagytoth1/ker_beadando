package keretrendszer.Controllers;

import keretrendszer.Models.User;
import keretrendszer.Repositories.ComputerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private static User userLoggedIn;
    @Autowired
    ComputerRepo computerRepo;
    @GetMapping("/")
    public String showMainPage(Model model){
        model.addAttribute("title", "Főoldal");
        if(userLoggedIn != null)
            model.addAttribute("succMsg", "Sikeres bejelentkezés!");
        model.addAttribute("user", userLoggedIn);
        model.addAttribute("computers", computerRepo.findAll());
        return "index";
    }

    //----------GETTERS, SETTERS---------
    public static User getUserLoggedIn() {
        return userLoggedIn;
    }

    public static void setUserLoggedIn(User value) {
        userLoggedIn = value;
    }
}
