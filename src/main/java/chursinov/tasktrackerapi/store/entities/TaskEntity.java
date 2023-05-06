package chursinov.tasktrackerapi.store.entities;

import chursinov.tasktrackerapi.store.enums.TaskPriorityEnum;
import chursinov.tasktrackerapi.store.enums.TaskStatusEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.Instant;

import static chursinov.tasktrackerapi.store.enums.TaskPriorityEnum.*;
import static chursinov.tasktrackerapi.store.enums.TaskStatusEnum.*;

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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    String name;

    String description;

    @Builder.Default
    TaskStatusEnum taskStatusEnum = NEW;

    @Builder.Default
    TaskPriorityEnum taskPriority = MEDIUM;

    @Builder.Default
    Instant createdAt = Instant.now();

}
