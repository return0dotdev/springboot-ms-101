package com.return0.dev.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "m_user")
public class User extends BaseEntity implements Serializable {

    @Column(nullable = false, unique = true, length = 60)
    private String email;

    @Column(nullable = false, unique = false, length = 120)
    private String password;

    @Column(nullable = false, length = 120)
    private String name;


    private String civilId;

    @Column
    private String token;
    @Column
    private Date tokenExp;

    @Column
    private boolean activated;

    @OneToOne(mappedBy = "user", orphanRemoval = true)
    private Social social;

    @OneToMany(mappedBy = "user", orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Address> addresses;


}
