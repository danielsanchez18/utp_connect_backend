package com.nova.team.utp_connect_backend.repositories;

import com.nova.team.utp_connect_backend.enities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {
}