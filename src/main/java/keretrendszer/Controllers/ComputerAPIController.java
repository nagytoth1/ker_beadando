package keretrendszer.Controllers;

import keretrendszer.Models.Computer;
import keretrendszer.Repositories.ComputerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pc")
public class ComputerAPIController {
    @Autowired
    ComputerRepo repo;
    @PostMapping("/create")
    public ResponseEntity<String> createPC(@RequestBody Computer computer){
        Computer newPC = new Computer();
        try{
            newPC.setComputer(computer);
            repo.save(newPC);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(String.format("Sikeresen hozzáadtad a %s nevű számítógépet!", computer.getName()), HttpStatus.OK);
    }
    @GetMapping("/all")
    public ResponseEntity<List<Computer>> showAll(){
        try{
            return new ResponseEntity<>(repo.findAll(), HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/modify")
    public ResponseEntity<String> modifyPC(@RequestParam long id, @RequestBody Computer computer){
        Optional<Computer> foundPC;
        try{
            foundPC = repo.findById(id);
            if(foundPC.isEmpty())
                return new ResponseEntity<>("PC-t nem találtam ezzel az id-vel", HttpStatus.NOT_FOUND);

            Computer updatedPC = foundPC.get();
            updatedPC.setComputer(computer);
            repo.save(updatedPC);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(String.format("Sikeresen hozzáadtad a %s nevű számítógépet!", computer.getName()), HttpStatus.OK);
    }
}
