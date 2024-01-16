package ru.kata.spring.boot_security.demo.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true)
    @NotEmpty(message = "Поле \'Никнейм\' не должно быть пустым")
    @Size(min = 4, max = 30, message = "Поле \'Имя пользователя\' должно содержать от 4 до 32 символов")
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

    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public User() {
    }

    public User(String username, byte age, String name, String surname, String email, String password) {
        this.username = username;
        this.age = age;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setUsername(String name) {
        this.username = name;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String roleToString() {
        return roles.stream()
                .map(Role::getNameOnly)
                .sorted((element1, element2) -> element1.compareTo(element2))
                .reduce((x, y) -> x +", " + y)
                .orElse("");
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                '}';
    }
}
