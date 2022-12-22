package apap.tutorial.belajarbelajar.service;
import java.util.List;
import apap.tutorial.belajarbelajar.model.CourseModel;
import apap.tutorial.belajarbelajar.rest.CourseDetail;
import reactor.core.publisher.Mono;

public interface CourseRestService {
    CourseModel createCourse(CourseModel course);
    List<CourseModel> retrieveListCourse();
    CourseModel getCourseByCode(String code);
    CourseModel updateCourse(String code, CourseModel courseUpdate);
    void deleteCourse(String code);
    Mono<String> getStatus(String code);
    Mono<CourseDetail> postStatus();
}

