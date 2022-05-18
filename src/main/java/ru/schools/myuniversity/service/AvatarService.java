package ru.schools.myuniversity.service;

import org.springframework.web.multipart.MultipartFile;
import ru.schools.myuniversity.model.Avatar;

import java.io.IOException;

public interface AvatarService {

    void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException;

    Avatar findAvatar(Long id);
}
