package apap.tutorial.belajarbelajar.repository;

import apap.tutorial.belajarbelajar.model.CourseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseDb extends JpaRepository<CourseModel, String>{
    Optional<CourseModel> findByCode(String code);

    @Query("SELECT c FROM CourseModel c WHERE c.code = :code")
    Optional<CourseModel> findByCodeUsingQuery(@Param("code") String code);

    @Query("SELECT c FROM CourseModel c WHERE c.nameCourse = :nameCourse")
    Optional<CourseModel> findNameCourseByQuery(@Param("nameCourse") String nameCourse);

}
