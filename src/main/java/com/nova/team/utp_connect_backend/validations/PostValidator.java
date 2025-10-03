package com.nova.team.utp_connect_backend.validations;

import com.nova.team.utp_connect_backend.enities.Post;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class PostValidator {

    public void validate(Post post) {
        if (!StringUtils.hasText(post.getTitle())) {
            throw new IllegalArgumentException("El título es obligatorio");
        }
        if (!StringUtils.hasText(post.getContent())) {
            throw new IllegalArgumentException("El contenido es obligatorio");
        }
        if (!StringUtils.hasText(post.getType())) {
            throw new IllegalArgumentException("El tipo es obligatorio");
        }
        if (!StringUtils.hasText(post.getPrivacy())) {
            throw new IllegalArgumentException("La privacidad es obligatoria");
        }
        if (!StringUtils.hasText(post.getIsCollaborative())) {
            throw new IllegalArgumentException("El campo colaborativo es obligatorio");
        }
    }
}