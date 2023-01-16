package keretrendszer.Controllers;

import keretrendszer.Models.User;
import keretrendszer.Repositories.RolesRepo;
import keretrendszer.Repositories.UserRepo;
import keretrendszer.Services.EmailSender;
import org.apache.tomcat.websocket.AuthenticationException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;
import java.util.Random;

@Controller
public class UserController {
    private static UserController instance;
    @Autowired
    RolesRepo roles;
    @Autowired
    UserRepo users;

    //----GET-methods-------
    @GetMapping("/login")
    public String showLoginForm(Model m){
        m.addAttribute("title", "Bejelentkezés");
        m.addAttribute("user", new User());
        return "login";
    }
    @GetMapping("/signout")
    public String signout(RedirectAttributes attributes){
        HomeController.setUserLoggedIn(null);
        attributes.addAttribute("succMsg", "Sikeresen kijelentkeztél!");
        return "redirect:/";
    }
    @GetMapping("/register")
    public String showRegForm(Model m){
        m.addAttribute("user", new User());
        m.addAttribute("roles", roles.findAll());
        m.addAttribute("title",
                HomeController.getUserLoggedIn() == null ? "Regisztráció" :
                        "Felhasználó felvétele");
        return "register";
    }

    @GetMapping("/forgot")
    public String showForgotPasswordForm(Model model){
        model.addAttribute("user", new User());
        return "forgot_passwd";
    }

    @GetMapping("/users")
    public String showUsers(Model model){
        model.addAttribute("title", "Felhasználók");
        model.addAttribute("user", HomeController.getUserLoggedIn());
        model.addAttribute("users", users.findAll());
        return "users";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model m){
        Optional<User> u = users.findById(id);
        if(u.isEmpty()){
            m.addAttribute("failMsg", "Törlés sikertelen volt: Felhasználó nem található!");
            return showUsers(m);
        }
        try{
            users.deleteById(id);
        }catch (Exception e){
            m.addAttribute("failMsg", e.getMessage());
            return showUsers(m);
        }
        m.addAttribute("succMsg", "A felhasználó törlése sikeres volt.");
        return showUsers(m);
    }



    //----POST-methods-------
    @PostMapping("/login")
    public String loginUser(@ModelAttribute User user, Model m){
        try{
            User foundUser = validateLogin(user.getUsername(), user.getPassword());
            //3 - ha a felhasználó létezik, akkor hajtsuk végre a bejelentkezést
            HomeController.setUserLoggedIn(foundUser);
        }
        catch(AuthenticationException auth){
            m.addAttribute("title", "Hiba bejelentkezéskor");
            m.addAttribute("user", new User());
            m.addAttribute("failMsg", auth.getMessage());
            return showLoginForm(m);
        }
        return "redirect:/";
    }
    @PostMapping("/register")
    public String addUser(@ModelAttribute User user, Model model, RedirectAttributes attributes){
        if(HomeController.getUserLoggedIn() != null && !HomeController.getUserLoggedIn().getRole().getName().equals("admin")){
            attributes.addAttribute("failMsg", "Nincs jogod felhasználót hozzáadni a rendszerhez!");
            return "redirect:/";
        }
        //if password is not valid, then return
        try {
            User.getValidator().validateUsername(user.getUsername());
            User.getValidator().validatePassword(user.getPassword());
            User.getValidator().validateEmail(user.getEmail());
        } catch (AuthenticationException e) {
            attributes.addAttribute("failMsg", e.getMessage());
            return "redirect:/register";
        }
        String encryptedPasswd = BCrypt.hashpw(
                user.getPassword(),
                BCrypt.gensalt(10)); //hash password via BCrypt
        //if user already exists with given email - must be unique
        if(users.findByEmail(user.getEmail()) != null){
            attributes.addAttribute("failMsg", "Ezzel az e-mail címmel már regisztráltak felhasználót.");
            return "redirect:/register";
        }
        //if user already exists with given username - must be unique
        if(users.findByUsername(user.getUsername()) != null){
            attributes.addAttribute("failMsg", "Ezzel a felhasználónévvel már regisztráltak felhasználót.");
            return "redirect:/register";
        }

        User regUser = new User();
        regUser.setUsername(user.getUsername());
        regUser.setPassword(encryptedPasswd);
        regUser.setEmail(user.getEmail());
        regUser.setRole(user.getRole());

        try{
            users.save(regUser);
        }catch(ConstraintViolationException exc){
            attributes.addAttribute("failMsg", "Adott felhasználó már létezik.");
            return "redirect:/register";
        }
        catch(Exception e){
            attributes.addAttribute("failMsg", e.getMessage());
            return "redirect:/register";
        }
        //ha admin adja hozzá
        if(HomeController.getUserLoggedIn() != null){
            model.addAttribute("succMsg", String.format("Sikeresen hozzáadtad %s felhasználót!", regUser.getUsername()));
            return this.showUsers(model);
        }
        model.addAttribute("succMsg", String.format("Sikeres regisztráció, %s. Kérlek, jelentkezz be a folytatáshoz!", regUser.getUsername()));
        return this.showLoginForm(model);
    }
    private User validateLogin(String enteredValue, String enteredPlainPassword) throws AuthenticationException {
        //1. bemenetek validálása
        User foundUser;
        try{
            User.getValidator().validateEmail(enteredValue); //ha nem felel meg az e-mail paramétereinek, felhasználónévként vizsgáljuk tovább
            foundUser = users.findByEmail(enteredValue); //ekkor tudjuk, hogy email cím lett megadva, kereshetünk email címmel
        }catch(AuthenticationException authEmail){
            //nem email cím, akkor vizsgálja meg, hogy felhasználónév-e
            User.getValidator().validateUsername(enteredValue);
            foundUser = users.findByUsername(enteredValue); //ekkor tudjuk, hogy megfelel a username követelményeinek
        }

        User.getValidator().validatePassword(enteredPlainPassword);
        //2 - a felhasználónév megkeresése az adatbázisban
        if(foundUser == null ||
            !BCrypt.checkpw(enteredPlainPassword, foundUser.getPassword()))
            throw new AuthenticationException("Hibás bejelentkezési adatok!");
        return foundUser;
    }
    @Autowired
    private EmailSender sender;
    @PostMapping("/send")
    public String sendMail(@ModelAttribute User user, Model model) {
        String enteredEmail = user.getEmail();
        try{
            User.getValidator().validateEmail(enteredEmail);
        }catch(AuthenticationException argExc){
            model.addAttribute("failMsg", argExc.getMessage());
            return showForgotPasswordForm(model);
        }
        //1 - Can the given user be found in the table
        User foundUser = users.findByEmail(enteredEmail);
        if(foundUser == null){
            model.addAttribute("failMsg", "Ilyen e-mail címmel nem regisztráltak még felhasználót!");
            return showForgotPasswordForm(model);
        }
        //2 - the passwd of foundUser should be set to a random value
        int number = new Random().nextInt(999999);
        String encryptedPasswd = BCrypt.hashpw(
                String.format("%06d", number), //get 6-digit number
                BCrypt.gensalt(10)); //hash password via BCrypt
        foundUser.setPassword(encryptedPasswd);
        System.out.printf("Email->user: Password has been set to '%06d'\n", number); //The plain passwd should be sent to the user for next login.
        //3 - send email to user - Warn user they should change password after first login
        users.save(foundUser);
        model.addAttribute("succMsg", "E-mail címedre elküldtük a jelszavad visszaállításhoz szükséges lépéseket!");
        sender.sendMail(foundUser.getEmail(),
                "Forgot password",
                String.format("Your new password: %06d\n Don't forget to change it!", number));
        return showForgotPasswordForm(model);
    }
}
