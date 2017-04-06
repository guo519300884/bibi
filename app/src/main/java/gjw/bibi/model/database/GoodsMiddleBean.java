package gjw.bibi.model.database;

import java.io.Serializable;

/**
 * Created by 皇上 on 2017/3/28.
 */

public class GoodsMiddleBean implements Serializable {

    private int id;
    private String name;
    private String price;
    private String figure;
    //商品在购物车要购买的数量
    private int number = 1;
    private boolean isChecked = true;

    public GoodsMiddleBean() {
    }

    public GoodsMiddleBean(int id, int number, String price, String figure, String name) {
        this.id = id;
        this.price = price;
        this.figure = figure;
        this.name = name;
        this.number = number;

    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getFigure() {
        return figure;
    }

    public void setFigure(String figure) {
        this.figure = figure;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "GoodsMiddleBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", figure='" + figure + '\'' +
                ", number=" + number +
                '}';
    }


}
