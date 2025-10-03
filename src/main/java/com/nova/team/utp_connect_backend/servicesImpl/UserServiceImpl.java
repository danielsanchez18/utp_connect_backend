package com.nova.team.utp_connect_backend.servicesImpl;

import com.nova.team.utp_connect_backend.enities.Role;
import com.nova.team.utp_connect_backend.enities.User;
import com.nova.team.utp_connect_backend.repositories.UserRepository;
import com.nova.team.utp_connect_backend.services.FileStorageService;
import com.nova.team.utp_connect_backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Override
    public User create(User user, MultipartFile image) {

        // Asignar el rol "Estudiante" manualmente
        Role studentRole = new Role();
        studentRole.setId(1L);
        studentRole.setName("Estudiante");
        user.setRoles(List.of(studentRole));

        // Si la imagen no está vacía, almacenamos la imagen
        if (image != null && !image.isEmpty()) {
            String imagePath = fileStorageService.storeImage(image, "IMG_users");
            user.setProfilePicture("static/" + imagePath);  // Asignamos la ruta de la imagen del usuario
        } else {
            // Si no se recibe imagen, el perfil queda sin imagen
            user.setProfilePicture(null);
        }

        return userRepository.save(user);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return userRepository.findById(id);
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User update(UUID id, User user, MultipartFile image) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User updatedUser = existingUser.get();

            // update basic info
            updatedUser.setName(user.getName());
            updatedUser.setLastName(user.getLastName());
            updatedUser.setEmail(user.getEmail());
            updatedUser.setPassword(user.getPassword());
            updatedUser.setPhone(user.getPhone());

            // update profile info
            updatedUser.setUrlWebsite(user.getUrlWebsite());
            updatedUser.setPresentation(user.getPresentation());
            updatedUser.setSkills(user.getSkills());
            updatedUser.setInterests(user.getInterests());

            // Si se proporciona una nueva imagen
            if (image != null && !image.isEmpty()) {
                // Elimina la imagen anterior si existe
                if (user.getProfilePicture() != null) {
                    String oldFileName = user.getPresentation().replace("IMG_users/", "");
                    fileStorageService.deleteImage(oldFileName, "IMG_users");
                }

                // Guarda la nueva imagen
                String imagePath = fileStorageService.storeImage(image, "IMG_users");
                user.setProfilePicture("static/" + imagePath);
            }

            return userRepository.save(updatedUser);
        }
        return userRepository.save(user);
    }
}
