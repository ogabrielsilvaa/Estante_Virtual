package com.br.estante_virtual.entity;

import com.br.estante_virtual.enums.RoleName;
import jakarta.persistence.*;

@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_name", unique = true)
    private RoleName name;

    /**
     * Construtor exigido pelo JPA.
     */
    public Role() {
    }

    /**
     * Construtor para facilitar a criação de novas roles no código.
     */
    public Role(RoleName name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RoleName getName() {
        return name;
    }

    public void setName(RoleName name) {
        this.name = name;
    }
}
