package csmijo.com.citypicker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import csmijo.com.citypicker.R;
import csmijo.com.citypicker.model.City;
import csmijo.com.citypicker.utils.CommonViewHolder;

/**
 * Created by chengqianqian-xy on 2016/12/29.
 */

public class ResultAdapter extends BaseAdapter {

    private Context mContext;
    private List<City> mCities;

    public ResultAdapter(Context context, List<City> cities) {
        this.mCities = cities;
        this.mContext = context;
    }

    public void changeData(List<City> cityList) {
        if (mCities == null) {
            mCities = cityList;
        } else {
            mCities.clear();
            mCities.addAll(cityList);
        }

        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return mCities == null ? 0 : mCities.size();
    }

    @Override
    public City getItem(int position) {
        return mCities == null ? null : mCities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_search_result_listview, parent, false);
        }

        TextView tv_name = CommonViewHolder.get(convertView, R.id.tv_item_result_listView_name);

        tv_name.setText(mCities.get(position).getName());
        return convertView;
    }
}
