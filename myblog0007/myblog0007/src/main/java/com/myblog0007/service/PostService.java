package com.myblog0007.service;

import com.myblog0007.payload.PostDto;
import com.myblog0007.payload.PostResponse;


public interface PostService {

    PostDto savePost(PostDto postDto);

    void deletePost(long id);

    PostDto updatePost(long id, PostDto postDto);

    PostDto getPostById(long id);

    PostResponse getPosts(int pageNo, int pageSize, String sortBy, String sortDir);
}
