package ru.kata.spring.boot_security.demo.service;

import org.springframework.ui.Model;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    User getUserById(Long id);
    User getUserByName(String name);
    List<User> getAllUsers();
    void saveUser(User user, String userRole, String adminRole);
    void deleteUser(Long id);
    void updateUser(User user, Long id, String userRole, String adminRole);
    void getModelRoles(Long id, Model model);
}
