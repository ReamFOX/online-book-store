package com.farion.onlinebookstore.entity;

import com.farion.onlinebookstore.util.ParameterNames;
import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Basic(optional = false)
    private RoleName name;

    @Override
    public String getAuthority() {
        return ParameterNames.ROLE + this.getName().name();
    }

    public enum RoleName {
        USER,
        ADMIN
    }
}
