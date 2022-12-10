package keretrendszer.beadando.Controllers;

import keretrendszer.beadando.Models.User;
import keretrendszer.beadando.Repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    UserRepo users;
    @GetMapping("/modify")
    public String getCarsPage(Model m, @RequestParam(value = "id") long id){
        m.addAttribute("title", "Profilom módosítása");
        m.addAttribute("user", users.findById(id));
        m.addAttribute("id", id);

        return "update-cars";
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
