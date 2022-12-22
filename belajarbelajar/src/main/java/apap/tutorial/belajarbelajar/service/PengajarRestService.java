package apap.tutorial.belajarbelajar.service;
import java.util.List;
import apap.tutorial.belajarbelajar.model.PengajarModel;
//import apap.tutorial.belajarbelajar.rest.CourseDetail;
//import reactor.core.publisher.Mono;

public interface PengajarRestService {
    PengajarModel createPengajar(PengajarModel pengajar);
    List<PengajarModel> retrieveListPengajar();
    PengajarModel getPengajarByNoPengajar(Long noPengajar);
    PengajarModel updatePengajar(Long noPengajar, PengajarModel pengajarUpdate);
    void deletePengajar(Long noPengajar);
    PengajarModel setGenderPengajar(Long noPengajar);

//    Mono<String> getStatus(Long noPengajar);
//    Mono<CourseDetail> postStatus();
}

