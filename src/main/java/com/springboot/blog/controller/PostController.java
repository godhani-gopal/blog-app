package com.springboot.blog.controller;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostDtoV2;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/posts")
@Tag(name = "CRUD REST APIs for Post resource")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // create a blog post
    @Operation(summary = "create POST REST API",
            description = "create a single post using this REST API. POST will be saved in the database upon successful request.")
    @ApiResponse(responseCode = "201",
            description = "Http Status 201 CREATED")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto) {
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    // get all posts rest api
    @Operation(summary = "GET all POST REST API",
            description = "used to fetch all the POSTs from the DB")
    @ApiResponse(responseCode = "200",
            description = "Http Status 200 SUCCESS")
    @GetMapping
    public PostResponse getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
        return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
    }

    // get post by id version 1
    @Operation(summary = "GET POST by id REST API",
            description = "used to fetch a single POST from the DB using id")
    @ApiResponse(responseCode = "200",
            description = "Http Status 200 SUCCESS")
    @GetMapping(value = "/{id}",params = "version=1")
    public ResponseEntity<PostDto> getPostByID(@PathVariable(name = "id") long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    // get post by id version 2
    @Operation(summary = "GET POST by id REST API",
            description = "used to fetch a single POST from the DB using id")
    @ApiResponse(responseCode = "200",
            description = "Http Status 200 SUCCESS")
    @GetMapping("/{id}")
    public ResponseEntity<PostDtoV2> getPostByIDV2(@PathVariable(name = "id") long id) {
        PostDto postDto = postService.getPostById(id);
        PostDtoV2 postDtoV2 = new PostDtoV2();
        postDtoV2.setContent(postDto.getContent());
        postDtoV2.setTitle(postDto.getTitle());
        postDtoV2.setDescription(postDto.getDescription());
        Set<String> tags = new HashSet<>();
        tags.add("Java");
        tags.add("SpringBoot");
        tags.add("Another Tag");
        postDtoV2.setTags(tags);

        return ResponseEntity.ok(postDtoV2);
    }

    // get post by id version 3
    @Operation(summary = "GET POST by id REST API",
            description = "used to fetch a single POST from the DB using id")
    @ApiResponse(responseCode = "200",
            description = "Http Status 200 SUCCESS")
    @GetMapping(value = "/{id}", params = "version=3")
    public ResponseEntity<PostDto> getPostByIDV3(@PathVariable(name = "id") long id) {
        PostDto postDto = postService.getPostById(id);
        postDto.setTitle("version-3 title");
        return ResponseEntity.ok(postDto);
    }

    // get post by id version 4
    @Operation(summary = "GET POST by id REST API",
            description = "used to fetch a single POST from the DB using id")
    @ApiResponse(responseCode = "200",
            description = "Http Status 200 SUCCESS")
    @GetMapping(value = "/{id}",headers = "X-API-VERSION=4")
    public ResponseEntity<PostDto> getPostByIDV4(@PathVariable(name = "id") long id) {
        PostDto postDto = postService.getPostById(id);
        postDto.setTitle("version-4 via header title");
        return ResponseEntity.ok(postDto);
    }

    // get post by id version 5
    @Operation(summary = "GET POST by id REST API",
            description = "used to fetch a single POST from the DB using id")
    @ApiResponse(responseCode = "200",
            description = "Http Status 200 SUCCESS")
    @GetMapping(value = "/{id}",produces = "application/vnd.companyname.v5+json")
    public ResponseEntity<PostDto> getPostByIDV5(@PathVariable(name = "id") long id) {
        PostDto postDto = postService.getPostById(id);
        postDto.setTitle("version-5 via header title");
        return ResponseEntity.ok(postDto);
    }

    // update post by id
    @Operation(summary = "UPDATE POST REST API",
            description = "used to update a single POST id")
    @ApiResponse(responseCode = "200",
            description = "Http Status 200 SUCCESS")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable(name = "id") long id) {
        return ResponseEntity.ok(postService.updatePost(postDto, id));
    }

    // delete a post using id
    @Operation(summary = "DELETE a POST using id REST API",
            description = "used to delete a single POST from the DB using id")
    @ApiResponse(responseCode = "200",
            description = "Http Status 200 SUCCESS")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable(name = "id") long id) {
        postService.deletePostById(id);
        return new ResponseEntity<>("Post with ID " + id + " deleted successfully!", HttpStatus.OK);
    }

    //get all posts using category id
    @Operation(summary = "GET all POSTs by categoryId REST API",
            description = "used to fetch all POSTs from the DB that belong to specified category")
    @ApiResponse(responseCode = "200",
            description = "Http Status 200 SUCCESS")
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable("categoryId") long categoryId) {
        List<PostDto> postDtos = postService.getPostsByCategory(categoryId);
        return ResponseEntity.ok(postDtos);
    }
}