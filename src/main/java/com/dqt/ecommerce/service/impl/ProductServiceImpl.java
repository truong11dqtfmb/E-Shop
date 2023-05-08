package com.dqt.ecommerce.service.impl;

import com.dqt.ecommerce.constant.SystemConstant;
import com.dqt.ecommerce.dto.ProductDTO;
import com.dqt.ecommerce.model.Product;
import com.dqt.ecommerce.repository.CategoryRepository;
import com.dqt.ecommerce.repository.ProductRepository;
import com.dqt.ecommerce.service.ProductService;
import com.dqt.ecommerce.util.FileUploadUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

//======================================================================================================================

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product save(ProductDTO productDTO) throws IOException {
//        Product product = modelMapper.map(productDTO, Product.class);
        Product product = new Product();
        product.setTitle(productDTO.getTitle());
        product.setDescription(productDTO.getDescription());
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setSale(productDTO.getSale());
        product.setCategory(categoryRepository.findById(productDTO.getCategoryId()).get());
        if (productDTO.getSale() != 0) {
            product.setCost(productDTO.getPrice() - (productDTO.getPrice() * productDTO.getSale() / 100));
        } else {
            product.setCost(productDTO.getPrice());
        }
        product.setActived(true);
        product.setDeleted(false);
        product.setImage(FileUploadUtil.convertFormMultipartFileToString(productDTO.getImage()));
        return productRepository.save(product);
    }

    @Override
    public Product update(ProductDTO productDTO, Long id) throws IOException {
        Optional<Product> pro = productRepository.findById(id);

        if (pro.isPresent()) {
//            Product product =  modelMapper.map(productDTO, Product.class);
            Product product = pro.get();
            product.setId(pro.get().getId());
            product.setSale(productDTO.getSale());
            product.setPrice(productDTO.getPrice());
            product.setCategory(categoryRepository.findById(productDTO.getCategoryId()).get());
            if (productDTO.getSale() != 0) {
                product.setCost(productDTO.getPrice() - (productDTO.getPrice() * productDTO.getSale() / 100));
            } else {
                product.setCost(productDTO.getPrice());
            }
            if (productDTO.getImage().isEmpty()) {
                product.setImage(pro.get().getImage());
            } else {
                product.setImage(FileUploadUtil.convertFormMultipartFileToString(productDTO.getImage()));
            }
            return productRepository.save(product);
        }
        return null;
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public ProductDTO convertToDTO(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            ProductDTO productDTO = modelMapper.map(product.get(), ProductDTO.class);
            productDTO.setCategoryId(product.get().getCategory().getId());
            return productDTO;
        }
        return null;
    }

    @Override
    public List<Product> search(String name) {
        return productRepository.findByNameContaining(name);
    }

    @Override
    public Page<Product> pagefindAll(int pageNumber, String sortField, String sortDir) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNumber - 1, SystemConstant.PAGE_SIZE, sort);

        return productRepository.findAll(pageable);
    }

    @Override
    public Page<Product> pageSearch(String search, int pageNumber, String sortField, String sortDir) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNumber - 1, SystemConstant.PAGE_SIZE, sort);

        return productRepository.search(search, pageable);
    }

    @Override
    public long countProduct() {
        return productRepository.count();
    }

    @Override
    public void deleteAllProduct() {
        productRepository.findAll().forEach(product -> {
            product.setActived(false);
            product.setDeleted(true);
            productRepository.save(product);
        });
    }

    @Override
    public void enabledAllProduct() {
        productRepository.findAll().forEach(product -> {
            product.setActived(true);
            product.setDeleted(false);
            productRepository.save(product);
        });
    }

    @Override
    public void saleAllProduct(int sale) {
        productRepository.findAll().forEach(product -> {
            product.setSale(sale);
            product.setCost(product.getPrice() - (product.getPrice() * sale / 100));
            productRepository.save(product);
        });
    }




    @Override
    public Product deleteById(Long id) {
        Optional<Product> product = findById(id);
        if (product.isPresent()) {
            product.get().setDeleted(true);
            product.get().setActived(false);
            productRepository.save(product.get());
            return product.get();
        }
        return null;
    }

    @Override
    public Product enabledById(Long id) {
        Optional<Product> product = findById(id);
        if (product.isPresent()) {
            product.get().setDeleted(false);
            product.get().setActived(true);
            productRepository.save(product.get());
            return product.get();
        }
        return null;
    }

//======================================================================================================================
    @Override
    public List<Product> findAllActived() {
        return productRepository.findByIsActivedTrueAndIsDeletedFalse();
    }





    @Override
    public List<Product> findTop8() {
        return productRepository.findByTop8();
    }



    @Override
    public List<Product> findAllByCategoryId(long id, int pageno, int pagesize) {

        Pageable page = PageRequest.of(pageno,pagesize);


        return productRepository.findByCategoryId(id,page);
    }

    @Override
    public Page<Product> pagefindAllActived(int pageNumber, String sortField, String sortDir) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNumber - 1, SystemConstant.PAGE_SIZE, sort);

        return productRepository.findAllByIsActivedTrueAndIsDeletedFalse(pageable);
    }

    @Override
    public Page<Product> pageSearchActived(String search, int pageNumber, String sortField, String sortDir) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNumber - 1, SystemConstant.PAGE_SIZE, sort);

        return productRepository.searchAllActived(search, pageable);
    }


}
