package com.ragtagger.brag.ui.home.product.list.adapter;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;

import com.ragtagger.brag.data.model.datas.DataProductList;
import com.ragtagger.brag.utils.Utility;

/**
 * Created by alpesh.rathod on 2/19/2018.
 */

public class ItemProductViewModel extends BaseObservable {

    private DataProductList.Products mProduct;
    private int posistion;
    private Context mContext;
    private ItemProductViewModelListener mListener;

    private String productPrice;

    public ItemProductViewModel(Context mContext, DataProductList.Products mProduct, int posistion, ItemProductViewModelListener listener) {
        this.mProduct = mProduct;
        this.posistion = posistion;
        this.mContext = mContext;
        this.mListener = listener;
    }

    public String getNo() {
        return mProduct.getNo();
    }

    public boolean isAlreadyInCart() {
        return mProduct.isAlreadyInCart();
    }

    public String getDescription() {
        return mProduct.getDescription();
    }


    public String getImage() {
        if (mProduct.getImages() != null && mProduct.getImages().size() > 0)
            return mProduct.getImages().get(0);
        else return null;
        //return "https://www.dropbox.com/s/wefvp2rsqjv3gxj/black-printed-cage_front.jpg?dl=3";
    }

    public String getPrice() {
        return Utility.getIndianCurrencyPriceFormat(mProduct.getUnitPrice());
    }

    // Loading Image using Glide Library.
    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        Utility.imageSetCenterInside(imageView.getContext(), url, imageView,500,500);
    }

    public void setProduct(DataProductList.Products product) {
        this.mProduct = product;
        notifyChange();
    }

    public void onAddClick(View v) {
        mListener.OnAddClick(posistion, mProduct);
    }

    public void onCartClick(View v) {
        mListener.OnCartClick(posistion, mProduct);
    }

    public void OnListItemClick(View v) {
        mListener.OnListItemClick(posistion, mProduct);
    }

    public interface ItemProductViewModelListener {
        void OnAddClick(int position, DataProductList.Products product);

        void OnCartClick(int position, DataProductList.Products product);

        void OnListItemClick(int position, DataProductList.Products product);
    }
}
