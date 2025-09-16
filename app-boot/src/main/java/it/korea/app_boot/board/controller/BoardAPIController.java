package it.korea.app_boot.board.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.korea.app_boot.board.dto.BoardDTO;
import it.korea.app_boot.board.dto.BoardSearchDTO;
import it.korea.app_boot.board.service.BoardJPAService;
import it.korea.app_boot.board.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//return이 view가 아닌 data
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class BoardAPIController {

    private final BoardService service;
    private final BoardJPAService jpaService;

    @GetMapping("/board/list")
    public ResponseEntity<Map<String,Object>> getBoardListData(BoardSearchDTO searchDto) {
        Map<String,Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;

        try {
            resultMap = service.getBoardList(searchDto);
        }
        catch(Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            e.printStackTrace();
        }

        //HTtpServletResponse + HttpStatus 결합 객체
        return new ResponseEntity<>(resultMap, status);
    }

    @GetMapping("/board/data")
    public ResponseEntity<Map<String, Object>> getBoardData(BoardSearchDTO searchDTO) {

        Map<String,Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        log.info("=========== 게시판 데이터 가져오기 ============");

        List<Sort.Order> sorts = new ArrayList<>();
        String[] sidxs = searchDTO.getSidx().split(",");
        String[] sords = searchDTO.getSord().split(",");

        for(int i = 0; i < sidxs.length; i++) {
            if(sords[i].equals("asc")) {
                sorts.add(new Sort.Order(Sort.Direction.ASC, sidxs[i]));
            }
            else {
                sorts.add(new Sort.Order(Sort.Direction.DESC, sidxs[i]));
            }
        }
        //현재 페이지, 가져올 갯수, order by 객체
        Pageable pageable = PageRequest.of(searchDTO.getPage(), searchDTO.getSize(), Sort.by(sorts));

        try {
            resultMap = jpaService.getBoardList(pageable);
        }
        catch(Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            e.printStackTrace();
        }

        //HTtpServletResponse + HttpStatus 결합 객체
        return new ResponseEntity<>(resultMap, status);
    }

    @GetMapping("/board/{brdId}")
    public ResponseEntity<Map<String, Object>> getBoard(@PathVariable(name = "brdId") int brdId) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;

        resultMap = jpaService.getBoard(brdId);

        return new ResponseEntity<>(resultMap, status);
    }

    @PostMapping("/board")
    public ResponseEntity<Map<String, Object>> writeBoard(@Valid @ModelAttribute BoardDTO.Request request) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;

        resultMap = jpaService.writeBoard(request);


        return new ResponseEntity<>(resultMap, status);
    }

    

    @GetMapping("/board/file/{bfId}")
    public ResponseEntity<Resource> downFile(@PathVariable("bfId") int bfId) throws Exception {

        return jpaService.downLoadFile(bfId);
    }

    @PutMapping("/board/{brdId}")
    public ResponseEntity<Map<String, Object>> updateBoard(@PathVariable("brdId") int brdId,
                                        @Valid @ModelAttribute BoardDTO.Request request) throws Exception {

        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;

        request.setBrdId(brdId);

        resultMap = jpaService.updateBoard(request);

        return new ResponseEntity<>(resultMap, status);
    }

    @DeleteMapping("/board/{brdId}")
    public ResponseEntity<Map<String, Object>> deleteBoard(@PathVariable("brdId") int brdId) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;

        try {
            jpaService.deleteBoard(brdId); 
            resultMap.put("resultCode", 200);
            resultMap.put("resultMsg", "삭제 성공");
        } 
        catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            resultMap.put("resultCode", 500);
            resultMap.put("resultMsg", "삭제 실패: " + e.getMessage());
        }

        return new ResponseEntity<>(resultMap, status);
    }

    @PatchMapping("/board/{brdId}/like")
    public ResponseEntity<Map<String, Object>> updateLike(@PathVariable("brdId") int brdId) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;

        try {
            int likeCount = jpaService.updateLike(brdId);
            resultMap.put("resultCode", 200);
            resultMap.put("resultMsg", "좋아요 성공");
            resultMap.put("likeCount", likeCount); 
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            resultMap.put("resultCode", 500);
            resultMap.put("resultMsg", "좋아요 실패: " + e.getMessage());
        }

        return new ResponseEntity<>(resultMap, status);
    }

    @DeleteMapping("/board/file/{bfId}")
    public ResponseEntity<Map<String, Object>> deleteFile(@PathVariable("bfId") int bfId) throws Exception {
        Map<String, Object> resultMap = jpaService.deleteFile(bfId);
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }
}
