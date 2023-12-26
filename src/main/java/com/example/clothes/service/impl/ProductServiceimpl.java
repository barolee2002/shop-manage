package com.example.clothes.service.impl;

import com.example.clothes.dto.BasePage;
import com.example.clothes.dto.ProductDTO;
import com.example.clothes.entity.Product;
import com.example.clothes.entity.User;
import com.example.clothes.repository.ProductRepository;
import com.example.clothes.repository.UserRepository;
import com.example.clothes.service.ProductAttributeService;
import com.example.clothes.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceimpl implements ProductService {
    private final ProductRepository productRepo;
    private final ModelMapper mapper = new ModelMapper();
    private final ProductAttributeService attributeService;
    private final UserRepository userRepo;
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
        return productDTO;
    }
    @Override
    public BasePage<ProductDTO> get(String name, String category, String code, Long userId, Integer page, Integer pageSize) {

        Pageable pageable = PageRequest.of(page - 1, pageSize);

        User user = userRepo.findById(userId).get();
        Page<Product> productPage = productRepo.findAll( name,1, category,code,user.getOwnerId(), pageable);
        BasePage<ProductDTO> dataPage = new BasePage<>();
        dataPage.setTotalElements(productPage.getTotalElements());
        dataPage.setTotalPages(productPage.getTotalPages());
        dataPage.setElements(productPage.getNumberOfElements());
        dataPage.setData(productPage.get().map(product -> mapper.map(product, ProductDTO.class)).toList());
        return dataPage;

    }

}
