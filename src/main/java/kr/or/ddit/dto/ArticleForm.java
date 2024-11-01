package kr.or.ddit.dto;

import kr.or.ddit.entity.Article;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class ArticleForm {
    private long id;
    private String title;
    private  String content;

    // 폼 데이터를 담은 dto 객체를 엔티티로 변환하는 역할
    public Article toEntity() {
        // lombok 설정에 의해서 @AllArgsContructor 어노테이션이 적용되지 않아 에러가 발생할 수 있다.
        return new Article(id, title, content);
    }
}
