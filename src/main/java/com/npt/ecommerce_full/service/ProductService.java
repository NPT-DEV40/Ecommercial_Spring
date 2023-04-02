package com.npt.ecommerce_full.service;

import com.npt.ecommerce_full.dto.ProductDto;
import com.npt.ecommerce_full.model.Product;
import com.npt.ecommerce_full.repository.ProductRepository;
import com.npt.ecommerce_full.utils.ImageUpload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ImageUpload imageUpload;


    // MultipartFile is an interface in Spring Framework that represents an uploaded  file received in a multipart request
    private Product save(MultipartFile imageProduct, ProductDto productDto) throws IOException {
        try {
            Product product = new Product();
            if (imageProduct == null) {
                product.setImage(null);
            } else {
                if (imageUpload.uploadImage(imageProduct)) {
                    System.out.println("Upload image successfully");
                }
                product.setImage(Base64.getEncoder().encodeToString(imageProduct.getBytes()));
            }
            product.setId(productDto.getId());
            product.setName(productDto.getName());
            product.setCategory(productDto.getCategory());
            product.setDescription(productDto.getDescription());
            product.setCostPrice(productDto.getCostPrice());
            product.setSalePrice(product.getSalePrice());
            product.setCurrentQuantity(productDto.getCurrentQuantity());
            return productRepository.save(product);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Product update(MultipartFile imageProduct, ProductDto productDto) throws IOException {
        Product product = productRepository.findById(productDto.getId()).orElseThrow();
        if (imageProduct == null) {
            product.setImage(productDto.getImage());
        } else {
            if (!imageUpload.checkExisted(imageProduct)) {
                imageUpload.uploadImage(imageProduct);
            }
            product.setImage(Base64.getEncoder().encodeToString(imageProduct.getBytes()));
        }
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setCategory(productDto.getCategory());
        product.setCostPrice(productDto.getCostPrice());
        product.setSalePrice(productDto.getSalePrice());
        product.setCurrentQuantity(productDto.getCurrentQuantity());
        return productRepository.save(product);
    }

    public List<ProductDto> findAll() {
        List<Product> productList = productRepository.findAll();
        return transferList(productList);
    }

    public ProductDto findById(Integer id) {
        Product product = productRepository.findById(id).orElseThrow();
        return transferDto(product);
    }
    public void deleteById(Integer id) {
        Product product = productRepository.findById(id).orElseThrow();
        product.setDeleted(true);
        product.setActivated(false);
        productRepository.save(product);
    }

    public void enableById(Integer id) {
        Product product = productRepository.findById(id).orElseThrow();
        product.setDeleted(false);
        product.setActivated(true);
        productRepository.save(product);
    }

    public ProductDto getById(Integer id) {
        Product product = productRepository.findById(id).orElseThrow();
        ProductDto productDto = transferDto(product);
        return productDto;
    }

    private ProductDto transferDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setCategory(product.getCategory());
        productDto.setImage(product.getImage());
        productDto.setCostPrice(product.getCostPrice());
        productDto.setSalePrice(product.getSalePrice());
        productDto.setCurrentQuantity(product.getCurrentQuantity());
        return productDto;
    }

    private List<ProductDto> transferList(List<Product> productList) {
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : productList) {
            ProductDto productDto = transferDto(product);
            productDtos.add(productDto);
        }
        return productDtos;
    }
}
