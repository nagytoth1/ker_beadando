package keretrendszer.Controllers;

import keretrendszer.Models.Computer;
import keretrendszer.Repositories.*;
import keretrendszer.Validator.ComputerValidator;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.xml.bind.ValidationException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Optional;

@Controller
public class ComputerController {
    @Autowired
    ComputerRepo computers;
    @Autowired
    ProcessorRepo processors;
    @Autowired
    RamRepo rams;
    @Autowired
    VideocardRepo videocards;
    @Autowired
    OpsystemsRepo systems;
    @Autowired
    PsuRepository psus;
    @GetMapping("/uploadpc")
    public String uploadForm(Model model,
                             @ModelAttribute("failMsg") String failMsg,
                             @ModelAttribute("succMsg") String succMsg){
        model.addAttribute("title", "[STACKER] Számítógép feltöltése");
        model.addAttribute("computer", new Computer());
        model.addAttribute("processors", processors.findAll());
        model.addAttribute("videocards", videocards.findAll());
        model.addAttribute("opsystems", systems.findAll());
        model.addAttribute("supplies", psus.findAll());
        model.addAttribute("rams", rams.findAll());
        model.addAttribute("user", HomeController.getUserLoggedIn());
        model.addAttribute("failMsg", failMsg);
        model.addAttribute("succMsg", succMsg);
        return "upload_computer";
    }

    @GetMapping("/details/{id}")
    public String showDetails(@PathVariable("id") long id, RedirectAttributes redirectAttributes, Model model){
        Optional<Computer> foundComputer = computers.findById(id);
        if(foundComputer.isEmpty()){
            redirectAttributes.addAttribute("failMsg", "Nincs ilyen számítógép");
            return "redirect:/";
        }
        model.addAttribute("computer", foundComputer.get());
        model.addAttribute("user", HomeController.getUserLoggedIn());
        model.addAttribute("title", "Számítógép részletei");
        return "computers";
    }

    @GetMapping("/deletepc/{id}")
    public String deletePC(@PathVariable("id") long id, RedirectAttributes attributes){
        try{
            if(HomeController.getUserLoggedIn() != null &&
                    !HomeController.getUserLoggedIn().getRole().getName().equals("stacker"))
                throw new Exception("Törlés sikertelen: Nincs stacker-jogosultságod!");
            Optional<Computer> foundComputer = computers.findById(id);
            if(foundComputer.isEmpty())
                throw new Exception("Törlés sikertelen: A számítógép nem található!");
            computers.deleteById(id);
            attributes.addAttribute("succMsg", "A számítógép törlése sikeres volt."); //ha nincs kivétel, akkor a vezérlés idáig el fog jutni
        }
        catch (Exception e){
            attributes.addAttribute("failMsg", e.getMessage());
        }
        attributes.addAttribute("user", HomeController.getUserLoggedIn());
        return "redirect:/";
    }
    @PostMapping("/uploadpc")
    public String uploadPC(@ModelAttribute Computer computer, RedirectAttributes attributes){
        try{
            Computer newComputer = new Computer();
            ComputerValidator.validate(computer);
            newComputer.setComputer(computer); //hajtsa végre az összes settert rajta
            computers.save(newComputer);
            attributes.addAttribute("succMsg", "Sikeres feltöltés!");
        }
        catch(Exception e){
            attributes.addAttribute("failMsg", e.getMessage());
        }
        return "redirect:/uploadpc";
    }

    @GetMapping("/buypc/{id}")
    public String buyPC(@PathVariable("id") long id, RedirectAttributes attributes){
        try{
            Optional<Computer> comp = computers.findById(id);
            if(comp.isEmpty())
                throw new Exception("Nem található ilyen számítógép");
            Computer compToBuy = comp.get();
            compToBuy.setBought(true);
            computers.save(compToBuy);
        }
        catch(Exception e){
            attributes.addAttribute("failMsg", e.getMessage());
        }
        return "redirect:/uploadpc";
    }

}
