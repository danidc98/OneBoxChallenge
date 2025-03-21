package com.onebox.challenge.storage.provider;

import com.onebox.challenge.storage.model.CartObject;

import java.util.List;
import java.util.Optional;



public interface CartsProvider  {

    public void deleteByTimestampLessThan(long timestamp);

    public CartObject save(CartObject cart);

    public void deleteById(String id);


    public Optional<CartObject> findById(String id);
}
