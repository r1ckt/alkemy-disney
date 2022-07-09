package com.alkemy.disney.entity;

import com.alkemy.disney.dto.GenreDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;

@Entity
@Table(name = "movies")
@SQLDelete(sql="UPDATE movie SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
@Getter
@Setter
public class MovieEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "movie_image")
    private String image;

    @Column(name = "movie_title")
    private String title;

    @Column(name = "movie_creation_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate creationDate;

    @Column(name = "movie_rate")
    private Integer rate;

    @ManyToOne(fetch = LAZY, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinColumn(name = "genre_id", insertable = false, updatable = false)
    private GenreEntity genre;

    @Column(name = "genre_id", nullable = false)
    private Long genreId;

    private boolean deleted = Boolean.FALSE;

    @ManyToMany(cascade = { PERSIST, MERGE })
    @JoinTable(name = "movie_characters",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "character_id")
    )
    private Set<CharacterEntity> characters = new HashSet<>();


    public void addCharacter(CharacterEntity characterEntity){
        characters.add(characterEntity);
    }

    public void removeCharacter(CharacterEntity characterEntity) { characters.remove(characterEntity); }

}
