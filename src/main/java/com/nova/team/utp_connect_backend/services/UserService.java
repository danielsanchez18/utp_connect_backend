package com.nova.team.utp_connect_backend.services;

import com.nova.team.utp_connect_backend.enities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

public interface UserService {

    User create(User user, MultipartFile image);

    Optional<User> findById(UUID id);

    Page<User> findAll(Pageable pageable);

    Optional<User> findByEmail(String email);

    User update(UUID id, User user, MultipartFile image);

}

