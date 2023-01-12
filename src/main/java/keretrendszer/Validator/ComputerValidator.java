package keretrendszer.Validator;

import keretrendszer.Models.Computer;

import javax.xml.bind.ValidationException;

public class ComputerValidator {
    //ComputerValidator - isValid - name.length < 100, motherboard.length < 100, ram_quantity >= 0, ram_id, cpu_id, gpu_id, psu_id, opsystem > 0
    public static void validate(Computer computer) throws ValidationException {
        if(computer.getName().length() > 100)
            throw new ValidationException("A számítógép neve nem lépheti át a 100 karaktert");
        if(computer.getMotherboard().length() > 100)
            throw new ValidationException("Az alaplap neve nem lépheti át a 100 karaktert");
        if(computer.getRam_quantity() < 0)
            throw new ValidationException("A RAMok száma nem lehet negatív!");
        if(computer.getRam().getId() <= 0)
            throw new ValidationException("A RAM indexe nem lehet 0 vagy negatív!");
        if(computer.getProcessor().getId() <= 0)
            throw new ValidationException("A processzor indexe nem lehet 0 vagy negatív!");
        if(computer.getVideocard().getId() <= 0)
            throw new ValidationException("A videokártya indexe nem lehet 0 vagy negatív!");
        if(computer.getPowerSupply().getId() <= 0)
            throw new ValidationException("A  indexe nem lehet 0 vagy negatív!");
        if(computer.getOpsystem().getId() <= 0)
            throw new ValidationException("Operációs rendszer indexe nem lehet 0 vagy negatív!");
    }
}
