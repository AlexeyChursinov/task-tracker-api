package chursinov.tasktrackerapi.store.entities;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "project")
public class ProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @Builder.Default
    private Instant updatedAt = Instant.now();

    @Builder.Default
    private Instant createAt = Instant.now();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
    private List<TaskEntity> tasks = new ArrayList<>();
}
