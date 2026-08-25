package com.aman.intern.moneyTransfer.Models.Records;

import com.aman.intern.moneyTransfer.Models.Enums.StatusEnum;
import com.aman.intern.moneyTransfer.Models.Enums.TransferFailureReason;

public record TransferResultRecord(
        StatusEnum status,
        TransferFailureReason failureReason
) {
}
