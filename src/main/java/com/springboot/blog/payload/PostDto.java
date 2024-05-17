package com.springboot.blog.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Schema(description = "PostDto Model Description")
@Data
public class PostDto {
    private long id;

    @Schema(description = "BlogPost Title")
    @NotEmpty
    @Size(min = 2, message = "Post title should have at least 2 characters")
    private String title;

    @Schema(description = "BlogPost Description")
    @NotEmpty
    @Size(min = 10, message = "Post title should have at least 2 characters")
    private String description;

    @Schema(description = "BlogPost Content")
    @NotEmpty
    private String content;

    @Schema(description = "BlogPost Comments")
    private Set<CommentDto> comments;

    @Schema(description = "BlogPost Category")
    private long categoryId;
}
