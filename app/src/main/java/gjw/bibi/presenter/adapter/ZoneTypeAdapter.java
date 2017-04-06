package gjw.bibi.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import gjw.bibi.R;
import gjw.bibi.activity.CircumActivity;
import gjw.bibi.activity.TopicActivity;
import gjw.bibi.bean.ZoneHeadBean;
import gjw.bibi.bean.ZoneTypeBean;
import gjw.bibi.view.myview.MyGridView;

import static gjw.bibi.adapter.TopicAdapter.CIR;
import static gjw.bibi.adapter.TopicAdapter.CIRCUM;

/**
 * Created by 皇上 on 2017/3/23.
 */

public class ZoneTypeAdapter extends RecyclerView.Adapter {


    public static final int HEAD = 0;
    public static final int REGION = 1;
    public static final int TOPIC = 2;
    public static final int ACTIVITY = 3;

    public int currentType = 0;

    private final List<ZoneHeadBean.DataBean> zoneHeadBeanData;
    private final List<ZoneTypeBean.DataBean> zoneTypeBeanData;
    private final Context context;
    private LayoutInflater inflater;
    private ZoneTypeBean.DataBean dataBean;
    private Intent intent;


    public ZoneTypeAdapter(Context context, List<ZoneHeadBean.DataBean> zoneHeadBeanData, List<ZoneTypeBean.DataBean> zoneTypeBeanData) {
        this.context = context;
        this.zoneHeadBeanData = zoneHeadBeanData;
        this.zoneTypeBeanData = zoneTypeBeanData;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemCount() {
        return zoneTypeBeanData.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position != 0) {
            dataBean = zoneTypeBeanData.get(position - 1);
        }

        if (position == 0) {
            currentType = HEAD;
        } else if ("region".equals(dataBean.getType())) {
            currentType = REGION;
        } else if ("topic".equals(dataBean.getType())) {
            currentType = TOPIC;
        } else if ("activity".equals(dataBean.getType())) {
            currentType = ACTIVITY;
        }
        return currentType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEAD) {
            return new HeadViewHolder(context, inflater.inflate(R.layout.item_zone_head, null));
        } else if (viewType == REGION) {
            return new RegionViewHolder(context, inflater.inflate(R.layout.item_zone_region, null));
        } else if (viewType == TOPIC) {
            return new TopicViewHolder(context, inflater.inflate(R.layout.item_zone_banner, null));
        } else if (viewType == ACTIVITY) {
            return new ActivityViewHolder(context, inflater.inflate(R.layout.item_zone_viewpager, null));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == HEAD) {
            HeadViewHolder headViewHolder = (HeadViewHolder) holder;
            headViewHolder.setData(zoneHeadBeanData);
        } else if (getItemViewType(position) == REGION) {
            RegionViewHolder regionViewHolder = (RegionViewHolder) holder;
            regionViewHolder.setData(dataBean);
        } else if (getItemViewType(position) == TOPIC) {
            TopicViewHolder topicViewHolder = (TopicViewHolder) holder;
            topicViewHolder.setData(dataBean);
        } else if (getItemViewType(position) == ACTIVITY) {
            ActivityViewHolder activityViewHolder = (ActivityViewHolder) holder;
            activityViewHolder.setData(dataBean);
        }
    }


    public class HeadViewHolder extends RecyclerView.ViewHolder {

        private final Context context;
        @InjectView(R.id.gv_zone)
        MyGridView gvZone;

        public HeadViewHolder(Context context, View view) {
            super(view);
            ButterKnife.inject(this, view);
            this.context = context;
        }

        public void setData(List<ZoneHeadBean.DataBean> zoneHeadBeanData) {

            ZoneHeadAdapter zoneHeadAdapter = new ZoneHeadAdapter(context, zoneHeadBeanData);
            gvZone.setAdapter(zoneHeadAdapter);
        }
    }

    public class RegionViewHolder extends RecyclerView.ViewHolder {

        private final Context context;
        @InjectView(R.id.iv_areas_icon)
        ImageView ivAreasIcon;
        @InjectView(R.id.tv_areas_title)
        TextView tvAreasTitle;
        @InjectView(R.id.zone_head)
        LinearLayout zoneHead;
        @InjectView(R.id.gv_zone_region)
        MyGridView gvZoneRegion;
        @InjectView(R.id.btn_areas_content)
        Button btnAreasContent;
        @InjectView(R.id.item_live_layout)
        CardView itemLiveLayout;
        @InjectView(R.id.tv_areas_content_refresh)
        TextView tvAreasContentRefresh;
        @InjectView(R.id.bn_bn)
        Banner bnBn;

        public RegionViewHolder(Context context, View view) {
            super(view);
            ButterKnife.inject(this, view);
            this.context = context;
        }

        public void setData(ZoneTypeBean.DataBean dataBean) {

            tvAreasTitle.setText(dataBean.getTitle());

            ZoneRegionAdapter zoneRegionAdapter = new ZoneRegionAdapter(context, dataBean);
            gvZoneRegion.setAdapter(zoneRegionAdapter);

            String btn = dataBean.getTitle();
            String substring = btn.substring(0, btn.length() - 1);
            btnAreasContent.setText("更多" + substring);

            tvAreasContentRefresh.setText("666条动态，点击更新！");
            for (int i = 0; i < zoneHeadBeanData.size(); i++) {
                ZoneHeadBean.DataBean logoBean = zoneHeadBeanData.get(i);

                if (substring.equals(logoBean.getName())) {
                    Glide.with(context).load(logoBean.getLogo()).into(ivAreasIcon);
                }
            }
            ZoneTypeBean.DataBean.BannerBean bannerBean = dataBean.getBanner();

            if (bannerBean != null && bannerBean.getBottom().size() > 0) {

                bnBn.setVisibility(View.VISIBLE);

                List<String> images = new ArrayList<>();

                for (int i = 0; i < bannerBean.getBottom().size(); i++) {
                    images.add(bannerBean.getBottom().get(i).getImage());
                }

                bnBn.setIndicatorGravity(BannerConfig.RIGHT);
                bnBn.setDelayTime(4000);

                bnBn.setImages(images).setImageLoader(new ImageLoader() {
                    @Override
                    public void displayImage(Context context, Object path, ImageView imageView) {
                        Glide.with(context)
                                .load(path)
                                .crossFade()
                                .into(imageView);
                    }
                }).start();

                bnBn.setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {


                        Toast.makeText(context, "版纳", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                bnBn.setVisibility(View.GONE);
            }
        }

        @OnClick({R.id.goin, R.id.zone_head, R.id.btn_areas_content})
        public void onViewClicked(View view) {
            switch (view.getId()) {
                case R.id.goin:
                    Toast.makeText(context, "58456", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.zone_head:
                    Toast.makeText(context, "24215", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btn_areas_content:
                    Toast.makeText(context, "2431", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    public class TopicViewHolder extends RecyclerView.ViewHolder {
        private final Context context;
        @InjectView(R.id.iv_areas_icon)
        ImageView ivAreasIcon;
        @InjectView(R.id.tv_areas_title)
        TextView tvAreasTitle;
        @InjectView(R.id.goin)
        Button goin;
        @InjectView(R.id.zone_head)
        LinearLayout zoneHead;
        @InjectView(R.id.bn_zone_topic)
        Banner bnZoneTopic;

        public TopicViewHolder(Context context, View view) {
            super(view);
            ButterKnife.inject(this, view);
            this.context = context;
        }

        public void setData(ZoneTypeBean.DataBean dataBean) {
            tvAreasTitle.setText("话题");
            final List<ZoneTypeBean.DataBean.BodyBean> dataBeanBody = dataBean.getBody();

            List images = new ArrayList();
            for (int i = 0; i < dataBeanBody.size(); i++) {
                images.add(dataBeanBody.get(i).getCover());
            }

            bnZoneTopic.setIndicatorGravity(BannerConfig.RIGHT);
            bnZoneTopic.setDelayTime(4000);

            bnZoneTopic.setImages(images).setImageLoader(new ImageLoader() {
                @Override
                public void displayImage(Context context, Object path, ImageView imageView) {
                    Glide.with(context)
                            .load(path)
                            .crossFade()
                            .into(imageView);
                }
            }).start();

            bnZoneTopic.setOnBannerListener(new OnBannerListener() {

                @Override
                public void OnBannerClick(int position) {

                    String uri = dataBeanBody.get(position).getUri();
                    String title = dataBeanBody.get(position).getTitle();

                    intent = new Intent(context, CircumActivity.class);
                    intent.putExtra(CIRCUM, uri);
                    intent.putExtra(CIR, title);
                    context.startActivity(intent);
                }
            });
        }

        @OnClick({R.id.goin, R.id.zone_head})
        public void onViewClicked(View view) {
            switch (view.getId()) {
                case R.id.goin:
                    intent = new Intent(context, TopicActivity.class);
                    context.startActivity(intent);
                    break;
                case R.id.zone_head:
                    intent = new Intent(context, TopicActivity.class);
                    context.startActivity(intent);
                    break;
            }
        }
    }

    public class ActivityViewHolder extends RecyclerView.ViewHolder {

        private final Context context;
        @InjectView(R.id.rv_zone_activity)
        RecyclerView rvZoneActivity;

        public ActivityViewHolder(Context context, View view) {
            super(view);
            ButterKnife.inject(this, view);
            this.context = context;
        }

        public void setData(ZoneTypeBean.DataBean dataBean) {
            ActivityAdapter activityAdapter = new ActivityAdapter(context, dataBean);
            rvZoneActivity.setAdapter(activityAdapter);

            rvZoneActivity.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

        }
    }
}
