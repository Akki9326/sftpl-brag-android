package com.pulse.brag.data.model.requests;

/**
 * Created by alpesh.rathod on 3/12/2018.
 */

public class QGetFilter {
    String category;
    String subCategory;
    String seasonCode;

    public void setCategory(String category) {
        this.category = category;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public void setSeasonCode(String seasonCode) {
        this.seasonCode = seasonCode;
    }
}
