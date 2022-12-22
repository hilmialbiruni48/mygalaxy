package apap.tutorial.belajarbelajar.rest;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class GenderizeDetail {
    private String status;

    @JsonProperty("gender")
    private String gender;

    @JsonProperty("probability")
    private Double probability;

    @JsonProperty("count")
    private Integer count;
}