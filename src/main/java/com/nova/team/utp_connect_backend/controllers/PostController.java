package com.nova.team.utp_connect_backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nova.team.utp_connect_backend.enities.Post;
import com.nova.team.utp_connect_backend.services.PostService;
import com.nova.team.utp_connect_backend.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity<?> create(
            @RequestPart("post") String postJson,
            @RequestPart(value = "mediaFiles", required = false) List<MultipartFile> mediaFiles
    ) throws IOException {
        ObjectMapper om = new ObjectMapper();
        Post post = om.readValue(postJson, Post.class);

        try {
            Post createdPost = postService.create(post, mediaFiles);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ResponseUtil.successResponse("Post creado exitosamente", createdPost));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseUtil.errorResponse(ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtil.errorResponse("Error al crear el post"));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        try {
            Optional<Post> post = postService.findById(id);
            return ResponseEntity.ok(ResponseUtil.successResponse("Post encontrado", post));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseUtil.errorResponse(ex.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> getAll(@PageableDefault(size = 10, page = 0) Pageable pageable) {
        try {
            Page<Post> posts = postService.findAll(pageable);
            return ResponseEntity.ok(ResponseUtil.successResponse("Posts encontrados exitosamente", posts));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseUtil.errorResponse("Error al buscar los posts"));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable("id") UUID id,
            @RequestPart("post") String postJson,
            @RequestPart(value = "mediaFiles", required = false) List<MultipartFile> mediaFiles
    ) throws IOException {
        ObjectMapper om = new ObjectMapper();
        Post post = om.readValue(postJson, Post.class);

        try {
            Post updatedPost = postService.update(id, post, mediaFiles);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ResponseUtil.successResponse("Post actualizado exitosamente", updatedPost));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseUtil.errorResponse(ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtil.errorResponse("Error al actualizar el post"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        try {
            postService.delete(id);
            return ResponseEntity.ok(ResponseUtil.successResponse("Post eliminado exitosamente", null));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseUtil.errorResponse(ex.getMessage()));
        }
    }
}