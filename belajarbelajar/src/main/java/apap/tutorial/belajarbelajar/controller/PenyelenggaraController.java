package apap.tutorial.belajarbelajar.controller;

import apap.tutorial.belajarbelajar.model.PenyelenggaraModel;
import apap.tutorial.belajarbelajar.service.PenyelenggaraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PenyelenggaraController {

    @Qualifier("penyelenggaraServiceImpl")
    @Autowired
    PenyelenggaraService penyelenggaraService;

    @GetMapping("/penyelenggara/add")
    public String addPenyelenggaraForm(Model model){
        model.addAttribute("penyelenggara", new PenyelenggaraModel());
        return "form-add-penyelenggara";
    }

    @PostMapping(value = "/penyelenggara/add")
    public String addPenyelenggaraSubmit(
            @ModelAttribute PenyelenggaraModel penyelenggara,
            Model model
    ){

        penyelenggaraService.addPenyelenggara(penyelenggara);
        model.addAttribute("noPenyelenggara", penyelenggara.getNoPenyelenggara());
        return "add-penyelenggara";
    }

    @GetMapping("/penyelenggara/viewall")
    public String viewAllPenyelenggara(
            Model model
    ){
        model.addAttribute("listPenyelenggara", penyelenggaraService.getListPenyelenggara());
        return "viewall-penyelenggara";
    }
}