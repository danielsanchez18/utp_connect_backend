package com.nova.team.utp_connect_backend.servicesImpl;

import com.nova.team.utp_connect_backend.enities.Post;
import com.nova.team.utp_connect_backend.repositories.PostRepository;
import com.nova.team.utp_connect_backend.services.FileStorageService;
import com.nova.team.utp_connect_backend.services.PostService;
import com.nova.team.utp_connect_backend.validations.PostValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostValidator postValidation;

    @Autowired
    private FileStorageService fileStorageService;

    @Override
    public Post create(Post post, List<MultipartFile> mediaFiles) {
        postValidation.validate(post);
        if (mediaFiles != null && !mediaFiles.isEmpty()) {
            List<String> urls = new ArrayList<>();
            for (MultipartFile file : mediaFiles) {
                String path = fileStorageService.storeImage(file, "IMG_posts");
                urls.add("static/" + path);
            }
            post.setMediaUrls(urls);
        }
        System.out.println(post);
        return postRepository.save(post);
    }

    @Override
    public Optional<Post> findById(UUID id) {
        return postRepository.findById(id);
    }

    @Override
    public Page<Post> findAll(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @Override
    public Post update(UUID id, Post post, List<MultipartFile> mediaFiles) {
        Post existing = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post no encontrado"));
        postValidation.validate(post);

        // Actualiza los campos básicos
        existing.setTitle(post.getTitle());
        existing.setContent(post.getContent());
        existing.setType(post.getType());
        existing.setPrivacy(post.getPrivacy());
        existing.setIsCollaborative(post.getIsCollaborative());
        existing.setHashtags(post.getHashtags());

        // Si se proporcionan nuevos archivos, reemplaza los anteriores
        if (mediaFiles != null && !mediaFiles.isEmpty()) {
            // Aquí podrías eliminar los archivos antiguos si lo deseas
            List<String> urls = new ArrayList<>();
            for (MultipartFile file : mediaFiles) {
                String path = fileStorageService.storeImage(file, "IMG_posts");
                urls.add("static/" + path);
            }
            existing.setMediaUrls(urls);
        }

        return postRepository.save(existing);
    }

    @Override
    public void delete(UUID id) {
        postRepository.deleteById(id);
    }
}