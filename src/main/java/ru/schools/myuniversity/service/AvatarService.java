package ru.schools.myuniversity.service;

import org.springframework.web.multipart.MultipartFile;
import ru.schools.myuniversity.model.Avatar;

import java.io.IOException;
import java.util.List;

public interface AvatarService {

    void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException;

    Avatar findAvatar(Long id);

    List<Avatar> getAvatarsByQuantity(int pageNumber, int pageSize);
}
