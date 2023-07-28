package pl.shelter.shelterbackend.animal.image;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    public void save(MultipartFile multipartFile, Long animalId) {
        Image image = new Image();
        image.setImageName(StringUtils.cleanPath(multipartFile.getOriginalFilename()));
//        image.setContentType(multipartFile.getContentType());
        image.setAnimalId(animalId);
        try {
            image.setData(multipartFile.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        image.setSize(multipartFile.getSize());

        imageRepository.save(image);
    }

    public void deleteImageByAnimalId(Long animalId) {
        imageRepository.deleteByAnimalId(animalId);
    }

    public Optional<Image> getFileByAnimalId(Long animalId) {
        return imageRepository.findFileByAnimalId(animalId);
    }

    public List<Image> getAllFiles() {
        return imageRepository.findAll();
    }
}
