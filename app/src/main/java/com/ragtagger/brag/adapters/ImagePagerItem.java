package com.ragtagger.brag.adapters;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;

import com.ragtagger.brag.data.model.response.RImagePager;
import com.ragtagger.brag.utils.Utility;

/**
 * Created by alpesh.rathod on 3/1/2018.
 */

public class ImagePagerItem extends BaseObservable {

    private RImagePager item;
    private Context context;
    private int position;

    private ItemImageClickListener itemImageClickListener;

    public ImagePagerItem(RImagePager item, Context context, int position, ItemImageClickListener itemImageClickListener) {
        this.item = item;
        this.context = context;
        this.position = position;
        this.itemImageClickListener = itemImageClickListener;
    }

    public String getUrl() {
        return item.getUrl();
    }

    public String getId() {
        return item.getId();
    }

    public void setItem(RImagePager item) {
        this.item = item;
        notifyChange();
    }

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        Utility.imageSetCenterInside(imageView.getContext(), url, imageView);
    }

    public void onItemClick(View v) {
        itemImageClickListener.onItemClick(position, item);
    }

    public interface ItemImageClickListener {
        void onItemClick(int position, RImagePager item);

    }
}
