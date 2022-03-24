package com.ragtagger.brag.data.model.response;

import com.ragtagger.brag.data.model.datas.DataAddToCart;

import java.util.List;

/**
 * Created by alpesh.rathod on 3/9/2018.
 */

public class RAddToCart extends BaseResponse{

    private List<DataAddToCart> data;

    public List<DataAddToCart> getData() {
        return data;
    }
}
