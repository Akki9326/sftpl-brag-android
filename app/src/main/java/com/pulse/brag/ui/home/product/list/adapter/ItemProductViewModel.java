package com.pulse.brag.ui.home.product.list.adapter;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;

import com.pulse.brag.pojo.DummeyDataRespone;
import com.pulse.brag.utils.Utility;

/**
 * Created by alpesh.rathod on 2/19/2018.
 */

public class ItemProductViewModel extends BaseObservable {

    private DummeyDataRespone mProduct;
    private int posistion;
    private Context mContext;
    private ItemProductViewModelListener mListener;

    public ItemProductViewModel(Context mContext,DummeyDataRespone mProduct,int posistion,ItemProductViewModelListener listener) {
        this.mProduct = mProduct;
        this.posistion=posistion;
        this.mContext = mContext;
        this.mListener=listener;
    }

    public int getProductId() {
        return mProduct.getId();
    }

    public String getFirstName() {
        return mProduct.getFirst_name()+ " "+mProduct.getLast_name();
    }

    public String getLastName() {
        return mProduct.getLast_name();
    }

    public String getThumbImg() {
        return mProduct.getAvatar();
    }

    public String getPrice(){
        return "Rs 500.00";
    }

    // Loading Image using Glide Library.
    @BindingAdapter("imageUrl") public static void setImageUrl(ImageView imageView, String url){
        Utility.imageSet(imageView.getContext(),url,imageView);
    }

    public void setProduct(DummeyDataRespone product){
        this.mProduct=product;
        notifyChange();
    }

    public void onAddClick(View v){
        mListener.OnAddClick(posistion,mProduct);
    }

    public void OnListItemClick(View v){
        mListener.OnListItemClick(posistion,mProduct);
    }

    public interface ItemProductViewModelListener {
        void OnAddClick(int position,DummeyDataRespone product);
        void OnListItemClick(int position,DummeyDataRespone product);
    }
}
