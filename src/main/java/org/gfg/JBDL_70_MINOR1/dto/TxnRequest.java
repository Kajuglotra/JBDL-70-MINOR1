package org.gfg.JBDL_70_MINOR1.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class TxnRequest {
    @NotBlank(message = "user phoneNo should not be blank")
    private String userPhoneNo;
    @NotBlank(message = "book No should not be blank")
    private String bookNo;

}
