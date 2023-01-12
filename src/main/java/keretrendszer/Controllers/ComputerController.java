package keretrendszer.Controllers;

import keretrendszer.Models.Computer;
import keretrendszer.Repositories.ComputerRepo;
import keretrendszer.Repositories.ProcessorRepository;
import keretrendszer.Repositories.RamRepository;
import keretrendszer.Validator.ComputerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/pc")
public class ComputerController {
    @Autowired
    ComputerRepo computerRepo;
    @Autowired
    ProcessorRepository processorRepo;
    @Autowired
    RamRepository ramRepo;
    @GetMapping("/upload")
    public String uploadForm(Model model){
        model.addAttribute("title", "[STACKER] Számítógép feltöltése");
        model.addAttribute("computer", new Computer());
        model.addAttribute("processors", processorRepo.findAll());
        model.addAttribute("rams", ramRepo.findAll());
        model.addAttribute("user", HomeController.getUserLoggedIn());
        return "upload_computer";
    }

    @GetMapping("/details/{id}")
    public String showDetails(@PathVariable("id") long id, RedirectAttributes redirectAttributes, Model model){
        Optional<Computer> foundComputer = computerRepo.findById(id);
        if(foundComputer.isEmpty()){
            redirectAttributes.addAttribute("failMsg", "Nincs ilyen számítógép");
            return "redirect:/";
        }
        model.addAttribute("computer", foundComputer.get());
        model.addAttribute("user", HomeController.getUserLoggedIn());
        model.addAttribute("title", "Számítógép részletei");
        return "computers";
    }

    @GetMapping("/delete/{id}")
    public String deletePC(@PathVariable("id") long id, RedirectAttributes attributes){
        try{
            if(HomeController.getUserLoggedIn() != null &&
                    !HomeController.getUserLoggedIn().getRole().getName().equals("stacker"))
                throw new Exception("Törlés sikertelen: Nincs stacker-jogosultságod!");
            Optional<Computer> foundComputer = computerRepo.findById(id);
            if(foundComputer.isEmpty())
                throw new Exception("Törlés sikertelen: A számítógép nem található!");
            computerRepo.deleteById(id);
            attributes.addAttribute("succMsg", "A számítógép törlése sikeres volt."); //ha nincs kivétel, akkor a vezérlés idáig el fog jutni
        }
        catch (Exception e){
            attributes.addAttribute("failMsg", e.getMessage());
        }
        return "redirect:/users";
    }
    @PostMapping("/upload")
    public String uploadPC(@ModelAttribute Computer computer, RedirectAttributes attributes){
        try {
            Computer newComputer = new Computer();
            ComputerValidator.validate(computer);
            newComputer.setComputer(computer); //hajtsa végre az összes settert rajta
            computerRepo.save(newComputer);
        }catch(Exception e){
            attributes.addAttribute("failMsg", e.getMessage());
            return "redirect:/upload";
        }
        return "redirect:/";
    }


}
