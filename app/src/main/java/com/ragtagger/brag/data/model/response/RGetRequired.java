package com.ragtagger.brag.data.model.response;

import com.ragtagger.brag.data.model.datas.DataGetRequired;
import com.ragtagger.brag.data.model.datas.DataMyOrder;

public class RGetRequired extends BaseResponse {
    private DataGetRequired data;
    public DataGetRequired getData() {
        return data;
    }

    public void setData(DataGetRequired data) {
        this.data = data;
    }
}
