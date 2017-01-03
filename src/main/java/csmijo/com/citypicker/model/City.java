package csmijo.com.citypicker.model;

/**
 * Created by chengqianqian-xy on 2016/12/29.
 */

public class City {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    private String name;
    private String pinyin;

    public City() {

    }

    public City(String name, String pinyin) {
        this.name = name;
        this.pinyin = pinyin;

    }
}
