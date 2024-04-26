package com.example.demo20230927;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;



    @GetMapping("/getTasks")
    public ResponseEntity<List<Task>> getTasks(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring("Bearer ".length());
        String username = JwtUtil.getUsernameFromToken(token);
        User user = userService.findByUsername(username); // 确保UserService有这个方法

        if (user != null) {
            List<Task> tasks = taskService.getTasksByUserId(user.getId());
            return ResponseEntity.ok(tasks);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/addTask")
    public ResponseEntity<Task> addTask(@RequestBody Task task, HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body(null); // 简化处理，实际应用中应更细致地处理错误
        }

        String token = authorizationHeader.substring(7); // 去掉"Bearer "
        String username = JwtUtil.getUsernameFromToken(token); // 从token中解析userId

        task.setUserId(userService.findByUsername(username).getId()); // 设置userId到task

        Task savedTask = taskService.addTask(task); // 保存task

        return ResponseEntity.ok(savedTask);
    }

    @PutMapping("/editTask/{taskId}")
    public ResponseEntity<Task> updateTask(@PathVariable Long taskId, @RequestBody Task task) {
        task.setTaskId(taskId); // 确保taskId被设置
        taskService.updateTask(task);
        return ResponseEntity.ok(task);
    }

    @DeleteMapping("/deleteTask/{taskId}")
    public Boolean deleteTask(@PathVariable Long taskId) {
        taskService.deleteTaskById(taskId);
        return true;
    }
    @PatchMapping("/patchTask/{taskId}")
    public ResponseEntity<Task> patchTask(@PathVariable Long taskId, @RequestBody Task task) {
        task.setTaskId(taskId);
        taskService.updateTask(task);
        return ResponseEntity.ok(task);
    }
}
