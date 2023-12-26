package com.example.clothes.service.impl;

import com.example.clothes.dto.ProductDTO;
import com.example.clothes.entity.Product;
import com.example.clothes.repository.ProductRepository;
import com.example.clothes.service.ProductAttributeService;
import com.example.clothes.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceimpl implements ProductService {
    private final ProductRepository productRepo;
    private final ModelMapper mapper = new ModelMapper();
    private final ProductAttributeService attributeService;
    @Override
    public ProductDTO create(ProductDTO productDTO) {
        Product product = new Product();
        product = mapper.map(productDTO, Product.class);
        product.setStatus(1);
        return mapper.map(productRepo.save(product), ProductDTO.class);
    }
    @Override
    public ProductDTO update(ProductDTO productDTO, Long productId) {
        Product product = productRepo.findById(productId).get();
        product = mapper.map(productDTO, Product.class);
        return mapper.map(productRepo.save(product), ProductDTO.class);
    }
    @Override
    public ProductDTO getDetail(Long productId) {
        Product product = productRepo.findById((productId)).get();
        ProductDTO productDTO = mapper.map(product,ProductDTO.class);
        productDTO.setAttributes(attributeService.getAllAttributeByProduct(productId));
        return productDTO;
    }

}
