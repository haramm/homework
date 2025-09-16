package it.korea.app_boot.gallery.service;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.korea.app_boot.common.dto.PageVO;
import it.korea.app_boot.common.files.FileUtils;
import it.korea.app_boot.gallery.dto.GalleryDTO;
import it.korea.app_boot.gallery.dto.GalleryRequest;
import it.korea.app_boot.gallery.entity.GalleryEntity;
import it.korea.app_boot.gallery.repository.GalleryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GalleryService {

    @Value("${server.file.gallery.path}")
    private String filePath;

    private final GalleryRepository galleryRepository;
    private final FileUtils fileUtils;
    private final List<String> extensions = Arrays.asList("jpg","jpeg","gif","png","webp","bmp");

    // 갤러리 리스트 조회
    public Map<String, Object> getGalleryList(Pageable pageable) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        Page<GalleryEntity> list = galleryRepository.findAll(pageable);

        List<GalleryDTO> gallerys = list.getContent().stream().map(GalleryDTO::of).toList();
        PageVO pageVO = new PageVO();
        pageVO.setData(list.getNumber(), (int)list.getTotalElements());

        resultMap.put("total", list.getTotalElements());
        resultMap.put("page", list.getNumber());
        resultMap.put("content", gallerys);
        resultMap.put("pageHTML", pageVO.pageHTML());

        return resultMap;
    }

    // 갤러리 등록
    @Transactional
    public void addGallery(GalleryRequest request) throws Exception {
        GalleryEntity entity = createGalleryEntity(request, null);
        galleryRepository.save(entity);
    }

    // 갤러리 수정
    @Transactional
    public void updateGallery(GalleryRequest request) throws Exception {
        if(request.getNums() == null || request.getNums().isEmpty()) {
            throw new RuntimeException("수정할 글 ID가 없습니다.");
        }

        GalleryEntity entity = galleryRepository.findById(request.getNums())
                .orElseThrow(() -> new RuntimeException("해당 글이 존재하지 않습니다."));

        entity = createGalleryEntity(request, entity);
        galleryRepository.save(entity);
    }

    // 엔티티 생성, 기존 파일 존재 시 삭제 후 등록 -> 수정, 생성 동시 기능 -> 해당 기능 사용하여 위 메소드에서 db에 저장
    private GalleryEntity createGalleryEntity(GalleryRequest request, GalleryEntity entity) throws Exception {
        boolean isNew = (entity == null);

        if(isNew) {
            entity = new GalleryEntity();
            String newNums = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10);
            entity.setNums(newNums);
            entity.setWriter("admin"); 
        }

        entity.setTitle(request.getTitle());

        // 파일 처리
        if(request.getFile() != null && !request.getFile().isEmpty()) {
            String fileName = request.getFile().getOriginalFilename();
            String ext = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();

            if(!extensions.contains(ext)) {
                throw new RuntimeException("파일 형식이 올바르지 않습니다. 이미지만 가능합니다.");
            }

            Map<String, Object> fileMap = fileUtils.uploadFiles(request.getFile(), filePath);
            if(fileMap == null) {
                throw new RuntimeException("파일 업로드 실패");
            }

            // 이전 파일 삭제 (수정 시)
            if(!isNew && entity.getStoredName() != null) {
                File oldFile = new File(filePath + entity.getStoredName());
                if(oldFile.exists()) oldFile.delete();

                File oldThumb = new File(filePath + "thumb" + File.separator + entity.getFileThumbName());
                if(oldThumb.exists()) oldThumb.delete();
            }

            String storedFilePath = filePath + fileMap.get("storedFileName").toString();
            File file = new File(storedFilePath);
            if(!file.exists()) throw new RuntimeException("업로드 파일이 존재하지 않음");

            String thumbFilePath = filePath + "thumb" + File.separator;
            String thumbName = fileUtils.thumbNailFile(150, 150, file, thumbFilePath);

            entity.setFileName(fileMap.get("fileName").toString());
            entity.setStoredName(fileMap.get("storedFileName").toString());
            entity.setFileThumbName(thumbName);
            entity.setFilePath(filePath);
        }

        return entity;
    }
}