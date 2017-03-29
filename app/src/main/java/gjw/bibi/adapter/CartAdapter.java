package gjw.bibi.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Iterator;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import gjw.bibi.R;
import gjw.bibi.database.GoodsDAO;
import gjw.bibi.database.GoodsMiddleBean;
import gjw.bibi.view.AddSubView;

/**
 * Created by 皇上 on 2017/3/28.
 * QQ:519300884
 */

public class CartAdapter extends BaseAdapter {

    private final Context context;
    public List<GoodsMiddleBean> queryList;
    private final TextView tvShopcartTotal;
    private final CheckBox checkboxAll;
    private final CheckBox checkboxDeleteAll;
    private final GoodsDAO goodsDAO;


    public CartAdapter(Context context, List<GoodsMiddleBean> queryList, TextView tvShopcartTotal, CheckBox checkboxAll, CheckBox checkboxDeleteAll) {
        this.context = context;
        this.queryList = queryList;
        this.tvShopcartTotal = tvShopcartTotal;
        this.checkboxAll = checkboxAll;
        this.checkboxDeleteAll = checkboxDeleteAll;

        goodsDAO = new GoodsDAO(context);
        showTotalPrice();
    }

    public void showTotalPrice() {
        tvShopcartTotal.setText("合计：" + getTotalPrice());
    }

    private double getTotalPrice() {

        double totalPrice = 0;

        if (queryList != null && queryList.size() > 0) {
            for (int i = 0; i < queryList.size(); i++) {
                GoodsMiddleBean bean = queryList.get(i);
                if (bean.isChecked()) {
                    totalPrice += Double.parseDouble(bean.getPrice()) * bean.getNumber();
                }
            }
        }
        return totalPrice;
    }


    @Override
    public int getCount() {
        return queryList == null ? 0 : queryList.size();
    }

    @Override
    public Object getItem(int position) {
        return queryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_shop_cart, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final GoodsMiddleBean goodsMiddleBean = queryList.get(position);
        //设置选择状态
        viewHolder.cbGov.setChecked(goodsMiddleBean.isChecked());
        //图片
        Glide.with(context)
                .load(goodsMiddleBean.getFigure())
                .into(viewHolder.ivGov);
        //设置价格
        viewHolder.tvPriceGov.setText("￥" + goodsMiddleBean.getPrice());
        //设置名称
        viewHolder.tvDescGov.setText(goodsMiddleBean.getName());
        //设置数量
        viewHolder.addSubView.setValue(goodsMiddleBean.getNumber());

        //设置库存
        viewHolder.addSubView.setMaxValue(30);

        viewHolder.addSubView.setOnNumberChangeListener(new AddSubView.OnNumberChangeListener() {
            @Override
            public void OnNumberChange(int value) {
                //回调数量
                goodsMiddleBean.setNumber(value);

                goodsDAO.updata(goodsMiddleBean);

                showTotalPrice();
            }
        });


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, position);
                }
            }
        });
        return convertView;
    }

    //自定义接口
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int postion);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    //校验是否全选
    public void checkAll() {
        int number = 0;
        if (queryList != null && queryList.size() > 0) {
            for (int i = 0; i < queryList.size(); i++) {

                GoodsMiddleBean middleBean = queryList.get(i);

                if (!middleBean.isChecked()) {
                    //有一条没选中就不是全选
                    checkboxAll.setChecked(false);
                    checkboxAll.setChecked(false);
                } else {
                    //每选中一条 选中一条就加 1
                    number++;
                }
                //若选中的数与购物车内的商品类数相等 就是全选
                if (queryList.size() == number) {
                    checkboxAll.setChecked(true);
                    checkboxDeleteAll.setChecked(true);
                }
            }
        } else {
            checkboxAll.setChecked(true);
            checkboxDeleteAll.setChecked(true);
        }
    }

    //删除数据
    public void deleteData() {
        if (queryList != null && queryList.size() > 0) {
            for (Iterator iterator = queryList.iterator(); iterator.hasNext(); ) {
                GoodsMiddleBean goodsMiddleBean = (GoodsMiddleBean) iterator.next();
                if (goodsMiddleBean.isChecked()) {
                    int position = queryList.indexOf(goodsMiddleBean);
                    //内存中移除
                    iterator.remove();
                    //本地同步
                    goodsDAO.delete(goodsMiddleBean);
                    //删除后刷新页面
                    notifyDataSetChanged();
                }
            }
        }
    }

    public void checkAll_none(boolean isChecked) {
        if (queryList != null && queryList.size() > 0) {
            for (int i = 0; i < queryList.size(); i++) {
                GoodsMiddleBean goodsMiddleBean = queryList.get(i);
                //设置是否勾选状态
                goodsMiddleBean.setChecked(isChecked);
                checkboxAll.setChecked(isChecked);
                checkboxDeleteAll.setChecked(isChecked);
                //刷新
                notifyDataSetChanged();
            }
        }
    }

    static class ViewHolder {
        @InjectView(R.id.cb_gov)
        CheckBox cbGov;
        @InjectView(R.id.iv_gov)
        ImageView ivGov;
        @InjectView(R.id.tv_desc_gov)
        TextView tvDescGov;
        @InjectView(R.id.tv_price_gov)
        TextView tvPriceGov;
        @InjectView(R.id.addSubView)
        AddSubView addSubView;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);

        }
    }
}
