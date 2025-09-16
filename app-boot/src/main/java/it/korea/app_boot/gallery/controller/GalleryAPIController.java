package it.korea.app_boot.gallery.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.korea.app_boot.gallery.dto.GalleryRequest;
import it.korea.app_boot.gallery.service.GalleryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class GalleryAPIController {

    private final GalleryService galleryService;

    @PostMapping("/gal")
    public ResponseEntity<Map<String, Object>> writeGallery(@Valid @ModelAttribute GalleryRequest request) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        try {
            galleryService.addGallery(request);
            resultMap.put("resultCode", 200);
            resultMap.put("resultMessage", "OK");
        }
        catch(Exception e) {
            //예외 발생 시 공통 모듈을 실행하기 위해 예외를 던진다
            throw new Exception(e.getMessage() == null ? "서버에러" : e.getMessage());
        }

        return new ResponseEntity<>(resultMap, status);
    }

    @GetMapping("/gal")
    public ResponseEntity<Map<String, Object>> getGalleryList(
                        @PageableDefault(page = 0, size = 10, sort = "createDate", direction = Sort.Direction.DESC)
                                                                        Pageable pageable) throws Exception {
        Map<String, Object> resultMap = galleryService.getGalleryList(pageable);


        return new ResponseEntity<>(resultMap, HttpStatus.OK);
        
    }

    @PutMapping("/gal")
    public ResponseEntity<Map<String, Object>> updateGallery(@Valid @ModelAttribute GalleryRequest request) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;

        try {
            galleryService.updateGallery(request);
            resultMap.put("resultCode", 200);
            resultMap.put("resultMessage", "OK");
        } 
        catch(Exception e) {
            throw new Exception(e.getMessage() == null ? "서버에러" : e.getMessage());
        }

        return new ResponseEntity<>(resultMap, status);
    }
}
