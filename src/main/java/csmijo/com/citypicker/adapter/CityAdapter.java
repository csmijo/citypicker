package csmijo.com.citypicker.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import csmijo.com.citypicker.R;
import csmijo.com.citypicker.model.City;
import csmijo.com.citypicker.model.LocateState;
import csmijo.com.citypicker.utils.PinyinUtils;
import csmijo.com.citypicker.view.WrapHeightGridView;

/**
 * Created by chengqianqian-xy on 2016/12/29.
 */

public class CityAdapter extends BaseAdapter {

    private static final int VIEW_TYPE_COUNT = 3;

    private Context mContext;
    private List<City> mCities;
    private HashMap<String, Integer> letterIndexs;
    private String[] sections;
    private OnCityClickListner mOnCityClickListner;
    private int locateState = LocateState.LOCATING;
    private String locatedCity;


    public CityAdapter(Context context, List<City> cities) {
        this.mCities = cities;
        this.mContext = context;
        if (mCities == null) {
            mCities = new ArrayList<>();
        }

        mCities.add(0, new City("定位", "0"));
        mCities.add(1, new City("热门", "1"));
        int size = mCities.size();
        letterIndexs = new HashMap<>();
        sections = new String[size];
        for (int i = 0; i < size; i++) {
            // 当前城市拼音首字母
            String currentLetter = PinyinUtils.getFirstLetter(mCities.get(i).getPinyin());
            // 上个首字母，如果不存在则设置为""  记录以某个首字母开头的城市的第一个位置
            String previousLetter = (i >= 1 ? PinyinUtils.getFirstLetter(mCities.get(i - 1).getPinyin()) : "");
            if (!TextUtils.equals(currentLetter, previousLetter)) {
                letterIndexs.put(currentLetter, i);
                sections[i] = currentLetter;
            }
        }
    }


    /**
     * 更新定位状态
     *
     * @param state
     * @param city
     */
    public void updateLocateState(int state, String city) {
        this.locateState = state;
        this.locatedCity = city;
        notifyDataSetChanged();
    }


    /**
     * 获取字母索引的位置
     *
     * @param letter
     * @return
     */
    public int getLetterPosition(String letter) {
        Integer integer = letterIndexs.get(letter);
        return integer == null ? -1 : integer;
    }


    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        return position < VIEW_TYPE_COUNT - 1 ? position : VIEW_TYPE_COUNT - 1;
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
        CityViewHolder holder;

        int viewType = getItemViewType(position);
        switch (viewType) {
            case 0:
                Log.e("cityAdapter", "getView: viewType = " + viewType);
                //定位
                convertView = LayoutInflater.from(mContext).inflate(R.layout.view_locate_city, parent, false);
                ViewGroup container = (ViewGroup) convertView.findViewById(R.id.layout_locate);
                TextView state = (TextView) convertView.findViewById(R.id.tv_located_city);
                switch (locateState) {
                    case LocateState.LOCATING:
                        state.setText(mContext.getString(R.string.cp_locating));
                        break;
                    case LocateState.FAILED:
                        state.setText(mContext.getString(R.string.cp_located_failed));
                        break;
                    case LocateState.SUCCESS:
                        state.setText(locatedCity);
                        break;
                }

                container.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (locateState == LocateState.FAILED) {
                            // 重新定位
                            if (mOnCityClickListner != null) {
                                mOnCityClickListner.onLocationClick();
                            } else if (locateState == LocateState.SUCCESS) {
                                // 返回定位城市
                                if (mOnCityClickListner != null) {
                                    mOnCityClickListner.onCityClick(locatedCity);
                                }
                            }
                        }
                    }
                });


                break;
            case 1:
                Log.e("cityAdapter", "getView: viewType = " + viewType);
                // 热门
                convertView = LayoutInflater.from(mContext).inflate(R.layout.view_hot_city, parent, false);
                WrapHeightGridView gridView = (WrapHeightGridView) convertView.findViewById(R.id.gridview_hot_city);
                final HotCityGridAdapter hotCityGridAdapter = new HotCityGridAdapter(mContext);
                gridView.setAdapter(hotCityGridAdapter);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (mOnCityClickListner != null) {
                            mOnCityClickListner.onCityClick(hotCityGridAdapter.getItem(position));
                        }
                    }
                });
                break;
            case 2:
                // 所有
                Log.e("cityAdapter", "getView: viewType = " + viewType);
                if (convertView == null) {
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.item_city_listview, parent, false);
                    holder = new CityViewHolder();
                    holder.letter = (TextView) convertView.findViewById(R.id.tv_item_city_listview_letter);
                    holder.name = (TextView) convertView.findViewById(R.id.tv_item_city_listview_name);
                    convertView.setTag(holder);
                } else {
                    holder = (CityViewHolder) convertView.getTag();
                }

                if (position >= 1) {
                    final String cityName = mCities.get(position).getName();
                    holder.name.setText(cityName);
                    // ???作用???
                    String currentLetter = PinyinUtils.getFirstLetter(mCities.get(position).getPinyin());
                    String previousLetter = (position >= 1 ? PinyinUtils.getFirstLetter(mCities.get(position - 1).getPinyin()) : "");
                    if (!TextUtils.equals(currentLetter, previousLetter)) {
                        holder.letter.setVisibility(View.VISIBLE);
                        holder.letter.setText(currentLetter);
                    } else {
                        holder.letter.setVisibility(View.GONE);
                    }

                    holder.name.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mOnCityClickListner != null) {
                                mOnCityClickListner.onCityClick(cityName);
                            }
                        }
                    });
                }
                break;
        }
        return convertView;
    }


    private class CityViewHolder {
        TextView letter;
        TextView name;
    }

    public void setOnCityClickListner(OnCityClickListner onCityClickListner) {
        mOnCityClickListner = onCityClickListner;
    }


    public interface OnCityClickListner {
        void onCityClick(String name);

        void onLocationClick();
    }
}
