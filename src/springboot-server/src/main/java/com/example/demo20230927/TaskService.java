package com.example.demo20230927;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;


// TaskService.java
@Service
public class TaskService {

    @Autowired
    private TaskMapper taskMapper;

    public List<Task> getTasksByUserId(Long userId) {
        return taskMapper.findAllTasksByUserId(userId);
    }

    public Task addTask(@NotNull Task task) {
        task.setCreatedAt(new Date());
        task.setIsCompleted(false);
        task.setIsDeleted(false);
        taskMapper.insertTask(task);
        return task; // 假设task的ID在插入时被自动设置
    }

    public void updateTask(Task task) {
        taskMapper.updateTask(task);
    }

    public void deleteTaskById(Long taskId) {
        taskMapper.softDeleteTask(taskId);
    }

    public Task getTaskById(Long taskId) {
        return taskMapper.findTaskById(taskId);
    }

    public static String getUserNameFromToken(String token) {
        return token.substring(7);
    }
}
