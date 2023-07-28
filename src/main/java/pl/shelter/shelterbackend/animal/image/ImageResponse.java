package pl.shelter.shelterbackend.animal.image;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageResponse {

    private Long id;
    private String name;
    private Long size;
    private String url;
    private String contentType;

}
