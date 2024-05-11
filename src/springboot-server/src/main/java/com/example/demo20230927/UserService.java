package com.example.demo20230927;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.List;


@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;
    private TaskMapper taskMapper;

    public int register(User user) {
        User oldUser = userMapper.findByUsername(user.getUsername());
        if(oldUser != null){
            return 2; //代码2代表该用户名已经存在
        } else if (user.getPassword().length() < 6 ||
                user.getPassword().length() > 16 ||
                user.getPassword().contains(" ")) {
            return 3; //代码3代表密码长度问题
        } else if (user.getUsername().length() == 0 ||
                user.getUsername().length() > 15 ||
                user.getUsername().contains(" ") ||
                user.getUsername().contains("/") ){
            return 4; //代码4代表用户名长度问题
        }
        user.setPassword(getSHA256(user.getPassword()));
        userMapper.insertUser(user);
        return 1;
    }

    public User login(String username, String password) {
        User user = userMapper.findByUsername(username);
        if (user != null && getSHA256(password).equals(user.getPassword())) { // 实际应用中应使用加密的密码比较
            return user;
        }
        return null;
    }

    public List<Task> getTasksByUser(String username) {
        User user = userMapper.findByUsername(username); // 确保你有这个方法
        if (user != null) {
            return taskMapper.findAllTasksByUserId(user.getId());
        }
        return Collections.emptyList();
    }

    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    public static String getSHA256(String input) {
        input = input + "salt";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] messageDigest = md.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


}
