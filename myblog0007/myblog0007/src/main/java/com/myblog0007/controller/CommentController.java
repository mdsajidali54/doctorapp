package com.myblog0007.controller;

import com.myblog0007.payload.CommentDto;
import com.myblog0007.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    //http://localhost:8080/api/posts/1/comments

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postId") long postId, @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);

    }
    //http://localhost:8080/api/posts/1/comments
    //get the comment based on postId

    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> getCommentsByPostId(@PathVariable(value = "postId") Long postId) {
        return commentService.getCommentsByPostId(postId);

    }
    //http://localhost:8080/api/posts/1/comments/2

    // get the comment based on commentId
    @GetMapping("/posts/{postId}/comments/{commentId}")
    public CommentDto getCommentsById(@PathVariable(value = "postId") Long postId,
                                      @PathVariable(value = "commentId") Long commentId) {
        return commentService.getCommentsById(postId, commentId);

    }

    // get all the comments by id

    //http://localhost:8080/api/comments
    @GetMapping("/comments")
    public List<CommentDto> getAllCommentsById() {
        return commentService.getAllCommentsById();

    }
    // delete from comment table
   // http://localhost:8080/api/posts/1/comments/2

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteCommentById(
            @PathVariable(value = "postId") Long postId,
            @PathVariable(value = "commentId") Long commentId
    ){

      commentService.deleteCommentById(postId, commentId);
      return new ResponseEntity<>("Comment is deleted successfully", HttpStatus.OK);

    }
}