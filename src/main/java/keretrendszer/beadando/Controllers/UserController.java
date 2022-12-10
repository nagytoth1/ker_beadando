package keretrendszer.beadando.Controllers;

import keretrendszer.beadando.EmailValidationHelper;
import keretrendszer.beadando.Models.User;
import keretrendszer.beadando.Repositories.RolesRepo;
import keretrendszer.beadando.Repositories.UserRepo;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    @Autowired
    RolesRepo roles;
    @Autowired
    UserRepo users;

    //----GET-methods-------
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
        model.addAttribute("roles", roles.findAll());
        return "register";
    }



    //----POST-methods-------
    @PostMapping("/login")
    public String loginUser(@ModelAttribute User user, Model m){

        User userLoggedIn = new User();
        if(validateLogin(user.getUsername(), user.getPassword(), userLoggedIn)) //ha validáció sikeres, akkor dobjon a főoldalra
        {
            HomeController.setUserLoggedIn(userLoggedIn);
            return "redirect:/";
        }


        m.addAttribute("title", "Hiba bejelentkezéskor");
        m.addAttribute("user", new User());
        m.addAttribute("failMsg", "Invalid login credentials!");
        return "login";
    }

    private boolean validateLogin(String enteredValue, String enteredPlainPassword, User userLoggedIn) {
        User foundUser = EmailValidationHelper.isEmail(enteredValue) ?
                users.findByEmail(enteredValue) :
                users.findByUsername(enteredValue);
        if(foundUser == null || !BCrypt.checkpw(enteredPlainPassword, foundUser.getPassword())) return false;
        userLoggedIn.setUsername(foundUser.getUsername());
        userLoggedIn.setEmail(foundUser.getEmail());
        userLoggedIn.setRole(foundUser.getRole());
        userLoggedIn.setPassword(foundUser.getPassword());
        return true;
    }

    @PostMapping("/register")
    public String addUser(@ModelAttribute User user, Model m){
        String encryptedPasswd = BCrypt.hashpw(
                user.getPassword(),
                BCrypt.gensalt(10)); //hash password via BCrypt
        if(users.findByEmail(user.getEmail()) != null){
            m.addAttribute("failMsg", "Ezzel az e-mail címmel már regisztráltak felhasználót.");
            return this.showRegForm(m);
        }

        if(users.findByUsername(user.getUsername()) != null){
            m.addAttribute("failMsg", "Ezzel a felhasználónévvel már regisztráltak felhasználót.");
            return this.showRegForm(m);
        }

        User regUser = new User();
        regUser.setUsername(user.getUsername());
        regUser.setPassword(encryptedPasswd);
        regUser.setRole(user.getRole());
        regUser.setEmail(user.getEmail());

        try{
            users.save(regUser);
        }catch(ConstraintViolationException exc){
            m.addAttribute("failMsg", "Adott felhasználó már létezik.");
            return this.showRegForm(m);
        }
        catch(Exception e){
            m.addAttribute("failMsg", e.getMessage());
            return this.showRegForm(m);
        }
        System.out.printf("User beregisztrált %s névvel %s emaillel %s jelszóval\n", user.getUsername(), user.getEmail(), encryptedPasswd);
        m.addAttribute("succMsg", String.format("Sikeres regisztráció, %s. Kérlek, jelentkezz be a folytatáshoz!", regUser.getUsername()));
        return this.showLoginForm(m);
    }
}
