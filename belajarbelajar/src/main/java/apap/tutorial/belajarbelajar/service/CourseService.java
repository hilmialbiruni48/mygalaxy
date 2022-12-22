package apap.tutorial.belajarbelajar.service;
import apap.tutorial.belajarbelajar.model.CourseModel;

import java.time.LocalDateTime;
import java.util.List;

public interface CourseService {

    void addCourse(CourseModel course);

    List<CourseModel> getListCourse();

    CourseModel getCourseByCodeCourse(String code);
    CourseModel getCourseByCodeCourseQuery(String code);
    CourseModel updateCourse(CourseModel course);

    boolean courseClosed(CourseModel course);
    boolean isClosed(LocalDateTime tanggalDimulai, LocalDateTime tanggalBerakhir);
    void deleteCourse(CourseModel course);

}
