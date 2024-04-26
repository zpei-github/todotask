package com.example.demo20230927;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String home() {
        return "Hello World";
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        int code = userService.register(user);
        if(code == 2){
            return ResponseEntity.internalServerError().body("用户已经存在");
        } else if (code == 3) {
            return ResponseEntity.internalServerError().body("密码不符合要求");
        }else if (code == 4){
            return ResponseEntity.internalServerError().body("用户名不符合要求");
        }
        return ResponseEntity.ok().body("注册成功");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        User loginUser = userService.login(user.getUsername(), user.getPassword());
        if (loginUser != null) {
            String token = JwtUtil.generateToken(user.getUsername());
            return ResponseEntity.ok(token); // 返回生成的token
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("用户名或密码错误");
        }
    }


}