package it.korea.app_boot.gallery.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GalleryRequest {

    @NotBlank(message = "타이틀은 존재해야 합니다")
    private String title;
    @NotNull(message = "파일은 필수입니다")
    private MultipartFile file;
    private String nums;

}
