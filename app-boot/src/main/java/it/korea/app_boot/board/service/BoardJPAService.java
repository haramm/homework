package it.korea.app_boot.board.service;

import java.io.File;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.korea.app_boot.board.dto.Board;
import it.korea.app_boot.board.dto.BoardDTO;
import it.korea.app_boot.board.dto.BoardFileDTO;
import it.korea.app_boot.board.dto.Board.Response;
import it.korea.app_boot.board.entity.BoardEntity;
import it.korea.app_boot.board.entity.BoardFileEntity;
import it.korea.app_boot.board.repository.BoardFileRepository;
import it.korea.app_boot.board.repository.BoardRepository;
import it.korea.app_boot.common.files.FileUtils;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardJPAService {

    private final BoardRepository boardRepository;
    private final FileUtils fileUtils;
    private final BoardFileRepository fileRepository;

    @Value("${server.file.upload.path}")
    private String filePath;

    public Map<String, Object> getBoardList(Pageable pageable) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        // findAll() -> select * from board;
        Page<BoardEntity> pageObj = boardRepository.findAll(pageable);

        //List of Entity -> List of DTO
        List<BoardDTO.Response> list = 
                        //pageObj.getContent().stream().map(BoardDTO.Response::of).toList(); // 불변 객체 출력
                        pageObj.getContent().stream().map(BoardDTO.Response::of).collect(Collectors.toList()); //가변 객체 출력

        resultMap.put("total", pageObj.getTotalElements());
        resultMap.put("content", list);

        return resultMap;
    }

    @Transactional
    public Map<String, Object> getBoard(int brdId) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();

        BoardEntity entity = boardRepository.getBoard(brdId)
                                        .orElseThrow(() -> new RuntimeException("게시글 없음"));

        BoardDTO.Detail detail = BoardDTO.Detail.of(entity);

        resultMap.put("vo", detail);

        return resultMap;
    }

    @Transactional
    public Map<String, Object> writeBoard(BoardDTO.Request request) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();

        //물리적으로 저장
        Map<String, Object> fileMap = fileUtils.uploadFiles(request.getFile(), filePath);
        BoardEntity entity = new BoardEntity();
        entity.setTitle(request.getTitle());
        entity.setContents(request.getContents());
        entity.setWriter("admin");

        if(fileMap != null) {
            BoardFileEntity fileEntity = new BoardFileEntity();
            fileEntity.setFileName(fileMap.get("fileName").toString());
            fileEntity.setStoredName(fileMap.get("storedFileName").toString());
            fileEntity.setFilePath(fileMap.get("filePath").toString());
            fileEntity.setFileSize(request.getFile().getSize());
            entity.addFiles(fileEntity);
        }

        boardRepository.save(entity);
        resultMap.put("resultCode", 200);
        resultMap.put("resultMsg", "OK");

        return resultMap;
    }

    @Transactional
    public Map<String, Object> updateBoard(BoardDTO.Request request) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();

        BoardEntity entity = boardRepository.findById(request.getBrdId())
                .orElseThrow(() -> new RuntimeException("게시글 없음"));

        entity.setTitle(request.getTitle());
        entity.setContents(request.getContents());

        
        if (request.getFile() != null && !request.getFile().isEmpty()) {
            
            if (entity.getFileList() != null && !entity.getFileList().isEmpty()) {
                for (BoardFileEntity oldFile : entity.getFileList()) {
                    File f = new File(oldFile.getFilePath() + oldFile.getStoredName());
                    if (f.exists()) {
                        f.delete();
                    }
                    fileRepository.delete(oldFile);
                }
                entity.getFileList().clear();
            }
            Map<String, Object> fileMap = fileUtils.uploadFiles(request.getFile(), filePath);
            if (fileMap != null) {
                BoardFileEntity fileEntity = new BoardFileEntity();
                fileEntity.setFileName(fileMap.get("fileName").toString());
                fileEntity.setStoredName(fileMap.get("storedFileName").toString());
                fileEntity.setFilePath(fileMap.get("filePath").toString());
                fileEntity.setFileSize(request.getFile().getSize());
                entity.addFiles(fileEntity);
            }
        }
        boardRepository.save(entity);

        resultMap.put("resultCode", 200);
        resultMap.put("resultMsg", "OK");
        return resultMap;
    }

    @Transactional
    public int updateLike(int brdId) {
        BoardEntity entity = boardRepository.findById(brdId)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));

        entity.setLikeCount(entity.getLikeCount() + 1); 
        boardRepository.save(entity);

        return entity.getLikeCount();
    }

    @Transactional
    public void deleteBoard(int brdId) {
        BoardEntity entity = boardRepository.findById(brdId)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        boardRepository.delete(entity);
    }

    @Transactional
    public Map<String, Object> deleteFile(int bfId) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();

        BoardFileEntity entity = fileRepository.findById(bfId)
                .orElseThrow(() -> new RuntimeException("파일 없음"));

        
        File file = new File(entity.getFilePath() + entity.getStoredName());
        if (file.exists()) {
            file.delete();
        }

        
        fileRepository.delete(entity);

        resultMap.put("resultCode", 200);
        resultMap.put("resultMsg", "삭제 성공");
        return resultMap;
    }

    //파일 다운로드
    public ResponseEntity<Resource> downLoadFile(int bfId) throws Exception{

        //http 헤더 객체
        HttpHeaders header = new HttpHeaders();
        Resource resource = null;

        //파일 정보
        BoardFileEntity entity = 
                fileRepository.findById(bfId).orElseThrow(() -> new NotFoundException("파일정보 없음"));

        BoardFileDTO fileDTO = BoardFileDTO.of(entity);

        String fullPath = fileDTO.getFilePath() + fileDTO.getStoredName();
        String fileName = fileDTO.getFileName(); // 다운로드할 때 사용

        File f = new File(fullPath);
        if(!f.exists()) {
            throw new NotFoundException("파일정보 없음");
        }
        //파일 타입 NIO를 이용한 타입찾기
        String mimeType = Files.probeContentType(Paths.get(f.getAbsolutePath()));

        if(mimeType == null) {
            mimeType = "application/octet-stream"; // 기본 바이너리 파일
        }
        //리소스 객체에 url을 통해 전송할 파일 저장
        resource = new FileSystemResource(f);
        
        //http 응답에서 브라우저가 컨텐츠를 처리하는 방식
        //inline -> 브라우저 바에서 처리 -> open
        //attachment -> 다운로드
        header.setContentDisposition(
            ContentDisposition.builder("attachment")
            .filename(fileName, StandardCharsets.UTF_8)
            .build()
        );

        header.setContentType(MediaType.parseMediaType(mimeType));
        header.setContentLength(fileDTO.getFileSize());
        //캐시 설정
        header.setCacheControl("no-cache, no-store, must-revalidate");
        header.set("Pragma", "no-cache"); //old browser 호환
        header.set("Expires", "0"); // 즉시 삭제

        return new ResponseEntity<>(resource, header, HttpStatus.OK);
    }
}
