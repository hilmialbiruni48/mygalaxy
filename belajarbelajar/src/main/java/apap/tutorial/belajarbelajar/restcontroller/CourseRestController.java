package apap.tutorial.belajarbelajar.restcontroller;

import java.util.List;
import java.util.NoSuchElementException;
import javax.validation.Valid;

import apap.tutorial.belajarbelajar.rest.CourseDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import apap.tutorial.belajarbelajar.model.CourseModel;
import apap.tutorial.belajarbelajar.service.CourseRestService;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1")
public class CourseRestController {
    @Autowired
    private CourseRestService courseRestService;

    @PostMapping(value = "/course/add")
    private CourseModel createCourse(@Valid @RequestBody CourseModel course, BindingResult bindingResult){
        if(bindingResult.hasFieldErrors()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Request body has invalid type or missing field."
            );
        } else {
            return courseRestService.createCourse(course);
        }
    }

    //Retrieve
    @GetMapping(value = "/course/{code}")
    private CourseModel retrieveCourse(@PathVariable("code") String code){
        try {
            return courseRestService.getCourseByCode(code);
        } catch (NoSuchElementException e){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Code course " + code + " not found"
            );
        }
    }

    //Delete
    @DeleteMapping(value = "/course/{code}")
    private ResponseEntity deleteCourse(@PathVariable("code") String code){
        try {
            courseRestService.deleteCourse(code);
            return ResponseEntity.ok("Course with code " + code + " has been deleted succesfully");
        } catch (NoSuchElementException e){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Code course " + code + " not found"
            );
        } catch (UnsupportedOperationException e){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Course is still open or has pengajar"
            );
        }
    }
    //Update
    @PutMapping(value = "/course/{code}")
    private CourseModel updateCourse(@PathVariable("code") String code, @RequestBody CourseModel course) {
        try {
            return courseRestService.updateCourse(code, course);
        } catch (NoSuchElementException e){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Code Course " + code + " not found"
            );
        }
    }
    //Retrieve List All
    @GetMapping(value = "/list-course")
    private List<CourseModel> retrieveListCourse(){
        return courseRestService.retrieveListCourse();
    }

    @GetMapping(value = "/course/{code}/status")
    private Mono<String> getStatus(@PathVariable("code") String code) {
        return courseRestService.getStatus(code);
    }
    @GetMapping(value = "/full")
    private Mono<CourseDetail> postStatus() {
        return courseRestService.postStatus();
    }
}
