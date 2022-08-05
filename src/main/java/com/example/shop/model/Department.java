package com.example.shop.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "departments", uniqueConstraints = @UniqueConstraint(name = "department_name_uniq", columnNames = "name"))
public class Department {
    @Id
    @SequenceGenerator(name = "department_sequence", sequenceName = "department_sequence", allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "department_sequence")
    private Long id;
    @Column(nullable = false)
    private String name;
    @Enumerated(EnumType.STRING)
    private Location location;
    @OneToMany(fetch = FetchType.EAGER)
    private List<User> users = new ArrayList<>();
    @Column(name = "is_deleted")
    private boolean isDeleted;

}
