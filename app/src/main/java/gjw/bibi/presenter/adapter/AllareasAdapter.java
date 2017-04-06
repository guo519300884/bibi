package gjw.bibi.presenter.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
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
import gjw.bibi.bean.LiveStreamingBean;
import gjw.bibi.videoplayer.DanmkuVideoActivity;

/**
 * Created by 皇上 on 2017/3/22.
 */

public class AllareasAdapter extends BaseAdapter {

    public static final String VIDEO = "video";
    public static final String VT = "视频标题";
    private final List<LiveStreamingBean.DataBean.PartitionsBean.LivesBean> lives;
    private final Context context;
    private LayoutInflater inflater;
    public int size;
    private Intent intent;

    public AllareasAdapter(Context context, List<LiveStreamingBean.DataBean.PartitionsBean.LivesBean> lives) {
        this.context = context;
        this.lives = lives;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 4;
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
            convertView = inflater.inflate(R.layout.item_content, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final LiveStreamingBean.DataBean.PartitionsBean.LivesBean livesBean = lives.get(position);

        Glide.with(context).load(livesBean.getCover().getSrc()).into(viewHolder.ivAreasContentIcon1);
        viewHolder.tvAreasContentTitle1.setText(livesBean.getTitle());
        viewHolder.tvAreasContentName1.setText(livesBean.getOwner().getName());
        viewHolder.tvAreasContentWatch1.setText(String.valueOf(livesBean.getOnline()));

        viewHolder.itemLiveLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String playurl = livesBean.getPlayurl();
                String title = livesBean.getTitle();

                intent = new Intent(context, DanmkuVideoActivity.class);
                intent.putExtra(VIDEO, playurl);
                intent.putExtra(VT, title);
                context.startActivity(intent);
//                intent = new Intent(context, VideoPlayActivity.class);
//                context.startActivity(intent);

                Toast.makeText(context, "0000000000000", Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }

    class ViewHolder {
        @InjectView(R.id.iv_areas_content_icon1)
        ImageView ivAreasContentIcon1;
        @InjectView(R.id.tv_areas_content_title1)
        TextView tvAreasContentTitle1;
        @InjectView(R.id.tv_areas_content_name1)
        TextView tvAreasContentName1;
        @InjectView(R.id.tv_areas_content_watch1)
        TextView tvAreasContentWatch1;
        @InjectView(R.id.item_live_layout)
        CardView itemLiveLayout;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
