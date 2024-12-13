package com.iamzakaria.dripp.service.checkout;

import com.iamzakaria.dripp.dao.UserRepository;
import com.iamzakaria.dripp.dto.Purchase;
import com.iamzakaria.dripp.dto.PurchaseResponse;
import com.iamzakaria.dripp.entity.Order;
import com.iamzakaria.dripp.entity.OrderItem;
import com.iamzakaria.dripp.entity.User;
import com.iamzakaria.dripp.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
@Service
public class CheckoutServiceImpl implements CheckoutService{
    @Autowired
    private UserRepository userRepository;
    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {

        // retrieve the order info from dto
        Order order = purchase.getOrder();

        // generate tracking number
        String orderTrackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);

        // populate order with orderItems
        Set<OrderItem> orderItems = purchase.getOrderItems();
        orderItems.forEach(order::add);

        // populate order with status and shippingAddress
        order.setStatus(Status.PENDING);
        order.setShippingAddress(purchase.getShippingAddress());

        // populate user with order
        User user = purchase.getUser();

        // check if this is an existing user
        String userEmail = user.getEmail();

        User userFromDB = userRepository.findByEmail(userEmail);

        if (userFromDB != null) {
            // we found them ... let's assign them accordingly
            user = userFromDB;
        }

        user.add(order);

        // save to the database
        userRepository.save(user);

        // return a response
        return new PurchaseResponse(orderTrackingNumber);
    }
    private String generateOrderTrackingNumber() {
        return UUID.randomUUID().toString();
    }
}
