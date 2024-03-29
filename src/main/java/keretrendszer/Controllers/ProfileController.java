package keretrendszer.Controllers;

import keretrendszer.Models.User;
import keretrendszer.Repositories.UserRepo;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    UserRepo users;
    @GetMapping("/")
    public String showProfile(Model model){
        //ne kelljen mindig átadni a bejelentkezett user-t, kéne egy layout_main.html, ami include-olja a navbar-t, és a content az változik
        model.addAttribute("user", HomeController.getUserLoggedIn());
        model.addAttribute("title", "Profilbeállítások");
        return "profile";
    }
    @GetMapping("/modifyName")
    public String modifyName(Model m, @RequestParam String username){
        try{
            User.getValidator().validateUsername(username);
        }catch(AuthenticationException e){
            m.addAttribute("failMsg", e.getMessage());
            return showProfile(m);
        }
        User userToModify = HomeController.getUserLoggedIn();

        if(userToModify.getUsername().equals(username)){
            m.addAttribute("failMsg", "A megadott felhasználónév megegyezik a korábbival");
            return showProfile(m);
        }
        userToModify.setUsername(username);
        users.save(userToModify);

        m.addAttribute("succMsg", "Sikeresen módosítottam a felhasználónevedet!");
        return showProfile(m);
    }

    @GetMapping("/modifyMail")
    public String modifyMail(Model m, @RequestParam String email){
        try{
            User.getValidator().validateEmail(email);
        }catch(AuthenticationException e){
            m.addAttribute("failMsg", e.getMessage());
            return showProfile(m);
        }

        User userToModify = HomeController.getUserLoggedIn();
        if(userToModify.getEmail().equals(email)){
            m.addAttribute("failMsg", "A megadott e-mail cím megegyezik a korábbival.");
            return showProfile(m);
        }

        userToModify.setEmail(email);
        users.save(userToModify);

        m.addAttribute("succMsg", "Sikeresen módosítottam az e-mail címedet!");
        return showProfile(m);
    }
    @PostMapping("/modifyPasswd")
    public String modifyPasswd(@RequestParam String newpasswd, Model m){
        try {
            User.getValidator().validatePassword(newpasswd);
        } catch (AuthenticationException e) {
            m.addAttribute("failMsg", e.getMessage());
            return showProfile(m);
        }

        String encryptedPasswd = BCrypt.hashpw(
                newpasswd,
                BCrypt.gensalt(10)); //hash password via BCrypt
        User userToModify = HomeController.getUserLoggedIn();
        userToModify.setPassword(encryptedPasswd);
        users.save(userToModify);
        m.addAttribute("succMsg", "Jelszavadat sikeresen módosítottam!");
        return showProfile(m);
    }
}
