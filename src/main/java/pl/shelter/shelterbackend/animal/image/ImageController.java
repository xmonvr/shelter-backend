//package pl.shelter.shelterbackend.animal.image;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.transaction.Transactional;
//
//@RestController
//@RequestMapping("images")
//@RequiredArgsConstructor
//public class ImageController {
//
//    private final ImageService imageService;
//
//    //todo zrobic update w pracy inz w zrzucie ekranu oraz w spisie endpointow
//    @GetMapping(value = "/get-image-by-animalId", produces = MediaType.IMAGE_JPEG_VALUE)
//    @Transactional
//    public byte[] getImage(@RequestParam Long animalId) {
//        return imageService.getFileByAnimalId(animalId).getData();
//    }
//}


