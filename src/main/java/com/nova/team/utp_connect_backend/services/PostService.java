package com.nova.team.utp_connect_backend.services;

import com.nova.team.utp_connect_backend.enities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PostService {

    Post create(Post post, List<MultipartFile> mediaFiles);

    Optional<Post> findById(UUID id);

    Page<Post> findAll(Pageable pageable);

    Post update(UUID id, Post post, List<MultipartFile> mediaFiles);

    void delete(UUID id);
}