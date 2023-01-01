package keretrendszer.beadando.Controllers;

import keretrendszer.beadando.Models.User;
import keretrendszer.beadando.Repositories.RolesRepo;
import keretrendszer.beadando.Repositories.UserRepo;
import keretrendszer.beadando.Services.EmailSender;
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

import java.util.Optional;
import java.util.Random;
import java.util.regex.Pattern;

@Controller
public class UserController {
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
    @GetMapping("/register")
    public String showRegForm(Model m){
        System.out.println(HomeController.getUserLoggedIn());
        m.addAttribute("title", "Regisztráció");
        m.addAttribute("user", new User());
        m.addAttribute("roles", roles.findAll());
        return "register";
    }

    @GetMapping("/forgotpassword")
    public String showForgotPasswordForm(Model model){
        model.addAttribute("user", new User());
        return "forgotpassword";
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
        if(!u.isPresent()){
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
            validateLogin(user.getUsername(), user.getPassword());
        }
        catch(AuthenticationException auth){
            m.addAttribute("title", "Hiba bejelentkezéskor");
            m.addAttribute("user", new User());
            m.addAttribute("failMsg", auth.getMessage());
            return showLoginForm(m);
        }
        return "redirect:/";
    }
    private void validateLogin(String enteredValue, String enteredPlainPassword) throws AuthenticationException {
        //1 - validate user input
        if(enteredValue == null || enteredValue.equals(""))
            throw new AuthenticationException("Kérlek, add meg a felhasználónevedet vagy az e-mail címedet!");
        if(enteredPlainPassword == null || enteredPlainPassword.equals(""))
            throw new AuthenticationException("Kérlek, add meg a jelszavadat!");
        validatePassword(enteredPlainPassword);

        //2 - search if user exists in database
        User foundUser = User.isEmail(enteredValue) ?
                    users.findByEmail(enteredValue) :
                    users.findByUsername(enteredValue);

        if(foundUser == null ||
                !BCrypt.checkpw(enteredPlainPassword, foundUser.getPassword()))
            throw new AuthenticationException("Hibás bejelentkezési adatok!");

        //3 - if user exists, do the loginprocess
        HomeController.setUserLoggedIn(foundUser.getShallowCopy());
    }
    @PostMapping("/register")
    public String addUser(@ModelAttribute User user, Model m){
        //if password is not valid, then return
        try {
            validatePassword(user.getPassword());
        } catch (AuthenticationException e) {
            m.addAttribute("failMsg", e.getMessage());
            return this.showRegForm(m);
        }
        if(!User.isEmail(user.getEmail())){
            m.addAttribute("failMsg", "A megadott adat nem e-mail cím!");
            return showRegForm(m);
        }
        String encryptedPasswd = BCrypt.hashpw(
                user.getPassword(),
                BCrypt.gensalt(10)); //hash password via BCrypt
        //if user already exists with given email - must be unique
        if(users.findByEmail(user.getEmail()) != null){
            m.addAttribute("failMsg", "Ezzel az e-mail címmel már regisztráltak felhasználót.");
            return this.showRegForm(m);
        }
        //if user already exists with given username - must be unique
        if(users.findByUsername(user.getUsername()) != null){
            m.addAttribute("failMsg", "Ezzel a felhasználónévvel már regisztráltak felhasználót.");
            return this.showRegForm(m);
        }
        User regUser = new User();
        try {
            regUser.setUsername(user.getUsername());
            regUser.setPassword(encryptedPasswd);
            regUser.setEmail(user.getEmail());
        }
        catch (Exception e) {
            m.addAttribute("failMsg", e.getMessage());
            return this.showRegForm(m);
        }
        regUser.setRole(user.getRole());

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
        if(HomeController.getUserLoggedIn() != null){
            m.addAttribute("succMsg", String.format("Sikeresen hozzáadtad %s felhasználót!", regUser.getUsername()));
            return this.showUsers(m);
        }
        m.addAttribute("succMsg", String.format("Sikeres regisztráció, %s. Kérlek, jelentkezz be a folytatáshoz!", regUser.getUsername()));
        return this.showLoginForm(m);
    }
    private void validatePassword(String passwd) throws AuthenticationException {
        if(passwd == null || passwd.equals(""))
            throw new AuthenticationException("Add meg a jelszavadat!");
        if(passwd.length() < 4)
            throw new AuthenticationException("A megadott jelszó túl rövid!");
        if(passwd.length() > 40)
            throw new AuthenticationException("A megadott jelszó túl hosszú!");
        //jelszó tartalmazhat speciális karaktereket
        if(Pattern.compile("[\s_.;\n]")
                .matcher(passwd)
                .find())
            throw new AuthenticationException("A felhasználónév nem tartalmazhatja a következő karaktereket: ['szóköz', '_', '.', ';', 'sortörés']!");
    }
    @Autowired
    private EmailSender sender;
    @PostMapping("/send")
    public String sendMail(@ModelAttribute User u, Model m) {
        String email = u.getEmail();
        //0 - does mailaddr even exist?
        if(email == null || email.equals("")){
            m.addAttribute("failMsg", "Nem adtál meg e-mail címet!");
            return showForgotPasswordForm(m);
        }
        if(!User.isEmail(email)){
            m.addAttribute("failMsg", "A megadott adat nem e-mail cím!");
            return showForgotPasswordForm(m);
        }
        //1 - Can the given user be found in the table
        User foundUser = users.findByEmail(email);
        if(foundUser == null){
            m.addAttribute("failMsg", "Ilyen e-mail címmel nem regisztráltak még felhasználót!");
            return showForgotPasswordForm(m);
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
        m.addAttribute("succMsg", "E-mail címedre elküldtük a jelszavad visszaállításhoz szükséges lépéseket!");
        System.out.println("Sending mail....");
        sender.sendMail(foundUser.getEmail(),
                "Forgot password",
                String.format("Your new password: %06d\n Don't forget to change it!", number));
        System.out.println("Mail sent!");
        return showForgotPasswordForm(m);
    }
}
