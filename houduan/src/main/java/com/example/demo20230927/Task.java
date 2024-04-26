package com.example.demo20230927;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;

    private Long userId;
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    private Boolean isCompleted;

    @Temporal(TemporalType.TIMESTAMP)
    private Date completedAt;

    private Boolean isDeleted;
}
