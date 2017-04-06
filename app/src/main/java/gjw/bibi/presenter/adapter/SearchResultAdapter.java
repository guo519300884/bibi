package gjw.bibi.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import gjw.bibi.R;
import gjw.bibi.bean.TagSearchBean;
import gjw.bibi.utils.TimeUtils;

/**
 * Created by 皇上 on 2017/3/29.
 * 作者：郭建伟
 * QQ:519300884
 */

public class SearchResultAdapter extends BaseAdapter {

    private final Context context;
    private final List<TagSearchBean.DataBean.ItemsBean.ArchiveBean> archiveBeanList;

    public SearchResultAdapter(Context context, List<TagSearchBean.DataBean.ItemsBean.ArchiveBean> archiveBeanList) {
        this.context = context;
        this.archiveBeanList = archiveBeanList;
    }

    @Override
    public int getCount() {
        return archiveBeanList == null ? 0 : archiveBeanList.size();
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
            convertView = View.inflate(context, R.layout.item_search_result, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        TagSearchBean.DataBean.ItemsBean.ArchiveBean archiveBean = archiveBeanList.get(position);

        Glide.with(context).load(archiveBean.getCover()).into(viewHolder.ivSrTp);

        viewHolder.tvSrTitle.setText(archiveBean.getTitle());
        viewHolder.tvSrUp.setText(archiveBean.getAuthor());

        int play = archiveBean.getPlay();
        TimeUtils timeUtils = new TimeUtils();
        String p = timeUtils.play(play);
        viewHolder.tvSrPaly.setText(p);

        viewHolder.tvSrDm.setText(String.valueOf(archiveBean.getDanmaku()));
        viewHolder.tvSrTime.setText(archiveBean.getDuration());

        viewHolder.cvSrSrcv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "播放视频", Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.iv_sr_tp)
        ImageView ivSrTp;
        @InjectView(R.id.cv_sr_srcv)
        CardView cvSrSrcv;
        @InjectView(R.id.tv_sr_time)
        TextView tvSrTime;
        @InjectView(R.id.tv_sr_title)
        TextView tvSrTitle;
        @InjectView(R.id.tv_sr_up)
        TextView tvSrUp;
        @InjectView(R.id.tv_sr_paly)
        TextView tvSrPaly;
        @InjectView(R.id.tv_sr_dm)
        TextView tvSrDm;
        @InjectView(R.id.ll_sr_item)
        LinearLayout llSrItem;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
