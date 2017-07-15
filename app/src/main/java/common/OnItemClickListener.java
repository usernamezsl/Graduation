package common;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 作者: 张少林 on 2017/4/13 0013.
 * 邮箱:1083065285@qq.com
 */

public interface OnItemClickListener {
    void onItemClick(int position, View view, RecyclerView.ViewHolder vh);
}
