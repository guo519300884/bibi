package gjw.bibi.presenter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
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
import gjw.bibi.bean.LiveStreamingBean;

/**
 * Created by 皇上 on 2017/3/22.
 */

class RecommendhostAdapter extends BaseAdapter {


    private final Context context;
    private final List<LiveStreamingBean.DataBean.EntranceIconsBean> entranceIcons;
    private LayoutInflater inflater;

    public RecommendhostAdapter(Context context, List<LiveStreamingBean.DataBean.EntranceIconsBean> entranceIcons) {
        this.context = context;
        this.entranceIcons = entranceIcons;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return entranceIcons.size();
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
            convertView = inflater.inflate(R.layout.item_recommend, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        LiveStreamingBean.DataBean.EntranceIconsBean entranceIconsBean = entranceIcons.get(position);

        Glide.with(context).load(entranceIconsBean.getEntrance_icon().getSrc()).into(viewHolder.ivRecommend);
        viewHolder.tvRecommend.setText(entranceIconsBean.getName());

        return convertView;
    }


    static class ViewHolder {
        @InjectView(R.id.iv_recommend)
        ImageView ivRecommend;
        @InjectView(R.id.tv_recommend)
        TextView tvRecommend;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }


}
