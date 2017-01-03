package csmijo.com.citypicker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import csmijo.com.citypicker.R;

/**
 * Created by chengqianqian-xy on 2016/12/30.
 */

public class HotCityGridAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mCities;

    public HotCityGridAdapter(Context context) {
        this.mContext = context;

        this.mCities = new ArrayList<>();
        mCities.add("北京");
        mCities.add("上海");
        mCities.add("广州");
        mCities.add("深圳");
        mCities.add("杭州");
        mCities.add("南京");
        mCities.add("天津");
        mCities.add("武汉");
        mCities.add("重庆");
    }

    @Override
    public int getCount() {
        return mCities == null ? 0 : mCities.size();
    }

    @Override
    public String getItem(int position) {
        return mCities == null ? null : mCities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HotCityViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_hot_city_gridview, parent, false);
            viewHolder = new HotCityViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.tv_hot_city_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (HotCityViewHolder) convertView.getTag();
        }

        viewHolder.name.setText(mCities.get(position));
        return convertView;
    }

    public class HotCityViewHolder {
        TextView name;
    }
}
