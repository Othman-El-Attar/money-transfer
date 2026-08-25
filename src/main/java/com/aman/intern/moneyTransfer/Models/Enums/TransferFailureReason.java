package com.aman.intern.moneyTransfer.Models.Enums;

public enum TransferFailureReason {
    SENDER_ACCOUNT_NOT_FOUND,
    RECEIVER_ACCOUNT_NOT_FOUND,
    INVALID_AMOUNT,
    INSUFFICIENT_BALANCE,
    RECEIVER_USER_NOT_FOUND,
    SENDER_USER_NOT_FOUND,
    INVALID_TRANSFER_TYPE
}
