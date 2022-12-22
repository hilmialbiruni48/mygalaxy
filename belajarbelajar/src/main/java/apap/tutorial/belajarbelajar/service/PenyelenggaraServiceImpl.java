package apap.tutorial.belajarbelajar.service;

import apap.tutorial.belajarbelajar.model.PenyelenggaraModel;
import apap.tutorial.belajarbelajar.repository.PenyelenggaraDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PenyelenggaraServiceImpl implements PenyelenggaraService{
    @Autowired
    PenyelenggaraDb penyelenggaraDb;

    @Override
    public void addPenyelenggara(PenyelenggaraModel penyelenggara) {
        penyelenggaraDb.save(penyelenggara);
    }

    @Override
    public List<PenyelenggaraModel> getListPenyelenggara() {
        return penyelenggaraDb.findAll();
    }
}