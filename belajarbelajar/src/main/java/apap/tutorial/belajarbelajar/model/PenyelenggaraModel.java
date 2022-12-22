package apap.tutorial.belajarbelajar.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "penyelenggara")
public class PenyelenggaraModel implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noPenyelenggara;

    @NotNull
    @Size(max = 30)
    @Column(name = "nama_penyelenggara", nullable = false)
    private String namaPenyelenggara;

    @NotNull
    @Column(name = "jenis_lembaga", nullable = false)
    private Integer jenisLembaga;

    @ManyToMany(mappedBy = "listPenyelenggara")
    List<CourseModel> listCourse;
}