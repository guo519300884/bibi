package gjw.bibi.adapter;

import android.content.Context;
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
import gjw.bibi.bean.TopicBean;

/**
 * Created by 皇上 on 2017/3/21.
 */

public class TopicAdapter extends BaseAdapter {
    private final Context context;
    private final List<TopicBean.ListBean> topicBeanList;

    public TopicAdapter(Context context, List<TopicBean.ListBean> topicBeanList) {
        this.context = context;
        this.topicBeanList = topicBeanList;
    }

    @Override
    public int getCount() {
        return topicBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return topicBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_topic, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        TopicBean.ListBean listBean = topicBeanList.get(position);


        Glide.with(context).load(listBean.getCover()).into(viewHolder.ivTopic);

        viewHolder.tvTopic.setText(listBean.getTitle());

        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.iv_topic)
        ImageView ivTopic;
        @InjectView(R.id.tv_topic)
        TextView tvTopic;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
