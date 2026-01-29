package com.test.service.order.dto;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderCreateRequest {

    @Valid
    @NotNull(message = "주문자 정보는 필수입니다.")
    private ContactInfoRequest contactInfo;

    @Valid
    @NotNull(message = "주문 상품 정보는 필수입니다.")
    private ItemRequest items;

    @Getter
    @NoArgsConstructor
    @Setter
    public static class ContactInfoRequest {
        @NotBlank(message = "이메일은 필수입니다.")
        @Email(message = "이메일 형식이 올바르지 않습니다.")
        private String contactEmail;

        @NotBlank(message = "주문자 이름은 필수입니다.")
        private String contactName;

        @NotBlank(message = "휴대폰 번호는 필수입니다.")
        private String mobile;
    }

    @Getter
    @NoArgsConstructor
    @Setter
    public static class ItemRequest {
        @NotBlank(message = "상품 타입은 필수입니다.")
        private String itemType;

        @NotNull(message = "상품 ID는 필수입니다.")
        private Long id;
    }
}
