package gjw.bibi.presenter.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import gjw.bibi.R;
import gjw.bibi.bean.GoodsBean;
import gjw.bibi.model.database.GoodsMiddleBean;
import gjw.bibi.view.activity.GoodsInfoActivity;

/**
 * Created by 皇上 on 2017/3/28.
 */

public class GoodsAdapter extends BaseAdapter {

    public static final String GOODS = "goods";
    private final Context context;
    private final List<GoodsBean.ResultBean.RecordsBean> recordsBeen;
    private Intent intent;

    public GoodsAdapter(Context context, List<GoodsBean.ResultBean.RecordsBean> recordsBeen) {
        this.context = context;
        this.recordsBeen = recordsBeen;
    }

    @Override
    public int getCount() {
        return recordsBeen.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_goods, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final GoodsBean.ResultBean.RecordsBean bean = recordsBeen.get(position);

        Glide.with(context)
                .load(bean.getImgUrl())
                .into(viewHolder.ivPriceIcon);

        viewHolder.tvPriceTitle.setText(bean.getTitle());
        viewHolder.tvPrice.setText("￥" + bean.getSalvePrice());


        final GoodsMiddleBean goodsMiddleBean = new GoodsMiddleBean();
        goodsMiddleBean.setFigure(bean.getImgUrl());
        goodsMiddleBean.setName(bean.getTitle());
        goodsMiddleBean.setPrice(bean.getSalvePrice() + "");
        goodsMiddleBean.setId(bean.getSkuId());


        viewHolder.itemPriceLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent = new Intent(context, GoodsInfoActivity.class);
                intent.putExtra(GOODS, goodsMiddleBean);
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.iv_price_icon)
        ImageView ivPriceIcon;
        @InjectView(R.id.tv_price_title)
        TextView tvPriceTitle;
        @InjectView(R.id.tv_price)
        TextView tvPrice;
        @InjectView(R.id.item_price_layout)
        CardView itemPriceLayout;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
