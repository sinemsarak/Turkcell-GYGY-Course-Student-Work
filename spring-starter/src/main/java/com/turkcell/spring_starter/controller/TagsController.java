package com.turkcell.spring_starter.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.spring_starter.dto.CreateTagRequest;
import com.turkcell.spring_starter.dto.ListTagResponse;
import com.turkcell.spring_starter.service.TagServiceImpl;

@RestController
@RequestMapping("/api/tags")
public class TagsController {
    private final TagServiceImpl tagServiceImpl;

    public TagsController(TagServiceImpl tagServiceImpl) {
        this.tagServiceImpl = tagServiceImpl;
    }

    @PostMapping
    public ListTagResponse create(@RequestBody CreateTagRequest request) {
        return tagServiceImpl.create(request);
    }

    @GetMapping
    public List<ListTagResponse> getAll() {
        return tagServiceImpl.getAll();
    }

    @GetMapping("/{id}")
    public ListTagResponse getById(@PathVariable UUID id) {
        return tagServiceImpl.getById(id);
    }

    @PutMapping("/{id}")
    public ListTagResponse update(@PathVariable UUID id, @RequestBody CreateTagRequest request) {
        return tagServiceImpl.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        tagServiceImpl.delete(id);
    }
}
