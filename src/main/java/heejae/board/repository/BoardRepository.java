package heejae.board.repository;

import heejae.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

// jparepository에는 entity 클래스랑 id의 type을 넣으면됌
public interface BoardRepository extends JpaRepository<Board,Integer> {

    Page<Board> findByTitleContaining(String searchKeyword, Pageable pageable);
}
