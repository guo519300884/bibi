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
import gjw.bibi.utils.TimeUtils;

/**
 * Created by 皇上 on 2017/3/23.
 */

public class ToThemAdapter extends BaseAdapter {
    private final Context context;
    private List<ToThemBean.ResultBean.PreviousBean.ListBean> previousBean;

    public ToThemAdapter(Context context, List<ToThemBean.ResultBean.PreviousBean.ListBean> listBeen) {
        this.context = context;
        this.previousBean = listBeen;
    }


    @Override
    public int getCount() {
        return previousBean == null ? 0 : previousBean.size();
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

        ToThemBean.ResultBean.PreviousBean.ListBean resultBean = previousBean.get(position);

        Glide.with(context).load(resultBean.getCover()).into(viewHolder.ivAreasContentIcon1);
        viewHolder.tvAreasContentTitle1.setText(resultBean.getTitle());
        viewHolder.tvAreasContentName1.setText("更新至第" + resultBean.getNewest_ep_index() + "话");

        int i = Integer.parseInt(resultBean.getFavourites());

        TimeUtils timeUtils = new TimeUtils();
        String play = timeUtils.play(i);
        viewHolder.tothemlike.setText(play + "人追番");

        viewHolder.itemLiveLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "番剧", Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

    public void setDateDrama(List<ToThemBean.ResultBean.PreviousBean.ListBean> previousBean) {
        this.previousBean = previousBean;
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
