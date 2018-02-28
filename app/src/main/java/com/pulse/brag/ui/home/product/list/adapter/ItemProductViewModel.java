package com.pulse.brag.ui.home.product.list.adapter;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;

import com.pulse.brag.data.model.DummeyDataRespone;
import com.pulse.brag.utils.Utility;

/**
 * Created by alpesh.rathod on 2/19/2018.
 */

public class ItemProductViewModel extends BaseObservable {

    private DummeyDataRespone mProduct;
    private int posistion;
    private Context mContext;
    private ItemProductViewModelListener mListener;

    private String productPrice;

    public ItemProductViewModel(Context mContext, DummeyDataRespone mProduct, int posistion, ItemProductViewModelListener listener) {
        this.mProduct = mProduct;
        this.posistion = posistion;
        this.mContext = mContext;
        this.mListener = listener;
    }

    public int getProductId() {
        return mProduct.getId();
    }

    public String getFirstName() {
        return mProduct.getFirst_name() + " " + mProduct.getLast_name();
    }

    public String getLastName() {
        return mProduct.getLast_name();
    }

    public String getThumbImg() {
        return mProduct.getAvatar();
    }

    public String getPrice() {
        return Utility.getIndianCurrencyPriceFormat(999);
    }

    // Loading Image using Glide Library.
    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        Utility.imageSet(imageView.getContext(), url, imageView);
    }

    public void setProduct(DummeyDataRespone product) {
        this.mProduct = product;
        notifyChange();
    }

    public void onAddClick(View v) {
        mListener.OnAddClick(posistion, mProduct);
    }

    public void onCartClick(View v){
        mListener.OnCartClick(posistion,mProduct);
    }

    public void OnListItemClick(View v) {
        mListener.OnListItemClick(posistion, mProduct);
    }

    public interface ItemProductViewModelListener {
        void OnAddClick(int position, DummeyDataRespone product);

        void OnCartClick(int position, DummeyDataRespone product);

        void OnListItemClick(int position, DummeyDataRespone product);
    }
}
