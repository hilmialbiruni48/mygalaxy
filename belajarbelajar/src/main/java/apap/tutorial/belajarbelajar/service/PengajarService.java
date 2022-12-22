package apap.tutorial.belajarbelajar.service;
import apap.tutorial.belajarbelajar.model.CourseModel;
import apap.tutorial.belajarbelajar.model.PengajarModel;
import apap.tutorial.belajarbelajar.model.PenyelenggaraModel;

import java.util.List;

public interface PengajarService {
    void addPengajar(PengajarModel pengajar);
    PengajarModel getPengajarByNoPengajar(Long noPengajar);
    PengajarModel updatePengajar(PengajarModel pengajar);
    void deletePengajar(PengajarModel pengajar);
    List<PengajarModel> getListPengajar();
}
