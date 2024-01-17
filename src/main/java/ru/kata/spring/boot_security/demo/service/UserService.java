package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    User getUserById(Long id);
    User getUserByUsername(String username);
    List<User> getAllUsers();
    void saveUser(User user);
    void updateUser(Long id, User user);
    void deleteUser(Long id);
}
