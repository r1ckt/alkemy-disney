package com.alkemy.disney.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.*;
import static javax.persistence.GenerationType.*;

@Entity
@Getter
@Setter
@Table(name = "characters")
@SQLDelete(sql="UPDATE character SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
public class CharacterEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "character_image")
    private String image;

    @Column(name = "character_name")
    private String name;

    @Column(name = "character_age")
    private Integer age;

    @Column(name = "character_weight_kg")
    private Double weight;

    @Column(name = "character_history")
    private String history;

    private boolean deleted = Boolean.FALSE;

    @ManyToMany(mappedBy = "characters", cascade = PERSIST)
    private Set<MovieEntity> movies = new HashSet<>();

}