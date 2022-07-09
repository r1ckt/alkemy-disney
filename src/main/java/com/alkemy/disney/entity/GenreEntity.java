package com.alkemy.disney.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

import static javax.persistence.GenerationType.*;

@Entity
@Table(name = "genre")
@SQLDelete(sql = "UPDATE genre SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
@Getter
@Setter
public class GenreEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "genre_name")
    private String name;

    @Column(name = "genre_image")
    private String image;

    private boolean deleted = Boolean.FALSE;


}
