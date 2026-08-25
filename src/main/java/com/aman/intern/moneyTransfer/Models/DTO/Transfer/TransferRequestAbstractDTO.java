package com.aman.intern.moneyTransfer.Models.DTO.Transfer;

import com.aman.intern.moneyTransfer.Models.Enums.TransferTypeEnum;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "transferType",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(
                value = InternalSelfTransferRequestDTO.class,
                name = "INTERNAL_SELF"
        ),

        @JsonSubTypes.Type(
                value = InternalUserTransferRequestDTO.class,
                name = "INTERNAL_USER"
        ),

        @JsonSubTypes.Type(
                value = ExternalUserTransferRequestDTO.class,
                name = "EXTERNAL"
        )
})
public abstract class TransferRequestAbstractDTO {

    @NotNull
    @Positive
    private BigDecimal amount;

    @NotNull
    private TransferTypeEnum transferType;
}