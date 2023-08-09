package pl.shelter.shelterbackend.animal.image;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    public void save(MultipartFile multipartFile, Long animalId) {
        Image image = new Image();
        image.setImageName("animal_" + animalId);
        image.setAnimalId(animalId);
        try {
            image.setData(multipartFile.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        imageRepository.save(image);
    }

    public void deleteImageByAnimalId(Long animalId) {
        imageRepository.deleteByAnimalId(animalId);
    }

    public Image getFileByAnimalId(Long animalId) {
        return imageRepository.findFileByAnimalId(animalId).orElseThrow();
    }
}
