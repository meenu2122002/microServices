package com.example.ecommerce;


import com.example.ecommerce.exception.ProductPurchaseException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;



@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public Integer createProduct(ProductRequest request) {
        var product =productMapper.toProduct(request);
return productRepository.save(product).getId();

    }

    public List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> requestList) throws ProductPurchaseException {
   var productIds=requestList.stream()
           .map(ProductPurchaseRequest::productId).toList();
   var storedProducts=productRepository.findAllByIdInOrderById(productIds);

   if(storedProducts.size()!=requestList.size()){
       throw new ProductPurchaseException("One or more Product does not exist");
   }


   var storedRequest=requestList.stream().
           sorted(Comparator.comparing(ProductPurchaseRequest::productId))
           .toList();

   var purchasedProducts=new ArrayList<ProductPurchaseResponse>();


   for(int i=0;i<storedProducts.size();i++){
       var product=storedProducts.get(i);
       var productsRequest=storedRequest.get(i);
       if(product.getAvailableQuantity()<productsRequest.quantity()){
           throw new ProductPurchaseException("Insufficient stock quantity for product with ID :: "+productsRequest.productId());
       }
       var newAvailableQuantity= product.getAvailableQuantity()-productsRequest.quantity();
       product.setAvailableQuantity(newAvailableQuantity);
       productRepository.save(product);
       purchasedProducts.add(productMapper.toProductPurchaseResponse(product, productsRequest.quantity()));

   }

   return purchasedProducts;




    }

    public ProductResponse findById(Integer productId) {
       return productRepository.findById(productId)
                .map(productMapper::toProductResponse)
                .orElseThrow((() -> new EntityNotFoundException("Product with provided Id does not exist")));

    }

    public List<ProductResponse> findAll() {

        return productRepository.findAll()
                .stream()
                .map(productMapper::toProductResponse)
                .collect(Collectors.toList());



    }
}
