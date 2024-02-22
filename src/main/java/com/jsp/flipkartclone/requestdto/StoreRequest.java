package com.jsp.flipkartclone.requestdto;

import io.micrometer.common.lang.Nullable;
import lombok.Data;

@Data
public class StoreRequest {
 private String storeName;
 @Nullable
 private String description;
}
