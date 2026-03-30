package heejae.board.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Board{

    @Id
//    identity는 mariadb에서 쓰는거임, jpa가 읽어서 처리함
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String content;

    //파일 업로드
    private String filename;

    //파일위치
    private String filepath;

}
