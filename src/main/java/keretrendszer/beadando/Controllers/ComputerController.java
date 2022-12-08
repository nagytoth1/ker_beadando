package keretrendszer.beadando.Controllers;

import keretrendszer.beadando.Models.Computer;
import keretrendszer.beadando.Repositories.ComputerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ComputerController {
    @Autowired
    ComputerRepo computerRepo;
    @GetMapping("/upload")
    public String uploadForm(Model model){
        model.addAttribute("title", "[STACKER] Számítógép feltöltése");
        model.addAttribute("computer", new Computer());
        return "upload_computer";
    }
    @PostMapping("/upload")
    public String uploadPC(@ModelAttribute Computer computer, Model model){
        try {
            Computer newComputer = new Computer();
            newComputer.setName(computer.getName());
            newComputer.setMotherboard(computer.getMotherboard());
            newComputer.setOpsystem(computer.getOpsystem());
            newComputer.setRam(computer.getRam());
            newComputer.setRam_quantity(computer.getRam_quantity());
            newComputer.setStorage(computer.getStorage());
            newComputer.setPowerSupply(computer.getPowerSupply());
            newComputer.setVideocard(computer.getVideocard());
            computerRepo.save(newComputer);
        }catch(Exception e){
            model.addAttribute("errMsg", String.format("Hiba történt lekérdezés közben: %s", e.getLocalizedMessage()));
            return "upload";
        }
        return "redirect:/";
    }
}
