package com.ecommerce.library.service;

import com.ecommerce.library.model.CartItem;
import com.ecommerce.library.model.Product;
import com.ecommerce.library.model.ShoppingCart;
import com.ecommerce.library.model.User;
import com.ecommerce.library.repository.CartItemRepository;
import com.ecommerce.library.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ShoppingCartService {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;

    public ShoppingCart addItemToCart(Product product, int quantity, User user) {
        ShoppingCart shoppingCart = user.getShoppingCart();
        if(shoppingCart == null) {
            shoppingCart = new ShoppingCart();
        }
        Set<CartItem> cartItems = shoppingCart.getCartItems();
        CartItem cartItem = findCartItem(cartItems, product.getId());
        if(cartItems == null) {
            cartItems = new HashSet<>();
            cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setCart(shoppingCart);
            cartItem.setPrice(quantity * product.getCostPrice());
            cartItem.setQuantity(quantity);
            cartItems.add(cartItem);
            cartItemRepository.save(cartItem);
        } else {
            if (cartItem == null) {
                cartItem = new CartItem();
                cartItem.setProduct(product);
                cartItem.setCart(shoppingCart);
                cartItem.setPrice(quantity * product.getCostPrice());
                cartItem.setQuantity(quantity);
                cartItems.add(cartItem);
                cartItemRepository.save(cartItem);
            } else {
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                cartItem.setPrice(cartItem.getPrice() + ( quantity * product.getCostPrice()));
                cartItemRepository.save(cartItem);
            }
        }
        shoppingCart.setCartItems(cartItems);
        shoppingCart.setUser(user);
        shoppingCart.setTotalPrice(TotalPrice(cartItems));
        shoppingCart.setTotalQuantity(TotalQuantity(cartItems));
        return shoppingCartRepository.save(shoppingCart);
    }

    public ShoppingCart updateItemInCart(Product product, int quantity, User user) {
        ShoppingCart shoppingCart = user.getShoppingCart();
        Set<CartItem> cartItems = shoppingCart.getCartItems();
        CartItem cartItem = findCartItem(cartItems, product.getId());

        cartItem.setQuantity(quantity);
        cartItem.setPrice(product.getCostPrice() * quantity);
        cartItemRepository.save(cartItem);

        cartItems.add(cartItem);
        shoppingCart.setCartItems(cartItems);
        shoppingCart.setTotalQuantity(TotalQuantity(cartItems));
        shoppingCart.setTotalPrice(TotalPrice(cartItems));
        return shoppingCartRepository.save(shoppingCart);
    }

    public ShoppingCart deleteItemInCart(Product product, User user) {
        ShoppingCart shoppingCart = user.getShoppingCart();
        Set<CartItem> cartItems = shoppingCart.getCartItems();
        CartItem cartItem = findCartItem(cartItems, product.getId());

        cartItems.remove(cartItem);
        cartItemRepository.delete(cartItem);

        shoppingCart.setCartItems(cartItems);
        shoppingCart.setTotalPrice(TotalPrice(cartItems));
        shoppingCart.setTotalQuantity(TotalQuantity(cartItems));
        return shoppingCartRepository.save(shoppingCart);
    }

    private int TotalQuantity(Set<CartItem> cartItems) {
        int totalItems = 0;
        for(CartItem cartItem : cartItems) {
            totalItems += cartItem.getQuantity();
        }
        return totalItems;
    }

    private double TotalPrice(Set<CartItem> cartItems) {
        double totalPrice = 0.0;
        for(CartItem cartItem : cartItems) {
            totalPrice += cartItem.getPrice();
        }
        return totalPrice;
    }

    private CartItem findCartItem(Set<CartItem> cartItems, long id) {
        if(cartItems == null) {
            return null;
        }
        CartItem item = null;
        for(CartItem cartItem : cartItems) {
            if(cartItem.getProduct().getId() == id) {
                item = cartItem;
            }
        }
        return item;
    }
}
