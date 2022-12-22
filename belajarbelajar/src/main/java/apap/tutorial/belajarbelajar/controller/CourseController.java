package apap.tutorial.belajarbelajar.controller;

import apap.tutorial.belajarbelajar.model.CourseModel;
import apap.tutorial.belajarbelajar.model.PengajarModel;
import apap.tutorial.belajarbelajar.model.PenyelenggaraModel;
import apap.tutorial.belajarbelajar.service.CourseService;
import apap.tutorial.belajarbelajar.service.PenyelenggaraService;
import apap.tutorial.belajarbelajar.service.PengajarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CourseController {
    @Qualifier("courseServiceImpl")
    @Autowired
    private CourseService courseService;
    @Qualifier("penyelenggaraServiceImpl")
    @Autowired
    private PenyelenggaraService penyelenggaraService;

    @Qualifier("pengajarServiceImpl")
    @Autowired
    private PengajarService pengajarService;

    @GetMapping("/course/add")
    public String addCourseFormPage(Model model) {
        CourseModel course = new CourseModel();
        List<PenyelenggaraModel> listPenyelenggara = penyelenggaraService.getListPenyelenggara();
        List<PenyelenggaraModel> listPenyelenggaraNew = new ArrayList<>();

        course.setListPenyelenggara(listPenyelenggaraNew);
        course.getListPenyelenggara().add(new PenyelenggaraModel());

        model.addAttribute("course", new CourseModel());
        model.addAttribute("listPenyelenggaraExisting", listPenyelenggara);
        return "form-add-course";
    }

    @PostMapping("/course/add")
    public String addCourseSubmitPage(@ModelAttribute CourseModel course, Model model) {
        if (course.getListPenyelenggara() == null) {
            course.setListPenyelenggara(new ArrayList<>());
        }
        courseService.addCourse(course);
        System.out.println(course.getCode());
        model.addAttribute("code", course.getCode());

        return "add-course";
    }

    @GetMapping("/course/viewall")
    public String listCourse(Model model) {
        List<CourseModel> listCourse = courseService.getListCourse();
        model.addAttribute("listCourse", listCourse);
        return "viewall-course";
    }

    @GetMapping("/course/view")
    public String viewDetailCoursePage(@RequestParam(value = "code") String code, Model model) {
        CourseModel course = courseService.getCourseByCodeCourse(code);
        List<PengajarModel> listPengajar = course.getListPengajar();
        Boolean cekCourse = courseService.courseClosed(course);
        Boolean cekPengajar = listPengajar.isEmpty();

        if (cekPengajar){
            cekCourse = !cekPengajar;
        }
        model.addAttribute("listPengajar", listPengajar);
        model.addAttribute("course", course);
        model.addAttribute("cekCourse", cekCourse);
        model.addAttribute("cekPengajar", cekPengajar);
        List<PenyelenggaraModel> listPenyelenggara = course.getListPenyelenggara();
        model.addAttribute("listPenyelenggara", listPenyelenggara);
        return "view-course";
    }

    @GetMapping("course/view-query")
    public String viewDetailCoursePageQuery(@RequestParam(value = "code") String code, Model model) {
        CourseModel course = courseService.getCourseByCodeCourseQuery(code);
        List<PengajarModel> listPengajar = course.getListPengajar();
        List<PenyelenggaraModel> listPenyelenggara = course.getListPenyelenggara();
        model.addAttribute("listPengajar", listPengajar);
        model.addAttribute("course", course);
        model.addAttribute("listPenyelenggara", listPenyelenggara);
        return "view-course";
    }

    @GetMapping("course/update/{code}")
    public String updateCourseFormPage(@PathVariable String code, Model model) {
        CourseModel course = courseService.getCourseByCodeCourse(code);
        model.addAttribute("course", course);

        return "form-update-course";
    }

    @PostMapping("course/update")
    public String updateCourseSubmitPage(@ModelAttribute CourseModel course, Model model) {
        CourseModel updatedCourse = courseService.updateCourse(course);
        if (updatedCourse == null) {
            return "error-update";
        } else {
            model.addAttribute("code", updatedCourse.getCode());
            return "update-course";
        }
    }

    @GetMapping("/course/delete/{code}")
    public String deleteCourse(@PathVariable(value = "code") String code, Model model) {
        CourseModel course = courseService.getCourseByCodeCourse(code);
        boolean courseClosed = courseService.isClosed(course.getTanggalDimulai(), course.getTanggalBerakhir());
        if (courseClosed){
            courseService.deleteCourse(course);
            model.addAttribute("course", course);
            return "delete-course";
        }
        else return "delete-no-code";
    }

    @GetMapping("/course/view-all-sort")
    public String listCourseSort(Model model) {
        List<CourseModel> listCourse1 = courseService.getListCourse();
        model.addAttribute("listCourse", listCourse1);
        return "viewall-course";
    }

    @PostMapping(value = "/course/add", params = {"addRow"})
    private String addRowPenyelenggaraMultiple(
            @ModelAttribute CourseModel course,
            Model model
    ){
        if (course.getListPenyelenggara() == null || course.getListPenyelenggara().size() == 0) {
            course.setListPenyelenggara(new ArrayList<>());
        }
        course.getListPenyelenggara().add(new PenyelenggaraModel());
        List<PenyelenggaraModel> listPenyelenggara = penyelenggaraService.getListPenyelenggara();

        model.addAttribute("course", course);
        model.addAttribute("listPenyelenggaraExisting", listPenyelenggara);

        return "form-add-course";
    }

    @PostMapping(value = "/course/add", params = {"deleteRow"})
    private String deleteRowPenyelenggaraMultiple(
            @ModelAttribute CourseModel course,
            @RequestParam("deleteRow") Integer row,
            Model model
    ) {
        final Integer rowId = Integer.valueOf(row);
        course.getListPenyelenggara().remove(rowId.intValue());

        List<PenyelenggaraModel> listPenyelenggara = penyelenggaraService.getListPenyelenggara();

        model.addAttribute("course", course);
        model.addAttribute("listPenyelenggaraExisting", listPenyelenggara);

        return "form-add-course";
    }

    @PostMapping(value = "/course/add", params = { "addRowPengajar" })
    private String addRowCoursePengajarMultiple(
            @ModelAttribute CourseModel course,
            Model model) {

        if (course.getListPengajar() == null || course.getListPengajar().size() == 0) {
            course.setListPengajar(new ArrayList<>());
        }

        course.getListPengajar().add(new PengajarModel());
        List<PengajarModel> listPengajar = pengajarService.getListPengajar();
        List<PenyelenggaraModel> listPenyelenggara = penyelenggaraService.getListPenyelenggara();

        model.addAttribute("listPenyelenggaraExisting", listPenyelenggara);
        model.addAttribute("course", course);
        model.addAttribute("listPengajarExisting", listPengajar);

        return "form-add-course";
    }

    @PostMapping(value = "/course/add", params = { "deleteRowPengajar" })
    private String deleteRowCoursePengajarMultiple(
            @ModelAttribute CourseModel course,
            @RequestParam("deleteRowPengajar") Integer row,
            Model model
    ) {
        final Integer rowId = Integer.valueOf(row);
        course.getListPengajar().remove(rowId.intValue());

        List<PengajarModel> listPengajar = pengajarService.getListPengajar();

        model.addAttribute("course", course);
        model.addAttribute("listPengajarExisting", listPengajar);

        return "form-add-course";
    }

    @PostMapping(value = "/course/add", params = { "save" })
    public String addPengajarSubmitPage(@ModelAttribute CourseModel course, Model model) {
        if (course.getListPenyelenggara() == null) {
            course.setListPenyelenggara(new ArrayList<>());
        }
        List<PengajarModel> listPengajar = course.getListPengajar();
        for (PengajarModel pengajar : listPengajar) {
            pengajar.setCourse(course);
        }

        course.setListPengajar(listPengajar);
        courseService.addCourse(course);

        model.addAttribute("code", course.getCode());
        return "add-course";
    }

}