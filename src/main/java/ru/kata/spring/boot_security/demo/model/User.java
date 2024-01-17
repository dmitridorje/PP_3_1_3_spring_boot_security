package ru.kata.spring.boot_security.demo.model;


import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Set;

@Entity
@Table(name="users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true)
    @NotEmpty(message = "Поле \'Имя пользователя\' не должно быть пустым")
    @Size(min = 4, max = 32, message = "Поле \'Имя пользователя\' должно содержать от 4 до 32 символов")
    private String username;

    @Column(name = "name")
    @NotEmpty(message = "Поле \'Имя\' не должно быть пустым")
    private String name;

    @Column(name = "surname")
    @NotEmpty(message = "Поле \'Фамилия\' не должно быть пустым")
    private String surname;

    @Column(name = "age")
    @Min(value = 1, message = "Возраст должен быть больше нуля")
    private Byte age;

    @Column(name = "email")
    @NotEmpty(message = "Поле \'Email\' не должно быть пустым")
    @Email(message = "В поле \'Email\' должен быть введён действительный электронный адрес")
    private String email;

    @Column
    @NotNull
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public User() {
    }

    public User(String username, Byte age, String name, String surname, String email, String password) {
        this.username = username;
        this.age = age;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Set<Role> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getPassword() {
        return password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public Byte getAge() {
        return age;
    }

    public void setAge(Byte age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
