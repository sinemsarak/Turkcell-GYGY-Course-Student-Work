package com.turkcell.spring_starter.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.turkcell.spring_starter.dto.CreateTagRequest;
import com.turkcell.spring_starter.dto.ListTagResponse;
import com.turkcell.spring_starter.entity.Tag;
import com.turkcell.spring_starter.repository.TagRepository;

@Service
public class TagServiceImpl {
    private final TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public ListTagResponse create(CreateTagRequest request) {
        Tag tag = new Tag();
        tag.setName(request.getName());
        tag = tagRepository.save(tag);
        return toResponse(tag);
    }

    public List<ListTagResponse> getAll() {
        return tagRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public ListTagResponse getById(UUID id) {
        Tag tag = tagRepository.findById(id).orElseThrow();
        return toResponse(tag);
    }

    public ListTagResponse update(UUID id, CreateTagRequest request) {
        Tag tag = tagRepository.findById(id).orElseThrow();
        tag.setName(request.getName());
        tag = tagRepository.save(tag);
        return toResponse(tag);
    }

    public void delete(UUID id) {
        tagRepository.deleteById(id);
    }

    private ListTagResponse toResponse(Tag tag) {
        ListTagResponse response = new ListTagResponse();
        response.setId(tag.getId());
        response.setName(tag.getName());
        return response;
    }
}
