package pl.shelter.shelterbackend.animal;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.shelter.shelterbackend.animal.image.Image;
import pl.shelter.shelterbackend.animal.image.ImageService;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class AnimalService {

    private AnimalRepository animalRepository;
    private final ImageService imageService;

    public Animal createAnimal(Animal animal, MultipartFile image) {
        var newAnimal = animalRepository.save(animal);
        imageService.save(image, newAnimal.getId());
        return newAnimal;
    }

    public Animal getAnimalById(Long id) {
        return animalRepository.findById(id);
    }

    public void updateAnimal(Long id, Animal animal, MultipartFile image) {
        Animal animalUpdate = animalRepository.findById(id);
        if (animal.getName() != null) {
            animalUpdate.setName(animal.getName());
        }

        if (animal.getTypeOfAnimal() != null) {
            animalUpdate.setTypeOfAnimal(animal.getTypeOfAnimal());
        }

        if (animal.getChip_number() != null) {
            animalUpdate.setChip_number(animal.getChip_number());
        }

        if (animal.getGender() != null) {
            animalUpdate.setGender(animal.getGender());
        }

        if (animal.getIsVaccinated() != null) {
            animalUpdate.setIsVaccinated(animal.getIsVaccinated());
        }

        if (animal.getAge() != null) {
            animalUpdate.setAge(animal.getAge());
        }

        if(image != null ) {
            Image file = imageService.getFileByAnimalId(id).orElseThrow();
            try {
                file.setData(image.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
//        animalRepository.save(animalUpdate);
    }

    public void deleteAnimal(Long id) {
        animalRepository.deleteById(id);
        imageService.deleteImageByAnimalId(id);
    }

    public List<Animal> getAllAnimals() {
        return animalRepository.findAll();
    }

    public List<Animal> findFilteredAnimals(Integer ageMin, Integer ageMax, Gender gender, TypeOfAnimal typeOfAnimal) {
        return animalRepository.filterAnimals(ageMin, ageMax, gender, typeOfAnimal);
    }

//    public void convertImage() {
//            String fileSource = "C:/imgSource/image.jpg";
//            String fileDestination = "C:/imgDestination/destination.jpeg";
//
//            try {
//
//                byte [] byteImage = Utils.ImageToByte(new Image(fileSource));
//
//                bd.addImage(byteImage, 1);
//                System.out.println(org.postgresql.util.Base64.encodeBytes(bd.getImage(1)));
//                Utils.byteToImage(bd.getImage(1), new Image(fileDestination));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//    }

//    public void createImage(MultipartFile multipartFile) {
//
//        String fileName = generateFileName();
//
//        Image image = Image.build();
//        image.setFileName(fileName);
//        image.setFiles(multipartFile);
//
//        try {
//            image.setData(multipartFile.getBytes());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return image;
//    }

//    private String generateFileName() {
//        LocalDateTime now = LocalDateTime.now(ZoneId.of("Warsaw/Europe"));
//        log.info("---------------------------generateFileName");
//        log.info("now --> " + now);
//        return now.toString();
//    }
//
//    public List<Long> getAllIds() {
//        return animalRepository.findAllIds();
//    }
}
