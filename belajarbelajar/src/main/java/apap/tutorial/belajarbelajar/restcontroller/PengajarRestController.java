package apap.tutorial.belajarbelajar.restcontroller;

import apap.tutorial.belajarbelajar.model.PengajarModel;
import apap.tutorial.belajarbelajar.rest.CourseDetail;
import apap.tutorial.belajarbelajar.service.CourseRestService;
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
import apap.tutorial.belajarbelajar.service.PengajarRestService;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1")
public class PengajarRestController {
    @Autowired
    private PengajarRestService pengajarRestService;

    @PostMapping(value = "/pengajar")
    private PengajarModel createPengajar(@Valid @RequestBody PengajarModel pengajar, BindingResult bindingResult){
        if(bindingResult.hasFieldErrors()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Request body has invalid type or missing field."
            );
        }
        return pengajarRestService.createPengajar(pengajar);
    }

    //Retrieve
    @GetMapping(value = "/pengajar/{noPengajar}")
    private PengajarModel retrievePengajar(@PathVariable("noPengajar") Long noPengajar){
        try {
            return pengajarRestService.getPengajarByNoPengajar(noPengajar);
        } catch (NoSuchElementException e){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No Pengajar " + noPengajar.toString() + " not found"
            );
        }
    }

    //Delete
    @DeleteMapping(value = "/pengajar/{noPengajar}")
    private ResponseEntity deletePengajar(@PathVariable("noPengajar") Long noPengajar){
        try {
            pengajarRestService.deletePengajar(noPengajar);
            return ResponseEntity.ok("Pengajar sudah terhapus");
        } catch (NoSuchElementException e){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No Pengajar " + noPengajar.toString() + " not found"
            );
        } catch (UnsupportedOperationException e){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Course is still open"
            );
        }
    }
    //Update
    @PutMapping(value = "/pengajar/{noPengajar}")
    private PengajarModel updatePengajar(@PathVariable("noPengajar") Long noPengajar, @Valid @RequestBody PengajarModel pengajar) {
        try {
            return pengajarRestService.updatePengajar(noPengajar, pengajar);
        } catch (NoSuchElementException e){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No Pengajar " + noPengajar.toString() + " not found"
            );
        }
    }
    //Retrieve List All
    @GetMapping(value = "/list-pengajar")
    private List<PengajarModel> retrieveListPengajar(){
        return pengajarRestService.retrieveListPengajar();
    }

    //Set Gender
    @GetMapping("/pengajar/jenis-kelamin/{noPengajar}")
    private PengajarModel setGenderPengajar(@PathVariable("noPengajar") Long noPengajar) {
        try {
            return pengajarRestService.setGenderPengajar(noPengajar);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No pengajar" + noPengajar.toString() + " not found"
            );
        } catch (UnsupportedOperationException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Course is still open"
            );
        }
    }
//    @GetMapping(value = "/pengajar/{code}/status")
//    private Mono<String> getStatus(@PathVariable("code") String code) {
//        return courseRestService.getStatus(code);
//    }
//    @GetMapping(value = "/full")
//    private Mono<CourseDetail> postStatus() {
//        return courseRestService.postStatus();
//    }
}