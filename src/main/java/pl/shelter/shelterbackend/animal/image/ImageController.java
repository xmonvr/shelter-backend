package pl.shelter.shelterbackend.animal.image;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@RequestMapping("images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

//    @GetMapping
//    @Transactional
//    public List<ImageResponse> list() {
//        return imageService.getAllFiles()
//                .stream()
//                .map(this::mapToImageResponse)
//                .collect(Collectors.toList());
//    }

//    private ImageResponse mapToImageResponse(Image image) {
//        String downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
//                .path("/images/")
//                .path(image.getId().toString())
//                .toUriString();
//        ImageResponse imageResponse = new ImageResponse();
//        imageResponse.setId(image.getId());
//        imageResponse.setName(image.getImageName());
//        imageResponse.setContentType(image.getContentType());
//        imageResponse.setUrl(downloadURL);
//
//        return imageResponse;
//    }

//    @GetMapping("/get-by-animalId")
//    @Transactional
//    public ResponseEntity<byte[]> getFile(@RequestParam Long animalId) {
//        Optional<Image> file = imageService.getFileByAnimalId(animalId);
//
//        if (!file.isPresent()) {
//            return ResponseEntity.notFound()
//                    .build();
//        }
//
//        Image fileEntity = file.get();
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileEntity.getImageName() + "\"")
//                .contentType(MediaType.valueOf(fileEntity.getContentType()))
//                .body(fileEntity.getData());
//    }

    @GetMapping(value = "/get-image-by-animalId", produces = MediaType.IMAGE_JPEG_VALUE)
    @Transactional
    public byte[] getImage(@RequestParam Long animalId) {
        return imageService.getFileByAnimalId(animalId).orElseThrow().getData();
    }
}


