package apap.tutorial.belajarbelajar.rest;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CourseDetail {
    private String status;

    @JsonProperty("course-license")
    private Integer courseLicense;

    @JsonProperty("valid-until")
    private Date validUntil;
}
