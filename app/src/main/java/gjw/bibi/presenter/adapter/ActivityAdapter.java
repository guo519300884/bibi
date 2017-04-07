package gjw.bibi.presenter.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import butterknife.ButterKnife;
import butterknife.InjectView;
import gjw.bibi.R;
import gjw.bibi.bean.ZoneTypeBean;

/**
 * Created by 皇上 on 2017/3/24.
 */

class ActivityAdapter extends RecyclerView.Adapter {
    private final Context context;
    private final ZoneTypeBean.DataBean dataBean
            ;
    private LayoutInflater inflater;

    public ActivityAdapter(Context context, ZoneTypeBean.DataBean dataBean) {
        this.context = context;
        this.dataBean = dataBean;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ActivityViewHolder(context, inflater.inflate(R.layout.item_ac, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ActivityViewHolder activityViewHolder = (ActivityViewHolder) holder;
        activityViewHolder.setData(dataBean);
    }


    public class ActivityViewHolder extends RecyclerView.ViewHolder {
        private final Context context;
        @InjectView(R.id.iv_areas_content_icon1)
        ImageView ivAreasContentIcon1;
        @InjectView(R.id.tv_areas_content_title1)
        TextView tvAreasContentTitle1;
        @InjectView(R.id.item_live_layout)
        CardView itemLiveLayout;

        public ActivityViewHolder(Context context, View view) {
            super(view);
            ButterKnife.inject(this, view);
            this.context = context;
        }

        public void setData(ZoneTypeBean.DataBean dataBean) {

            Glide.with(context)
                    .load(dataBean.getBody().get(getLayoutPosition()).getCover())
                    .into(ivAreasContentIcon1);

            tvAreasContentTitle1.setText(dataBean.getBody().get(getLayoutPosition()).getTitle());

            itemLiveLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "dddddddddd", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
