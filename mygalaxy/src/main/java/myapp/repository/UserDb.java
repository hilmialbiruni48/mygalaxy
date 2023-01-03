package myapp.repository;

import myapp.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDb extends JpaRepository<UserModel,Long> {
    UserModel findByUsername(String username);
    Optional<UserModel> findById(String id);
}
