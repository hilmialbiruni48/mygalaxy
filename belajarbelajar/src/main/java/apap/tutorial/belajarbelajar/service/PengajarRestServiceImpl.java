package apap.tutorial.belajarbelajar.service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import apap.tutorial.belajarbelajar.model.CourseModel;
import apap.tutorial.belajarbelajar.repository.PengajarDb;
//import apap.tutorial.belajarbelajar.rest.pengajarDetail;
import apap.tutorial.belajarbelajar.rest.GenderizeDetail;
import apap.tutorial.belajarbelajar.rest.Setting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import apap.tutorial.belajarbelajar.repository.PengajarDb;
import apap.tutorial.belajarbelajar.model.PengajarModel;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class PengajarRestServiceImpl implements PengajarRestService{

    private final WebClient webClient;

    public PengajarRestServiceImpl(WebClient.Builder webClientBuilder){
        this.webClient = webClientBuilder.baseUrl(Setting.genderizeUrl).build();
    }

    @Autowired
    private PengajarDb pengajarDb;

    @Override
    public PengajarModel createPengajar(PengajarModel pengajar){
        return pengajarDb.save(pengajar);
    }
    @Override
    public List<PengajarModel> retrieveListPengajar(){
        return pengajarDb.findAll();
    }

    @Override
    public PengajarModel getPengajarByNoPengajar(Long noPengajar){
        Optional<PengajarModel> pengajar = pengajarDb.findByNoPengajar(noPengajar);
        if (pengajar.isPresent()){
            return pengajar.get();
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public PengajarModel updatePengajar(Long noPengajar, PengajarModel pengajarUpdate){
        PengajarModel pengajar = getPengajarByNoPengajar(noPengajar);
        pengajar.setNamaPengajar(pengajarUpdate.getNamaPengajar());
        pengajar.setIsPengajarUniversitas(pengajarUpdate.getIsPengajarUniversitas());
        pengajar.setCourse(pengajarUpdate.getCourse());
        return pengajarDb.save(pengajar);
    }
    @Override
    public void deletePengajar(Long noPengajar){
        PengajarModel pengajar = getPengajarByNoPengajar(noPengajar);
        CourseModel course = pengajar.getCourse();
        if (isClosed(course.getTanggalDimulai(), course.getTanggalBerakhir())){
            pengajarDb.delete(pengajar);
        } else{
            throw new UnsupportedOperationException();
        }
    }

    public boolean isClosed(LocalDateTime tanggalDimulai, LocalDateTime tanggalBerakhir){
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(tanggalDimulai) || now.isAfter(tanggalBerakhir)){
            return true;
        }
        return false;
    }

    @Override
    public PengajarModel setGenderPengajar(Long noPengajar) {
        PengajarModel pengajar = getPengajarByNoPengajar(noPengajar);
        CourseModel course = pengajar.getCourse();
        if (!isClosed(course.getTanggalDimulai(), course.getTanggalBerakhir())) { throw new UnsupportedOperationException();}
        String firstName = pengajar.getNamaPengajar().split(" ")[0];
        Mono<GenderizeDetail> formatAPI = this.webClient.get().uri("/?name=" + firstName).retrieve().bodyToMono(GenderizeDetail.class);

        boolean booleanGender = formatAPI.block().getGender().equals("female");
        pengajar.setGenderPengajar(booleanGender);
        updatePengajar(noPengajar, pengajar);

        return getPengajarByNoPengajar(noPengajar);
    }

//    @Override
//    public Mono<String> getStatus(String code) {
//        return this.webClient.get().uri("/rest/pengajar/" + code + "/status")
//                .retrieve()
//                .bodyToMono(String.class);
//    }
//
//    @Override
//    public Mono<pengajarDetail> postStatus() {
//        MultiValueMap<String, String> data = new LinkedMultiValueMap();
//        data.add("code", "APAP");
//        data.add("namepengajar", "Arsitektur PAP");
//        return this.webClient.post().uri("/rest/pengajar/full")
//                .syncBody(data)
//                .retrieve()
//                .bodyToMono(pengajarDetail.class);
//    }
}

