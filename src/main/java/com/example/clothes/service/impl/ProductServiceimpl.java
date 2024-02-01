package com.example.clothes.service.impl;

import com.example.clothes.dto.BasePage;
import com.example.clothes.dto.ProductDTO;
import com.example.clothes.entity.Image;
import com.example.clothes.entity.Product;
import com.example.clothes.entity.User;
import com.example.clothes.repository.ImageRepository;
import com.example.clothes.repository.ProductAttributeRepository;
import com.example.clothes.repository.ProductRepository;
import com.example.clothes.repository.UserRepository;
import com.example.clothes.service.ProductAttributeService;
import com.example.clothes.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
@RequiredArgsConstructor
public class ProductServiceimpl implements ProductService {
    private final ProductRepository productRepo;
    private final ModelMapper mapper = new ModelMapper();
    private final ProductAttributeService attributeService;
    private final UserRepository userRepo;
    private final ProductAttributeRepository attributeRepository;
    private final ImageRepository imageRepository;
    @Override
    public ProductDTO create(ProductDTO productDTO) {
        Product product = new Product();
        product = mapper.map(productDTO, Product.class);
        product.setStatus(1);
        return mapper.map(productRepo.save(product), ProductDTO.class);
    }
    @Override
    @Transactional
    public ProductDTO update(ProductDTO productDTO, Long productId) {
        Product product = productRepo.findById(productId).get();
        product = mapper.map(productDTO, Product.class);
        imageRepository.deleteByProductId(product.getId());
        if(productDTO.getImageLinks().size() > 0) {
            for(int i = 0; i < productDTO.getImageLinks().size(); i++) {
                Image image = new Image();
                image.setLink(productDTO.getImageLinks().get(i));
                image.setProductId(productId);
                imageRepository.save(image);
            }
        }
        product = productRepo.save(product);
        return getDetail(product.getId());
    }
    @Override
    public ProductDTO getDetail(Long productId) {
        Product product = productRepo.findById((productId)).get();
        List<Image> images = imageRepository.findByProductId(productId);

        ProductDTO productDTO = mapper.map(product,ProductDTO.class);
        productDTO.setAttributes(attributeService.getAllAttributeByProduct(productId));
        productDTO.setImageLinks(images.stream().map(item->item.getLink()).toList());
        return productDTO;
    }
    @Override
    public BasePage<ProductDTO> get(String name, String category, String code, Long userId, String fromTime, String toTime, Integer page, Integer pageSize) {

        Pageable pageable = PageRequest.of(page - 1, pageSize);

        User user = userRepo.findById(userId).get();
        Page<Product> productPage = productRepo.findAll( name,1, category,code,user.getOwnerId(),fromTime, toTime, pageable);
        BasePage<ProductDTO> dataPage = new BasePage<>();
        dataPage.setTotalElements(productPage.getTotalElements());
        dataPage.setTotalPages(productPage.getTotalPages());
        dataPage.setElements(productPage.getNumberOfElements());
        dataPage.setData(productPage.get().map(product -> mapper.map(product, ProductDTO.class)).toList());
        return dataPage;

    }
    @Override
    public List<String> getAllCatgories(Long userId) {
        User user = userRepo.findById(userId).get();
        return productRepo.findCategoryByOwnerId(user.getOwnerId());
    }
    @Override
    public Integer deleteProduct(Long productId) {
        Product product = productRepo.findById(productId).get();
        product.setStatus(0);
        productRepo.save(product);
        return 1;
    }

}
