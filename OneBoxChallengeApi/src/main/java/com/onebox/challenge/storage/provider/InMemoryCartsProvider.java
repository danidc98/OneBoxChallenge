package com.onebox.challenge.storage.provider;


import com.onebox.challenge.storage.model.CartObject;
import com.onebox.challenge.storage.model.ProductDAO;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


/**
 * This class defines the in-memory carts provider. The objects are stored
 * in RAM memory. The methods defined below ensure the isolation between the database object
 * and the objects returned to the controllers. For example, findById returns a copy of a cart so that
 * the object inside the static variable 'carts' isn´t modified from the controllers until
 * itś called the save method
 */
@Repository
public class InMemoryCartsProvider implements CartsProvider {

    private static volatile List<CartObject> carts = new ArrayList<CartObject>();


    @Override
    public void deleteByTimestampLessThan(long timestamp) {
        //Garbage collector will drop the filtered terms.after this process.
        carts = carts.parallelStream().filter(
                cart -> cart.getLastTimestamp() > timestamp
        ).collect(Collectors.toList());
    }

    @Override
    public CartObject save(CartObject cartIn) {
        int index = 0;
        for (CartObject cart : carts) {
            if (cart.getId().equals(cartIn.getId())) {
                break;
            }
            index += 1;
        }
        carts.add(index,cartIn);
        return cartIn;
    }


    @Override
    public void deleteById(String id) {
        int index = 0;
        for (CartObject cartObject : carts) {
            if (cartObject.getId().equals(id)) break;
            index += 1;
        }
        carts.remove(index);

    }


    @Override
    public Optional<CartObject> findById(String id) {
        CartObject cartObject;
        Optional<CartObject> opt = carts.parallelStream().filter(
                cart ->
                        cart.getId().equals(id)
        ).map(cart ->

                {
                    CartObject cartCopy = new CartObject();

                    List<ProductDAO> productsCopy = cart.getProducts()
                            .parallelStream()
                            .map(
                                    productDAO -> new ProductDAO().id(productDAO.getId()).amount(productDAO.getAmount()).description(productDAO.getDescription())

                            ).toList();

                    cartCopy.setProducts(productsCopy);
                    cartCopy.setId(cart.getId());
                    cartCopy.setLastTimestamp(cart.getLastTimestamp());
                    return cartCopy;
                }

        ).findFirst();
        cartObject = opt.orElse(null);
        return Optional.ofNullable(cartObject);
    }


}
