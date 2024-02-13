package com.myblog0007.service.impl;

import com.myblog0007.entity.Comment;
import com.myblog0007.entity.Post;
import com.myblog0007.exception.ResourceNotFound;
import com.myblog0007.payload.CommentDto;
import com.myblog0007.repository.CommentRepository;
import com.myblog0007.repository.PostRepository;
import com.myblog0007.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private PostRepository postRepository;
    private CommentRepository commentRepository;
    private ModelMapper modelMapper;

    public CommentServiceImpl(PostRepository postRepository, CommentRepository commentRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {

        Comment comment= mapToEntity(commentDto);
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFound("Post not found with id:  " + postId)
        );
        comment.setPost(post);
        Comment savedComment = commentRepository.save(comment);
      CommentDto dto=   mapToDto(savedComment);
        return dto;
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFound("post not found with postId"+postId)

        );
        List<Comment> comments = commentRepository.findByPostId(postId);
        // convert comment into dto
        List<CommentDto> commentDtos = comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
        return commentDtos;
    }

    @Override
    public CommentDto getCommentsById(Long postId, Long commentId) {

        postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFound("Post not found with id: "+ postId)
       );

        Comment comment = commentRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFound("Comment not found with id: " + commentId)
        );
        CommentDto commentDto = mapToDto(comment);

        return commentDto;
    }

    @Override
    public List<CommentDto> getAllCommentsById() {
        List<Comment> comments = commentRepository.findAll();
      return   comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public void deleteCommentById(Long postId, Long commentId) {
        postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFound("Post not found with id: "+ postId)
        );

        Comment comment = commentRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFound("Comment not found with id: " + commentId)
        );
        commentRepository.deleteById(commentId);

    }


    private CommentDto mapToDto(Comment savedComment) {
        CommentDto dto = modelMapper.map(savedComment, CommentDto.class);

        return dto;
    }

    private Comment mapToEntity(CommentDto commentDto) {
        Comment comment = modelMapper.map(commentDto, Comment.class);
        return comment;
    }
}
