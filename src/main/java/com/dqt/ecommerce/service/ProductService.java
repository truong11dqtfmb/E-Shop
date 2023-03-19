package com.dqt.ecommerce.service;

import com.dqt.ecommerce.dto.ProductDTO;
import com.dqt.ecommerce.model.Category;
import com.dqt.ecommerce.model.Product;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> findAll();

    Product save(ProductDTO productDTO) throws IOException;

    Product update(ProductDTO productDTO,Long id) throws IOException;

    Optional<Product> findById(Long id);

    Product deleteById(Long id);

    Product enabledById(Long id);

    ProductDTO convertToDTO(Long id);

    List<Product> search(String name);

    Page<Product> pagefindAll(int pageNumber, String sortField, String sortDir);

    Page<Product> pageSearch(String search,int pageNumber, String sortField, String sortDir);

    long countProduct();

    void deleteAllProduct();

    void enabledAllProduct();

    void saleAllProduct(int sale);

//======================================================================================================================
    List<Product> findAllActived();

    List<Product> findTop8();

    List<Product> findAllByCategoryId(long id);

    Page<Product> pagefindAllActived(int pageNumber, String sortField, String sortDir);

    Page<Product> pageSearchActived(String search,int pageNumber, String sortField, String sortDir);
}
