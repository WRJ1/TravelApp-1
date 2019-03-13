package com.example.travelapp;

import android.view.View;

/**
 * RecyclerView条目点击接口
 * Created by kang on 2018/9/19.
 */

public interface OnRecyclerViewClickListener {
    void onItemClickListener(View view);
    void onItemLongClickListener(View view);

}
