package myapp.service;

import myapp.model.RoleModel;
import myapp.repository.RoleDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService{
    @Autowired
    private RoleDb roleDb;

    @Override
    public List<RoleModel> findAll(){
        return roleDb.findAll();
    }

    @Override
    public RoleModel getById(Long id) {
        Optional<RoleModel> role = roleDb.findById(id);
        if(role.isPresent()) {
            return role.get();
        } else return null;
    }


}
