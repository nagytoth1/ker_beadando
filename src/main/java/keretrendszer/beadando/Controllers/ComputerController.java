package keretrendszer.beadando.Controllers;

import keretrendszer.beadando.Models.Computer;
import keretrendszer.beadando.Repositories.ComputerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ComputerController {
    @Autowired
    ComputerRepo computerRepo;
    @GetMapping("/upload")
    public String uploadForm(Model m){
        m.addAttribute("title", "[STACKER] Számítógép feltöltése");
        m.addAttribute("computer", new Computer());
        return "upload_computer";
    }
    @PostMapping("/upload")
    public String uploadPC(@ModelAttribute Computer computer, Model m){
        try {
        /* Computer:
            id; name; motherboard; ram; ram_quantity;
            processor; videocard; powerSupply; storage; opsystem;
        */
            Computer newComputer = new Computer();
            newComputer.setComputer(computer); //hajtsa végre az összes settert rajta
            computerRepo.save(newComputer);
        }catch(Exception e){
            m.addAttribute("failMsg", String.format("Hiba történt lekérdezés közben: %s", e.getLocalizedMessage()));
            return uploadForm(m);
        }
        return "redirect:/";
    }
}
