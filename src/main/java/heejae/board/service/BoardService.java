package heejae.board.service;

import heejae.board.entity.Board;
import heejae.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
public class BoardService {

    //bean이 알아서 매칭을 해줌
    @Autowired
    private BoardRepository boardRepository;


    //글 작성
    public void write(Board board, MultipartFile file) throws Exception {

        //경로 설정
        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";

        //파일 이름에 붉힐 랜던 네임
        UUID uuid = UUID.randomUUID();

        //파일 이름을 uuid 뒤에 file 이름을 붙임
        String fileName = uuid + "_" + file.getOriginalFilename();

        // 파일을 생성하면 경로에 들어가고 이름은 설정한 이름과 같음
        File saveFile = new File(projectPath, fileName);

        file.transferTo(saveFile);

        board.setFilename(fileName);
        board.setFilepath("/files/" + fileName);

        boardRepository.save(board);
    }

    //게시글 리스트 처리
    public Page<Board> boardList(Pageable pageable){



        // findall은 모든 정보를 가져옴
        //System.out.println(boardRepository.findAll());
        return boardRepository.findAll(pageable);
    }


    // 게시글 검색
    public Page<Board> boardSearchList(String searchKeyword, Pageable pageable){
        return boardRepository.findByTitleContaining(searchKeyword, pageable);
    }

    //특정 게시글 불러오기
    public Board boardView(Integer id){

        //옵셔널 값으러 받아오기 때문에 .get()을 사용
        return boardRepository.findById(id).get();
    }

    //특정 게시글 삭제

    public void boardDelete(Integer id){
        boardRepository.deleteById(id);
    }
}
