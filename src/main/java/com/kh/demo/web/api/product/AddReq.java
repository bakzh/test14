package com.kh.demo.web.api.product;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AddReq {
  @NotBlank
  private String pname;
  @NotNull
  private Long count;
  @NotNull
  private Long price;
}
