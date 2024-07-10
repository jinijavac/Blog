package org.example.blog.controller;

import lombok.RequiredArgsConstructor;
import org.example.blog.Repository.PostRepository;
import org.example.blog.Repository.UserRepository;
import org.example.blog.Service.CommentService;
import org.example.blog.Service.PostService;
import org.example.blog.domain.Comment;
import org.example.blog.domain.Post;
import org.example.blog.domain.User;
import org.example.blog.security.PrincipalDetail;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentService commentService;

    @GetMapping("/")
    public String index(Model model) {
        List<Post> trendingPosts = postService.getTopTrendingPosts();
        List<Post> latestPosts = postService.findAllPostsOrderBycreatedDateDesc();

        model.addAttribute("trendingPosts", trendingPosts);
        model.addAttribute("latestPosts", latestPosts);

        return "index"; // 메인 페이지 템플릿
    }

    @GetMapping("/board/{id}")
    public String viewPost(@PathVariable Long id,
                           @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                           Model model) {
        postService.incrementViews(id);
        Post post = postService.postdetail(id);
        Page<Comment> comments = commentService.getCommentsByPostId(id, page, 10);

        model.addAttribute("post", post);
        model.addAttribute("comments", comments.getContent());
        model.addAttribute("totalPages", comments.getTotalPages());
        model.addAttribute("currentPage", page);
        return "board/detail"; // 게시물 상세 보기 페이지
    }

    @GetMapping("/board/saveform")
    public String saveForm() {
        return "board/saveform";
    }
    //index 페이지로 모델 정보 들고 이동 (viewResolver)

    @GetMapping("/board/{id}/updateform")
    public String updateform(@PathVariable Long id, Model model) {
        model.addAttribute("post", postService.postdetail(id));
        return "board/updateform";
    }

    @GetMapping("/search")
    public String search(@RequestParam(value = "query", required = false) String query,
                         @RequestParam(value = "searchType", required = false) String searchType,
                         Model model) {
        List<Post> searchResults = new ArrayList<>(); // 초기화
        if (query != null && searchType != null) {
            switch (searchType) {
                case "title":
                    searchResults = postService.searchByTitle(query);
                    break;
                case "content":
                    searchResults = postService.searchByContent(query);
                    break;
                case "user":
                    searchResults = postService.searchByUserUsername(query);
                    break;
                default:
                    // 다른 케이스는 처리하지 않음
                    break;
            }
        }
        model.addAttribute("searchResults", searchResults);
        model.addAttribute("query", query);
        model.addAttribute("searchType", searchType);
        return "board/search"; // search.html 템플릿으로 결과를 렌더링합니다.
    }
}
