package com.myblog0007.controller;

import com.myblog0007.payload.PostDto;
import com.myblog0007.payload.PostResponse;
import com.myblog0007.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/post")
public class PostController {
    
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    //http://localhost:8080/api/post
    // ? ---> it indicates we can return anything
    @PostMapping
    public ResponseEntity<?> savePost(@Valid @RequestBody PostDto postDto, BindingResult result){
        if(result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        PostDto dto = postService.savePost(postDto);

        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    // delete

    //http://localhost:8080/api/post
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") long id){
        postService.deletePost(id);

        return  new ResponseEntity<>("Post is deleted", HttpStatus.OK);

    }
    // update record

    //http://localhost:8080/api/post
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost( @PathVariable("id")  long id, @RequestBody PostDto postDto){

        PostDto dto = postService.updatePost(id, postDto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    // get record

    //http://localhost:8080/api/post
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") long id){
        PostDto dto = postService.getPostById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    //http://localhost:8080/api/post?pageNo=0&pageSize=5&sortBy=description&sortDir=desc
    @GetMapping
    public PostResponse getPosts(

            @RequestParam(value = "pageNo",defaultValue = "0",required = false) int pageNo,
            @RequestParam(value = "pageSize",defaultValue = "5",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "id", required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc", required = false) String sortDir


    ){
       PostResponse postResponse= postService.getPosts(pageNo, pageSize,sortBy,sortDir);

       return postResponse;
    }
    
}
