package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User getUserByName(String name) {
        return userRepository.findByUsername(name);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void saveUser(User user, String userRole, String adminRole) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        if (userRole != null && userRole.equals("USER")) {
            roles.add(roleService.getRoleByName("USER"));
        }
        if (adminRole != null && adminRole.equals("ADMIN")) {
            roles.add(roleService.getRoleByName("ADMIN"));
        }
        user.setRoles(roles);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void updateUser(User user, Long id, String userRole, String adminRole) {
        Set<Role> roles = new HashSet<>();
        if (userRole != null && userRole.equals("USER")) {
            roles.add(roleService.getRoleByName("USER"));
        }
        if (adminRole != null && adminRole.equals("ADMIN")) {
            roles.add(roleService.getRoleByName("ADMIN"));
        }
        user.setRoles(roles);

        User initialUser = userRepository.findById(id).orElse(null);
        if (initialUser != null) {
            initialUser.setUsername(user.getUsername());
            initialUser.setAge(user.getAge());
            initialUser.setName(user.getName());
            initialUser.setSurname(user.getSurname());
            initialUser.setEmail(user.getEmail());
            initialUser.setRoles(user.getRoles());
            if (!user.getPassword().equals(initialUser.getPassword())) {
                initialUser.setPassword(passwordEncoder.encode(user.getPassword()));
            }
        }
        userRepository.save(initialUser);
    }

    @Override
    public void getModelRoles(Long id, Model model) {
        Set<Role> roles = getUserById(id).getRoles();
        for (Role role : roles) {
            if (role.equals(roleService.getRoleByName("ADMIN"))) {
                model.addAttribute("ADMIN", true);
            }
            if (role.equals(roleService.getRoleByName("USER"))) {
                model.addAttribute("USER", true);
            }
        }
    }
}
