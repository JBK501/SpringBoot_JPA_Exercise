package kr.or.ddit.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PK가 이미 있다면, 다음번호로
    private Long id;
    @Column
    private String title;
    @Column
    private  String content;
}
