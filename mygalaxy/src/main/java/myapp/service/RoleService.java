package myapp.service;

import myapp.model.RoleModel;
import java.util.List;

public interface RoleService {
    List<RoleModel> findAll();
    RoleModel getById(Long id);
}
