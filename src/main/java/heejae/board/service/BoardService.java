package heejae.board.service;

import heejae.board.entity.Board;
import heejae.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    //bean이 알아서 매칭을 해줌
    @Autowired
    private BoardRepository boardRepository;


    //글 작성
    public void write(Board board){

        boardRepository.save(board);
    }

    //게시글 리스트 처리
    public List<Board> boardList(){
        // findall list 반환
        //System.out.println(boardRepository.findAll());
        return boardRepository.findAll();
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
