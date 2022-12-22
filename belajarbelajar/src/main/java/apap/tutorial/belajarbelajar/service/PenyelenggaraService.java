package apap.tutorial.belajarbelajar.service;

import apap.tutorial.belajarbelajar.model.PenyelenggaraModel;

import java.util.List;

public interface PenyelenggaraService {
    void addPenyelenggara(PenyelenggaraModel penyelenggara);
    List<PenyelenggaraModel> getListPenyelenggara();
}