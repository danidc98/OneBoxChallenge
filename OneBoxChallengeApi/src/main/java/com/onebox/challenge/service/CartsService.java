package com.onebox.challenge.service;

import com.onebox.challenge.DTO.CartItems;
import com.onebox.challenge.DTO.Product;
import com.onebox.challenge.exceptions.CartNotFoundException;
import com.onebox.challenge.storage.model.CartObject;
import com.onebox.challenge.storage.model.ProductDAO;
import com.onebox.challenge.storage.provider.CartsProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * This class defines the CartsService, which uses a general CartsProvider, whose technology could evolve
 * in future releases. Currently, the only available option is InMemoryCartsProvider. This class
 * is responsible for calling the methods of the CartsProvider and raise exceptions when the resources aren´t available.
 */
@Service
@EnableScheduling
public class CartsService {

    private CartsProvider cartsProvider;
    
    @Value("${expiration.millis}")
    private long expirationMillis;

    public CartsService(CartsProvider cartsProvider) {
        this.cartsProvider = cartsProvider;
    }

    public CartItems getCartInfo(String id) throws CartNotFoundException {

        CartObject cartObject;
        try {
            cartObject = cartsProvider.findById(id).get();
            if (cartObject.getLastTimestamp() < System.currentTimeMillis() - expirationMillis) {
                cartsProvider.deleteById(id);
                throw new CartNotFoundException(id);
            }
        } catch (NoSuchElementException ex) {
            throw new CartNotFoundException(id);
        }
        List<Product> products = cartObject.getProducts().parallelStream().map(
                productDAO -> new Product().id(productDAO.getId()).amount(productDAO.getAmount()).description(productDAO.getDescription())
        ).collect(Collectors.toList());

        return new CartItems().items(products);
    }

    public String createCart() {
        String randomId = UUID.randomUUID().toString();
        CartObject cartObject = new CartObject();
        cartObject.setProducts(new ArrayList<ProductDAO>());
        cartObject.setId(randomId);
        cartObject.setLastTimestamp(System.currentTimeMillis());
        cartsProvider.save(cartObject);
        return randomId;
    }


    @Scheduled(fixedRateString= "${deletion.period}", initialDelayString="${deletion.period}")
    public void deleteExpiredCarts() {
        cartsProvider.deleteByTimestampLessThan(System.currentTimeMillis() - expirationMillis);
    }

    public void deleteCart(String id) throws CartNotFoundException {
        if (cartsProvider.findById(id).isEmpty()) throw new CartNotFoundException(id);
        cartsProvider.deleteById(id);
    }

    /**
     * Adds products to cart if the cart exists and if it hasn´t expired. If it has expired, the cart will be deleted.
     *
     * @param id
     * @param products
     * @return
     * @throws CartNotFoundException
     */
    public boolean addProductsToCart(String id, List<Product> products) throws CartNotFoundException {
        CartObject cartObject;
        try {
            cartObject = cartsProvider.findById(id).get();
            if (cartObject.getLastTimestamp() < System.currentTimeMillis() - expirationMillis) {
                cartsProvider.deleteById(id);
                throw new CartNotFoundException(id);
            }
        } catch (NoSuchElementException ex) {
            throw new CartNotFoundException(id);
        }
        List<ProductDAO> newProductsList = new ArrayList<>(cartObject.getProducts());
        //Parallel stream to speed-up the search (though, normally, a client shouldn´t add so many products to its list).
        products.parallelStream().forEach(
                product -> {
                    AtomicBoolean alreadyInCart = new AtomicBoolean(false);
                    cartObject.getProducts().parallelStream().forEach(productDAO -> {
                        //If product exists in the cart, we sum the amounts
                        if (productDAO.getId().equals(product.getId())) {
                            alreadyInCart.set(true);
                            productDAO.setAmount(productDAO.getAmount() + product.getAmount());
                        }
                    });
                    //If the product wasn´t in the cart, we add it.
                    if (!alreadyInCart.get())
                        newProductsList.add(new ProductDAO().amount(product.getAmount()).id(product.getId()).description(product.getDescription()));
                }

        );
        cartObject.setProducts(newProductsList);
        cartObject.setLastTimestamp(System.currentTimeMillis());
        cartsProvider.save(cartObject);

        return true;
    }


}
