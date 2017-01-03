package csmijo.com.citypicker.utils;

import android.util.SparseArray;
import android.view.View;


/**
 * Created by chengqianqian-xy on 2017/1/3.
 *
 * 通用的 ViewHolder 工具类
 * Ref: http://mp.weixin.qq.com/s/j36r-y60F82sVDnkdt3F7g
 */

public class CommonViewHolder {
    public static <T extends View> T get(View convertView, int rId) {
        SparseArray<View> viewHolder = (SparseArray<View>) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new SparseArray<View>();
            convertView.setTag(viewHolder);
        }

        View childView = viewHolder.get(rId);
        if (childView == null) {
            childView = convertView.findViewById(rId);
            viewHolder.put(rId, childView);
        }

        return (T) childView;
    }
}
