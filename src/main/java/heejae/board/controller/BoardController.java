package heejae.board.controller;

import heejae.board.entity.Board;
import heejae.board.service.BoardService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public String boardWritePro(Board board, Model model, MultipartFile file) throws Exception{

        boardService.write(board, file);
        // 확인방법
//        System.out.println(board.getTitle());
//        System.out.println(board.getContent());

        //글작성 message 전달
        model.addAttribute("message", "글 작성 완료되었습니다.");

        // 글작성후, 이동할 web 사이트 loaction.replace
        model.addAttribute("searchUrl", "/board/list");

        return "message";
    }

    @GetMapping("/board/list")

    // pageabledefault를 통해 페이징 처리가 가능하다.

    public String boardlist(Model model,
                            @PageableDefault(page = 0, size=10, sort="id", direction = Sort.Direction.DESC)Pageable pageable,
                            @RequestParam(name = "searchKeyword", required = false) String searchKeyword) {

        System.out.println("검색어 확인" + searchKeyword);

        Page<Board> list = null;

        //검색과 검색하지 않았을때의 구별
        if(searchKeyword == null){
            list = boardService.boardList(pageable);
        }else{
            list = boardService.boardSearchList(searchKeyword, pageable);
        }

        int nowPage = list.getPageable().getPageNumber() + 1;

        // 여기 Math.max 함수는 두 수를 비교해서 더 큰수를 뽑아줌
        int startPage = Math.max(nowPage - 4,1);

        // 여기는 Math.min 함수를 사용해서 낮은 수를 뽑아줌
        int endPage = Math.min(nowPage + 5, list.getTotalPages());



        // list라는 이름으로 보낼건데 뭘 보낼거냐? list를 보냄
        model.addAttribute("list",list);
        model.addAttribute("nowPage",nowPage);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);

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
    public String boardUpdate(@PathVariable("id") Integer id, Board board, MultipartFile file) throws Exception{

        //기존 내용 가져오기
        Board boardTemp = boardService.boardView(id);

        //새로 입력한 내용을 기존 내용에 덮어 씌우기
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());

        boardService.write(boardTemp, file);

        return "redirect:/board/list";
    }
}
