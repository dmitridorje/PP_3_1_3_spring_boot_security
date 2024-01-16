package ru.kata.spring.boot_security.demo.service;

import org.springframework.ui.Model;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(Long id);
    User getUserFromModel(Model model);
    User getUserByName(String name);
    void saveUser(User user);
    void updateUser(long id, User user);
    void deleteUser(Long id);
}
