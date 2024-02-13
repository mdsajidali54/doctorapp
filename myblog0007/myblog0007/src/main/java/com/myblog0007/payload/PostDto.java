package com.myblog0007.payload;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class PostDto {
    private Long id;

    @NotEmpty
    @Size(min = 5 ,message = "Title should be at least 5 character")
    private String title;

    @Size(min = 5 , message = "Description should be at least 5 character")
    private String description;

    @Size(min = 5 , message = "Content should be at least 5 character")
    private String content;

}
