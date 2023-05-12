package com.ecommerce.library.service;

import com.ecommerce.library.dto.ProductDto;
import com.ecommerce.library.model.Product;
import com.ecommerce.library.repository.ProductRepository;
import com.ecommerce.library.utils.ImageUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ImageUpload imageUpload;
    public List<ProductDto> findAll() {
        List<Product> products = productRepository.findAll();
        return transfer(products);
    }

    public void save(MultipartFile image, ProductDto productDto) throws IOException {
        Product product = new Product();
        if(image == null) {
            product.setImage(null);
        } else {
            if(imageUpload.uploadImage(image)) {
                System.out.println("Upload successfully");
            }
            product.setImage(Base64.getEncoder().encodeToString(image.getBytes()));
        }
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setCostPrice(productDto.getCostPrice());
        product.setSalePrice(productDto.getSalePrice());
        product.setCurrentQuantity(productDto.getCurrentQuantity());
        product.setCategory(productDto.getCategory());
        product.set_deleted(false);
        product.set_activated(true);
        productRepository.save(product);
    }

    public void update(MultipartFile image, ProductDto productDto) throws IOException {
        Product product = new Product();
        if(image == null) {
            product.setImage(null);
        } else {
            if(imageUpload.uploadImage(image)) {
                System.out.println("Upload successfully");
            }
            product.setImage(Base64.getEncoder().encodeToString(image.getBytes()));
        }
        product.setName(productDto.getName());
        product.setCategory(productDto.getCategory());
        product.setDescription(productDto.getDescription());
        product.setCostPrice(productDto.getCostPrice());
        product.setSalePrice(productDto.getSalePrice());
        product.setCurrentQuantity(productDto.getCurrentQuantity());
        productRepository.save(product);
    }

    public ProductDto findById(Long id) {
        ProductDto productDto = new ProductDto();
        Product product = productRepository.findById(id).orElseThrow();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setCurrentQuantity(product.getCurrentQuantity());
        productDto.setCategory(product.getCategory());
        productDto.setSalePrice(product.getSalePrice());
        productDto.setCostPrice(product.getCostPrice());
        productDto.setImage(product.getImage());
        productDto.setDeleted(product.is_deleted());
        productDto.setActivated(product.is_activated());
        return productDto;
    }

    private List<ProductDto> transfer(List<Product> products) {
        List<ProductDto> productDtos = new ArrayList<>();
        for(Product product:products) {
            ProductDto productDto = new ProductDto();
            productDto.setId(product.getId());
            productDto.setName(product.getName());
            productDto.setDescription(product.getDescription());
            productDto.setCurrentQuantity(product.getCurrentQuantity());
            productDto.setCategory(product.getCategory());
            productDto.setSalePrice(product.getSalePrice());
            productDto.setCostPrice(product.getCostPrice());
            productDto.setImage(product.getImage());
            productDto.setDeleted(product.is_deleted());
            productDto.setActivated(product.is_activated());
            productDtos.add(productDto);
        }
        return productDtos;
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public void enableById(Long id) {
        Product product = productRepository.findById(id).orElseThrow();
        product.set_activated(true);
        productRepository.save(product);
    }

    public Page<ProductDto> pageProducts(int pageNumber) {
        PageRequest pageRequest = PageRequest.of(pageNumber, 5);
        List<ProductDto> productDtos = transfer(productRepository.findAll());
        return toPage(productDtos, pageRequest);
    }

    public Page<ProductDto> searchProducts(int pageNumber, String searchKey) {
        PageRequest pageRequest = PageRequest.of(pageNumber, 5);
        List<ProductDto> productDtos = transfer(productRepository.searchProductsList(searchKey));
        return toPage(productDtos, pageRequest);
    }

    public Page<ProductDto> toPage(List<ProductDto> productDtos, PageRequest pageRequest) {
        if(pageRequest.getOffset() >= productDtos.size()) {
            return Page.empty();
        }
        // getOffset() will be return the starting index of current page you indicate
        // getPageSize() will be return the number of items to be displayed per page
        int startIndex = (int) pageRequest.getOffset();
        int endIndex = ((pageRequest.getOffset() + pageRequest.getPageSize()) > productDtos.size())
                ? productDtos.size() : (int) (pageRequest.getOffset() + pageRequest.getPageSize());
        List<ProductDto> subList = productDtos.subList(startIndex,endIndex);
        return new PageImpl<ProductDto>(subList, pageRequest, productDtos.size());
    }


//    Customer
    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }
    public List<Product> listViewProducts() {
        return productRepository.listViewProducts();
    }
    public List<Product> getProductsInCategory(Long categoryId) {
        return productRepository.findProductInCategory(categoryId);
    }
    public List<Product> getRelatedProducts(Long categoryId) {
        return productRepository.getRelatedProducts(categoryId);
    }
    public Product getProductById(Long id) {
        return productRepository.getById(id);
    }
    public List<Product> findProductsInCategory(Long categoryId) {
        return productRepository.findProductInCategory(categoryId);
    }
    public List<Product> filterHighPrice() {
        return productRepository.filterHighPrice();
    }
    public List<Product> filterLowPrice() {
        return productRepository.filterLowPrice();
    }
}
