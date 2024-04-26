package com.example.demo20230927;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

// TaskMapper 接口
@Mapper
public interface TaskMapper {
    List<Task> findAllTasksByUserId(Long userId);
    Boolean insertTask(Task task);
    Boolean updateTask(Task task);
    Boolean deleteTaskById(Long taskId);
    Task findTaskById(Long taskId);
    Boolean softDeleteTask(Long taskId);
}
