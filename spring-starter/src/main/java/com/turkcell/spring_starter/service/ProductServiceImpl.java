package com.turkcell.spring_starter.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.turkcell.spring_starter.dto.CreateProductRequest;
import com.turkcell.spring_starter.dto.ListProductResponse;
import com.turkcell.spring_starter.entity.Category;
import com.turkcell.spring_starter.entity.Product;
import com.turkcell.spring_starter.repository.CategoryRepository;
import com.turkcell.spring_starter.repository.ProductRepository;

@Service
public class ProductServiceImpl {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public ListProductResponse create(CreateProductRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow();

        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setCategory(category);
        product = productRepository.save(product);

        return toResponse(product);
    }

    public List<ListProductResponse> getAll() {
        return productRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public ListProductResponse getById(UUID id) {
        Product product = productRepository.findById(id).orElseThrow();
        return toResponse(product);
    }

    public ListProductResponse update(UUID id, CreateProductRequest request) {
        Product product = productRepository.findById(id).orElseThrow();
        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow();

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setCategory(category);
        product = productRepository.save(product);

        return toResponse(product);
    }

    public void delete(UUID id) {
        productRepository.deleteById(id);
    }

    private ListProductResponse toResponse(Product product) {
        ListProductResponse response = new ListProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setCategoryId(product.getCategory().getId());
        return response;
    }
}
