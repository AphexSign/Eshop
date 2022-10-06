package ru.yarm.eshop5.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    private static final String SEQ_NAME = "user_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = 1)
    private Long id;


    @NotEmpty(message = "Логин не должен быть пустым")
    @Size(min = 2, max = 100, message = "Логин должен быть от 2 до 100 символов длиной")
    @Column(name = "name")
    private String name;

    @NotEmpty(message = "ФИО не должно быть пустым")
    @Size(min = 2, max = 100, message = "ФИО должно быть от 2 до 100 символов длиной")
    @Column(name = "fio")
    private String fio;


    @NotEmpty(message = "Пароль не должен быть пустым")
    @Size(min = 3, max = 100, message = "Пароль не может быть меньше 3-х символов")
    @Column(name = "password")
    private String password;

    @NotEmpty(message = "Адрес эл.почты не может быть пустым")
    @Email
    @Column(name = "email")
    private String email;

    @NotEmpty(message = "Адрес для доставки товара не может быть пустым")
    @Column(name = "address")
    private String address;

    @Size(min = 5, max = 12, message = "Номер телефона должен содержать не менее 5 и не более 12 цифр")
    @Column(name = "telephone")
    private String telephone;

    private boolean archive;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    private Cart cart;

}