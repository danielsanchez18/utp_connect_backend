package com.nova.team.utp_connect_backend.repositories;

import com.nova.team.utp_connect_backend.enities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);  // Buscar usuario por correo electrónico

}