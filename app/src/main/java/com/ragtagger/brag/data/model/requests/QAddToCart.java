package com.ragtagger.brag.data.model.requests;

/**
 * Created by alpesh.rathod on 3/9/2018.
 */

public class QAddToCart {

    private String itemId;
    private int quantity;
    private String id;

    public QAddToCart(String itemId, int quantity) {
        this.itemId = itemId;
        this.quantity = quantity;
    }

    public QAddToCart(String id,String itemId, int quantity) {
        this.id=id;
        this.itemId = itemId;
        this.quantity = quantity;
    }
}
