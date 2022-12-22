package apap.tutorial.belajarbelajar.controller;

import apap.tutorial.belajarbelajar.model.CourseModel;
import apap.tutorial.belajarbelajar.model.PengajarModel;
import apap.tutorial.belajarbelajar.service.CourseService;
import apap.tutorial.belajarbelajar.service.PengajarService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class    PengajarController {
    @Qualifier("pengajarServiceImpl")
    @Autowired
    private PengajarService pengajarService;

    @Qualifier("courseServiceImpl")
    @Autowired
    private CourseService courseService;

    @GetMapping("/pengajar/add/{code}")
    public String addPengajarFormPage(@PathVariable String code, Model model) {
        PengajarModel pengajar = new PengajarModel();
        CourseModel course = courseService.getCourseByCodeCourse(code);
        pengajar.setCourse(course);
        model.addAttribute("pengajar", pengajar);
        return "form-add-pengajar";
    }

    @PostMapping("/pengajar/add")
    public String addPengajarSubmitPage(@ModelAttribute PengajarModel pengajar, Model model) {
        pengajarService.addPengajar(pengajar);
        model.addAttribute("noPengajar", pengajar.getNoPengajar());
        return "add-pengajar";
    }

    //Latihan2
    @GetMapping("pengajar/update/{noPengajar}")
    public String updatePengajarFormPage(@PathVariable(value = "noPengajar") String noPengajar, Model model) {
        PengajarModel pengajar= pengajarService.getPengajarByNoPengajar(Long.parseLong(noPengajar));
        model.addAttribute("pengajar", pengajar);
        return "form-update-pengajar";
    }

    @PostMapping("/pengajar/delete")
    public String deletePengajarSubmit(
            @ModelAttribute CourseModel course,
            Model model
    ){
        if(courseService.isClosed(course.getTanggalDimulai(), course.getTanggalBerakhir())) {
            for (PengajarModel pengajar : course.getListPengajar()) {
                pengajarService.deletePengajar(pengajar);
            }
            model.addAttribute("code", course.getCode());
            return "delete-pengajar";
        }
        return "gagal-delete-pengajar";
    }

    @PostMapping("pengajar/update")
    public String updatePengajarSubmitPage(@ModelAttribute PengajarModel pengajar, Model model) {
        PengajarModel updatePengajar = pengajarService.updatePengajar(pengajar);
        model.addAttribute("pengajar", updatePengajar.getNamaPengajar());
        return "update-pengajar";
    }

    @GetMapping("pengajar/error")
    public String errorPagePengajar() {
        return "delete-pengajar-error";
    }

    @GetMapping("pengajar/errorUpdate")
    public String errorUpdatePengajar() {
        return "update-pengajar-error";
    }

}