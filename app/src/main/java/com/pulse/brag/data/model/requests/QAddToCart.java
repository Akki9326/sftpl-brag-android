package com.pulse.brag.data.model.requests;

/**
 * Created by alpesh.rathod on 3/9/2018.
 */

public class QAddToCart {

    private String itemId;
    private int quantity;

    public QAddToCart(String itemId, int quantity) {
        this.itemId = itemId;
        this.quantity = quantity;
    }
}
