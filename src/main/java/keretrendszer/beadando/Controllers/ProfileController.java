package keretrendszer.beadando.Controllers;

import keretrendszer.beadando.Models.User;
import keretrendszer.beadando.Repositories.UserRepo;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    UserRepo users;
    @GetMapping("/")
    public String showProfile(Model model){
        model.addAttribute("user", HomeController.getUserLoggedIn());
        model.addAttribute("title", "Profilbeállítások");
        return "profile";
    }
    @GetMapping("/users")
    public String showUsers(Model model){
        model.addAttribute("title", "Felhasználók");
        //model.addAttribute("userLoggedIn", HomeController.getUserLoggedIn());
        model.addAttribute("users", users.findAll());
        return "users";
    }
    @GetMapping("/modifyName")
    public String modifyName(Model m, @RequestParam String username){
        User userToModify = HomeController.getUserLoggedIn();

        if(userToModify.getUsername().equals(username)){
            m.addAttribute("failMsg", "A megadott felhasználónév megegyezik a korábbival");
            return showProfile(m);
        }
        try {
            userToModify.setUsername(username);
        } catch (AuthenticationException e) {
            m.addAttribute("failMsg", e.getMessage());
            return showProfile(m);
        }
        users.save(userToModify);

        m.addAttribute("succMsg", "Sikeresen módosítottam a felhasználónevedet!");
        return showProfile(m);
    }

    @PostMapping("/modify")
    public String updateCar(@RequestParam(value = "id") long id, @RequestParam String name){
        try{
            String failMsg, succMsg;
            Optional<User> foundUser = users.findById(id);
            if(!foundUser.isPresent())
                failMsg = "Update Error: User to modify is not found";
            //TODO: finish
            return "redirect:/";
        }
        catch(Exception e){
            return "Update Error: Internal Server error";
        }
    }
}
