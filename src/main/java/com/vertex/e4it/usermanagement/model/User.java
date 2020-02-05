package com.vertex.e4it.usermanagement.model;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "users")
public class User {

    public final static User EMPTY_USER = User.builder().id(-1).build();

    @ApiModelProperty(notes = "The database generated user ID")
    @NotNull(message = "Id expected to be not null")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ApiModelProperty(notes = "The user`s name. Can`t be null")
    @NotNull
    @Column(name = "user_name")
    private String name;

    @ApiModelProperty(notes = "The user`s surname.")
    @Column(name = "user_surname")
    private String surname;

    @ApiModelProperty(notes = "The user`s language level.")
    @Column(name = "language_level")
    @Enumerated(EnumType.STRING)
    private LanguageLevel languageLevel;

    @ApiModelProperty(notes = "The user`s email. Email is used for authorization user")
    @Email
    @Column(name = "email")
    private String email;

    @ApiModelProperty(notes = "The user`s date of birth.")
    @Column(name = "birthday")
    private LocalDate birthday;

    @ApiModelProperty(notes = "The user`s sex.")
    @Column(name = "sex")
    @Enumerated(EnumType.STRING)
    private Sex sex;

    @ApiModelProperty(notes = "The link on user`s profile photo.")
    @Column(name = "photo_link")
    private String photoLink;

    @ApiModelProperty(notes = "The user`s account confirmation status.")
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private UserStatus status;
}
