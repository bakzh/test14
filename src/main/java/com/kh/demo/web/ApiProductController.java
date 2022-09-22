package com.kh.demo.web;

import com.kh.demo.dao.Product;
import com.kh.demo.svc.ProductSVC;
import com.kh.demo.web.api.ApiResponse;
import com.kh.demo.web.api.product.AddReq;
import com.kh.demo.web.api.product.EditReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ApiProductController {

  private final ProductSVC productSVC;

  //등록
  @PostMapping("/products")
  public ApiResponse<Long> save(@RequestBody AddReq addReq){

    Product product = new Product();
    BeanUtils.copyProperties(addReq,product);

    Long pid = productSVC.save(product);

    ApiResponse.Header header = new ApiResponse.Header("00","성공");
    ApiResponse<Long> response = new ApiResponse<>(header,pid);

    return response;
  }

  //조회
  @GetMapping("/products/{id}")
  public ApiResponse<Product> findById(@PathVariable("id") Long pid){

    Optional<Product> findedProduct = productSVC.findByProductId(pid);

    ApiResponse<Product> response = null;
    if (findedProduct.isPresent()){
      Product product = findedProduct.get();
      ApiResponse.Header header = new ApiResponse.Header("00","성공");
      response = new ApiResponse<>(header,product);
    }else{
      ApiResponse.Header header = new ApiResponse.Header("01","해당 상품이 없습니다");
      response = new ApiResponse<>(header,null);
    }
    return response;
  }

  //수정
  @PatchMapping("/products/{id}")
  public ApiResponse<Product> edit(@PathVariable("id") Long pid, @RequestBody EditReq editReq){

    ApiResponse<Product> response = null;
    Optional<Product> findedProduct = productSVC.findByProductId(pid);
    if(findedProduct.isEmpty()){
      ApiResponse.Header header = new ApiResponse.Header("01", "해당 상품이 없습니다");
      response = new ApiResponse<>(header,null);
      return response;
    }

    Product product = new Product();
    BeanUtils.copyProperties(editReq,product);

    productSVC.update(pid,product);

    ApiResponse.Header header = new ApiResponse.Header("00","성공");
    response = new ApiResponse<>(header,productSVC.findByProductId(pid).get());

    return response;
  }

  //삭제
  @DeleteMapping("/products/{id}")
  public ApiResponse<Product> delete(@PathVariable("id") Long pid){

    ApiResponse<Product> response = null;
    Optional<Product> findedProduct = productSVC.findByProductId(pid);
    if(findedProduct.isEmpty()){
      ApiResponse.Header header = new ApiResponse.Header("01", "해당 상품이 없습니다");
      response = new ApiResponse<>(header,null);
      return response;
    }

    productSVC.deleteByProductId(pid);

    ApiResponse.Header header = new ApiResponse.Header("00","성공");
    response = new ApiResponse<>(header,null);

    return response;
  }

  //목록
  @GetMapping("/products")
  public ApiResponse<List<Product>> listUp(){

    List<Product> list = productSVC.findAll();

    ApiResponse.Header header = new ApiResponse.Header("00","성공");
    ApiResponse<List<Product>> response = new ApiResponse<>(header, list);

    return response;
  }
}
