package com.nova.team.utp_connect_backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nova.team.utp_connect_backend.enities.User;
import com.nova.team.utp_connect_backend.services.UserService;
import com.nova.team.utp_connect_backend.validations.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> create(
            @RequestPart("user") String userJson,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) throws IOException {
        ObjectMapper om = new ObjectMapper();
        User user = om.readValue(userJson, User.class);

        try {
            User createdUser = userService.create(user, image);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ResponseUtil.successResponse("Usuario creado exitosamente", createdUser));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseUtil.errorResponse(ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtil.errorResponse("Error al crear el usuario"));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        try {
            Optional<User> user = userService.findById(id);
            return ResponseEntity.ok(ResponseUtil.successResponse("Usuario encontrado", user));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseUtil.errorResponse(ex.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> getAll(@PageableDefault(size = 10, page = 0) Pageable pageable) {
        try {
            Page<User> users = userService.findAll(pageable);
            return ResponseEntity.ok(ResponseUtil.successResponse("Usuarios encontrados exitosamente", users));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseUtil.errorResponse("Error al buscar los usuarios"));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable("id") UUID id,
            @RequestPart("user") String userJson,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) throws IOException {
        ObjectMapper om = new ObjectMapper();
        User user = om.readValue(userJson, User.class);

        try {
            User updatedUser = userService.update(id, user, image);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ResponseUtil.successResponse("Usuario actualizado exitosamente", updatedUser));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseUtil.errorResponse(ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtil.errorResponse("Error al actualizar el usuario"));
        }
    }

}
