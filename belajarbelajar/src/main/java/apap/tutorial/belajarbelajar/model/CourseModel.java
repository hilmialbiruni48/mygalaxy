package apap.tutorial.belajarbelajar.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties(value={"listPenyelenggara"}, allowSetters = true)
@Table(name = "course")
public class CourseModel {
    @Id
    @Size(max = 30)
    private String code;

    @NotNull
    @Size(max = 30)
    @Column(name = "name_course", nullable = false)
    private String nameCourse;

    @NotNull
    @Size(max = 200)
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "jumlah_sks", nullable = false)
    private Integer jumlahSks;

    @NotNull
    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime tanggalDimulai;

    @NotNull
    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime tanggalBerakhir;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PengajarModel> listPengajar;

    @ManyToMany
    @JoinTable(name = "penyelenggara_course", joinColumns = @JoinColumn(name = "code"), inverseJoinColumns = @JoinColumn(name = "no_penyelenggara"))
    List<PenyelenggaraModel> listPenyelenggara;
}