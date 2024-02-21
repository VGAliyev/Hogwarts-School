package ru.hogwarts.school.service.Impl;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.AvatarService;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarServiceImpl implements AvatarService {
    @Value("${path.to.avatars.folder}")
    private String avatarsDir;

    private final AvatarRepository avatarRepository;
    private final StudentRepository studentRepository;

    private final Logger logger = LoggerFactory.getLogger(AvatarServiceImpl.class);

    public AvatarServiceImpl(StudentRepository studentRepository, AvatarRepository avatarRepository) {
        this.avatarRepository = avatarRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException {
        logger.info("Was invoked method upload avatar");
        Student student = studentRepository.findById(studentId).orElseThrow();
        Path filePath = Path.of(avatarsDir,
                student + "." + getExtension(avatarFile.getOriginalFilename()));

        saveToFile(avatarFile, filePath);

        saveToDB(studentId, avatarFile, student, filePath);
        logger.debug("Avatar uploaded");
    }

    @Override
    public Avatar findAvatar(Long studentId) {
        logger.info("Was invoked method find avatar by id {}", studentId);
        return avatarRepository.findByStudentId(studentId).orElseGet(Avatar::new);
    }

    @Override
    public List<Avatar> findAll(Integer pageNumber, Integer pageSize) {
        logger.debug("Find all avatar with pagination: pageNumber = {}, pageSize = {}", pageNumber, pageSize);
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        return avatarRepository.findAll(pageRequest).getContent();
    }

    private String getExtension(String originalFilename) {
        logger.debug("Get extension of file {}", originalFilename);
        return originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
    }

    private void saveToDB(Long studentId, MultipartFile avatarFile, Student student, Path filePath) throws IOException {
        logger.debug("Save avatar to database");
        Avatar avatar = findAvatar(studentId);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(generateDataForDB(filePath));

        avatarRepository.save(avatar);
    }

    private void saveToFile(MultipartFile avatarFile, Path filePath) throws IOException {
        logger.debug("Save avatar to file {}", filePath.getFileName());
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (
                InputStream is = avatarFile.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
        ) {
            bis.transferTo(bos);
        }
    }

    private byte[] generateDataForDB(Path filePath) throws IOException {
        logger.debug("Generate data for database from file {}", filePath.getFileName());
        try (
                InputStream is = Files.newInputStream(filePath);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            BufferedImage image = ImageIO.read(bis);

            int height = image.getHeight() / (image.getWidth() / 100);
            BufferedImage preview = new BufferedImage(100, height, image.getType());
            Graphics2D graphics2D = preview.createGraphics();
            graphics2D.drawImage(image, 0, 0, 100, height, null);
            graphics2D.dispose();

            ImageIO.write(preview, getExtension(filePath.getFileName().toString()), baos);
            return baos.toByteArray();
        }
    }
}
