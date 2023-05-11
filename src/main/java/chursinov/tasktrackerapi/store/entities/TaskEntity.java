package chursinov.tasktrackerapi.store.entities;

import chursinov.tasktrackerapi.store.enums.TaskPriorityEnum;
import chursinov.tasktrackerapi.store.enums.TaskStatusEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.Instant;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "task")
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;

    String description;

    @Enumerated(EnumType.STRING)
    TaskStatusEnum taskStatus;

    @Enumerated(EnumType.STRING)
    TaskPriorityEnum taskPriority;

    @Builder.Default
    Instant createdAt = Instant.now();

    @ManyToOne
    @JoinColumn(name = "project_id")
    private ProjectEntity project;

}
