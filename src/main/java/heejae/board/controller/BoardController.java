package heejae.board.controller;

import heejae.board.entity.Board;
import heejae.board.service.BoardService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;


    //getmapping은 localhost8080 들어왔을때 "/"경로로 들어왔을때 글자를 뛰워줌

    //어떤 url에 접근시 해당 페이를 뛰울거냐
    //localhost:8080/board/write
    @GetMapping("/board/write")
    public String boardWriteForm(){
        return "boardwrite";
    }

    @PostMapping("/board/writepro")
    public String boardWritePro(Board board){

        boardService.write(board);
        // 확인방법
        System.out.println(board.getTitle());
        System.out.println(board.getContent());
        return "redirect:/board/list";
    }

    @GetMapping("/board/list")
    public String boardlist(Model model) {
        // list라는 이름으로 보낼건데 뭘 보낼거냐? boardlist를 보냄
        model.addAttribute("list",boardService.boardList());

        return "boardlist";
    }

    //다시 넘겨줄때 매개변수에 모델 사용
    //locahost:8080/board./view?id=1 -> 파라미터 겟방식
    @GetMapping("/board/view")
    public String boardview(Model model,Integer id){

        model.addAttribute("board", boardService.boardView(id));
        return"boardview";
    }


    @GetMapping("/board/delete")
    public String boardDelete(Integer id){

        boardService.boardDelete(id);

        return "redirect:/board/list";
    }

    @GetMapping("/board/modify/{id}")
    //경로에서 id 값이 pathvarible을 사용해서 id의 값으로 들어옴
    public String boardModify(@PathVariable("id") Integer id,Model model){

        model.addAttribute("board",boardService.boardView(id));
        return "boardmodify";
    }


    @PostMapping("/board/update/{id}")
    public String boardUpdate(@PathVariable("id") Integer id, Board board){

        //기존 내용 가져오기
        Board boardTemp = boardService.boardView(id);

        //새로 입력한 내용을 기존 내용에 덮어 씌우기
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());

        boardService.write(boardTemp);

        return "redirect:/board/list";
    }
}
