package com.nova.team.utp_connect_backend.enities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "posts")
@Data
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String type; // Por ejemplo: "proyecto", "idea" o "propuesta".

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    private List<String> mediaUrls;  // Para imágenes/videos, etc.

    @Column(nullable = false)
    private String privacy;  // Por ejemplo: "público", "privado", "solo amigos", etc.

    @Column(nullable = false)
    private String isCollaborative; // Indica si es colaborativa o no.

    private List<String> hashtags;  // Etiquetas relacionadas con la publicación.

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Report> reports;

    @Embedded
    private Audit audit = new Audit();

}

