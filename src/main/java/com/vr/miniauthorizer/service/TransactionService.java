package com.vr.miniauthorizer.service;

import com.vr.miniauthorizer.model.TransactionModel;

public interface TransactionService {
    void performTransaction(TransactionModel transactionModel);
}
