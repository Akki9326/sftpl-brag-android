package com.pulse.brag.callback;

/**
 * Created by alpesh.rathod on 2/1/2018.
 */

public interface IOnItemClickListener<V> {

    /**
     *
     * @param pos represent item position
     * @param v represent item view
     */
    void onItemClick(int pos, V v);
}
