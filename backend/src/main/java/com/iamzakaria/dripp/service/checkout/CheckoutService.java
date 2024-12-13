package com.iamzakaria.dripp.service.checkout;

import com.iamzakaria.dripp.dto.Purchase;
import com.iamzakaria.dripp.dto.PurchaseResponse;

public interface CheckoutService {
    PurchaseResponse placeOrder(Purchase purchase);
}
