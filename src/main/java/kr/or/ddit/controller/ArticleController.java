package kr.or.ddit.controller;

import kr.or.ddit.dto.ArticleForm;
import kr.or.ddit.entity.Article;
import kr.or.ddit.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Slf4j
// 1. 컨트롤러 선언 : 해당 파일이 컨트롤러 임을 선언한다.
@Controller
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    // 2. URL 요청 접수
    // 뷰 페이지를 보여주기 위해 newArticleForm() 메소드를 추가한다.
    @GetMapping("/articles/new")
    public String newArticleForm() { // 3. 메소드 작성
        // 4. 반환값 작성 : 뷰 페이지의 이름을 적는다.
        // articles 디렉터리를 만들고, new.mustache 뷰 페이지를 추가한다.
        return "articles/new";
    }

    // 1. URL 요청 접수
    // 2. @GetMapping 대신 @PostingMapping 어노테이션을 사용한다.
    // 뷰 페이지에서 폼 데이터를 POST 방식으로 전송했으므로, 컨트롤러에서 받을 때도 @PostMapping으로 받는다.
    // 이때, 괄호 안에는 받는 URL 주소를 넣는다.
    // new.mustache에서 form 태그에 action 속성에 맵핑된 '/articles/create'로 설정했기 때문에 맵핑될 수 있다.
    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form) {
        // ArticleForm 타입의 form 객체를 매개변수로 선언한다.
        // 폼에서 전송한 데이터가 dto에 잘 담겼는지 확인하기 위해서 출력문을 추가한다.
        // 로깅을 사용하기 위해 클래스 명 위에 @Slf4j 어노테이션을 사용한다.
        // dto에 입력한 데이터가 정상적으로 바인딩되어 넘어오기 위해서는 mustache 파일에 작성된 form 태그 내에
        // 입력 태그들의 name과 ArticleForm 클래스 내 필드명이 같아야만 값이 정상적으로 바인딩되어 들어올 수 있다.
        log.info("createArticle->form:" + form.toString());

        // [JPA를 이용해서 엔터티를 DB에 저장하기]
        // 1. dto를 엔티티로 변환한다.
        Article article = form.toEntity();
        // dto가 엔티티로 변환되었는지 확인
        log.info("createArticle->article : " + article.toString());

        // 2. Repository로 엔티티를 DB에 저장한다.
        // entity로 변환된 article 데이터를 저장 후, saved 객체에 반환한다.
        Article saved = articleRepository.save(article);
        // article이 db에 잘 저장되었는지 확인한다.
        log.info("createdArticle->article : " + saved.toString());

        // 3. 결과로 이동한다.
        return "redirect:/articles/" + saved.getId();
    }

    @GetMapping("/articles/{id}")
    public String show(@PathVariable("id") Long id, Model model){
        log.info("요청 경로에 포함된 파라미터 id : " + id);

        // 1. 아이디를 조회해 데이터를 가져온다.
        Article articleEntity = articleRepository.findById(id).orElse(null);

        // 2. Model에 데이터를 등록한다.
        model.addAttribute("article",articleEntity);

        // 3. 뷰 페이지를 반환한다.
        return "articles/show";
    }
    @GetMapping("/articles")
    public String index(Model model) { // index() 메소드의 매개변수로 model 객체를 받아온다.
        // 캐스팅이란 형변환이라고도 하며 데이터 타입을 변환하는 것을 말한다.
        // 자바에서 상속 관계가 있는 특정 객체는 상황에 따라 더 넓은 범위로 해석될 수 있고, 때때로
        // 좁은 범위로 해석 될 수도 있다.
        // 이때, 넓은 범위로 해석하는 것을 업 캐스팅(upcasting), 좁은 범위로 해석하는 것을 다운 캐스팅(downcasting)
        // 이라고 한다.
        // 예를 들어 고양이를 생물로 해석했다면 업캐스팅이고, 생물에서 다시 동물로 해석했다면 다운캐스팅이다.

        // 1. 모든 데이터를 가져온다.
        ArrayList<Article> articleEntityList =  articleRepository.findAll();
        // 2. 모델에 데이터를 등록한다.
        model.addAttribute("articleList", articleEntityList);
        // 3. 뷰 페이지를 설정한다.
        return "articles/index";
    }

    // 데이터 수정 화면 요청접수
    // URL 주소에 있는 id를 받아오는 것이므로 데이터 타입 앞에 @PathVariable 어노테이션을 추가한다.
    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        // 1. db에서 수정할 데이터를 가져온다.
        // db에서 데이터를 가져올 때는 Repository를 이용한다.
        // 만약 데이터를 찾지 못하면 null을 반환하고, 데이터를 찾았다면 Article 타입의 articleEneityfmf wkrtjdgksek.
        Article articleEntity = articleRepository.findById(id).orElse(null);
        // 2. 모델에 데이터를 등록한다.
        model.addAttribute("article", articleEntity);
        // 3. 뷰 페이지를 설정한다.
        return "articles/edit";
    }

    // URL 요청 접수
    @PostMapping("/articles/update")
    public String update(ArticleForm form) {
        // 1. dto를 엔티티로 변환한다.
        log.info("update->form : " + form.toString());
        Article articleEntity = form.toEntity();
        log.info("update->articleEntity : " + articleEntity);

        // 2. 엔티티를 db에 저장한다.
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);

        // 기존 데이터가 존재한다면, 수정 할 수 있는 데이터가 존재하는 경우
        if (target != null) {
            articleRepository.save(articleEntity); // 엔티티를 db에 저장(갱신)
        }

        // 3. 수정 결과를 메시지로 리다이렉트한다.
        return "redirect:/articles/" + articleEntity.getId();
    }
    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable("id") Long id, RedirectAttributes ra) {
        log.info("삭제 요청이 들어왔습니다!");

        // 1. 삭제할 대상을 가져온다.
        Article target = articleRepository.findById(id).orElse(null);

        // 2. 대상 엔티티를 삭제한다.
        if(target != null) {
            // delete() 메소드로 대상 삭제
            articleRepository.delete(target);
            ra.addFlashAttribute("msg","삭제가 완료되었습니다!");
        }

        // 3. 결과 페이지로 리다이렉트한다.
        return "redirect:/articles";
    }
}
