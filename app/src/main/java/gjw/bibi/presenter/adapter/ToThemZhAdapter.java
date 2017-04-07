package gjw.bibi.presenter.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import gjw.bibi.R;
import gjw.bibi.bean.ToThemBean;


/**
 * Created by 皇上 on 2017/3/23.
 */

public class ToThemZhAdapter extends BaseAdapter {

    private final Context context;
    private List<ToThemBean.ResultBean.SerializingBean> serializingBeen;
    private int w;
    private int q;

    public ToThemZhAdapter(Context context, List<ToThemBean.ResultBean.SerializingBean> serializingBeen) {
        this.context = context;
        this.serializingBeen = serializingBeen;
    }

    @Override
    public int getCount() {
        return serializingBeen == null ? 0 : serializingBeen.size();
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
            convertView = View.inflate(context, R.layout.item_tothem_content, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ToThemBean.ResultBean.SerializingBean serializingBean = serializingBeen.get(position);

        Glide.with(context).load(serializingBean.getCover()).into(viewHolder.ivAreasContentIcon1);
        viewHolder.tvAreasContentTitle1.setText(serializingBean.getTitle());
        viewHolder.tvAreasContentName1.setText("更新至第" + serializingBean.getNewest_ep_index() + "话");

        int i = Integer.parseInt(serializingBean.getFavourites());

        if (i >= 10000) {
            w = i / 10000;
        }
        if (i >= 1000) {
            q = i % 10000 / 1000;
        } else {
            viewHolder.tothemlike.setText(i + "万人追番");
        }
        viewHolder.tothemlike.setText(w + "." + q + "万人追番");

        viewHolder.itemLiveLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, "漫画", Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

    public void setDateCartoon(List<ToThemBean.ResultBean.SerializingBean> serializingBeen) {
        this.serializingBeen = serializingBeen;

    }

    static class ViewHolder {
        @InjectView(R.id.iv_areas_content_icon1)
        ImageView ivAreasContentIcon1;
        @InjectView(R.id.tothemlike)
        TextView tothemlike;
        @InjectView(R.id.tv_areas_content_title1)
        TextView tvAreasContentTitle1;
        @InjectView(R.id.tv_areas_content_name1)
        TextView tvAreasContentName1;
        @InjectView(R.id.item_live_layout)
        CardView itemLiveLayout;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
