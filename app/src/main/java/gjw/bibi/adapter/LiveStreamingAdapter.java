package gjw.bibi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
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
import gjw.bibi.bean.LiveStreamingBean;
import gjw.bibi.view.MyGridView;

/**
 * Created by 皇上 on 2017/3/22.
 */

public class LiveStreamingAdapter extends RecyclerView.Adapter {

    //轮播图
    public static final int BANNER = 0;
    //推荐主播
    public static final int RECOMMENDHOST = 1;
    //绘画区
    public static final int DRAWING = 2;


    //当前类型
    private int currentType = BANNER;


    private final Context context;
    private final LiveStreamingBean.DataBean liveStreamingBeanData;
    private LayoutInflater inflater;
    private AllareasAdapter allareasAdapter;

    public LiveStreamingAdapter(Context context, LiveStreamingBean.DataBean liveStreamingBeanData) {
        this.context = context;
        this.liveStreamingBeanData = liveStreamingBeanData;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemCount() {
        return 11;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == BANNER) {
            currentType = BANNER;
        } else if (position == RECOMMENDHOST) {
            currentType = RECOMMENDHOST;
        } else if (position == DRAWING) {
            currentType = DRAWING;
        }
        return currentType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == BANNER) {
            return new BannerViewHolder(context, inflater.inflate(R.layout.item_banner, null));
        } else if (viewType == RECOMMENDHOST) {
            return new RecommendhostViewHolder(context, inflater.inflate(R.layout.item_recommendhost, null));
        } else if (viewType == DRAWING) {
            return new DrawingViewHolder(context, inflater.inflate(R.layout.item_drawing, null));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == BANNER) {
            BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
            bannerViewHolder.setData(liveStreamingBeanData.getBanner());
        } else if (getItemViewType(position) == RECOMMENDHOST) {
            RecommendhostViewHolder recommendhostViewHolder = (RecommendhostViewHolder) holder;
            recommendhostViewHolder.setData(liveStreamingBeanData.getEntranceIcons());
        } else if (getItemViewType(position) == DRAWING) {
            DrawingViewHolder drawingViewHolder = (DrawingViewHolder) holder;
            drawingViewHolder.setData(liveStreamingBeanData.getPartitions());
        }
    }


    public class BannerViewHolder extends RecyclerView.ViewHolder {
        private final Context context;
        @InjectView(R.id.ls_banner)
        Banner lsBanner;
        @InjectView(R.id.attention)
        LinearLayout attention;
        @InjectView(R.id.centre)
        LinearLayout centre;
        @InjectView(R.id.smallvideo)
        LinearLayout smallvideo;
        @InjectView(R.id.seek)
        LinearLayout seek;
        @InjectView(R.id.classify)
        LinearLayout classify;

        public BannerViewHolder(Context context, View view) {
            super(view);
            ButterKnife.inject(this, view);
            this.context = context;
        }

        public void setData(List<LiveStreamingBean.DataBean.BannerBean> banner) {
            List<String> images = new ArrayList<>();

            if (banner.size() == 1) {
                for (int i = 0; i < 2; i++) {
                    images.add(banner.get(0).getImg());
                }
            }


            lsBanner.setIndicatorGravity(BannerConfig.RIGHT);
            lsBanner.setDelayTime(4000);

            lsBanner.setImages(images).setImageLoader(new ImageLoader() {
                @Override
                public void displayImage(Context context, Object path, ImageView imageView) {
                    Glide.with(context)
                            .load(path)
                            .crossFade()
                            .into(imageView);
                }
            }).start();

            lsBanner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    Toast.makeText(context, "版纳", Toast.LENGTH_SHORT).show();

                }
            });
        }

        @OnClick({R.id.attention, R.id.centre, R.id.smallvideo, R.id.seek, R.id.classify})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.attention:
                    Toast.makeText(context, "22", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.centre:
                    Toast.makeText(context, "33", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.smallvideo:
                    Toast.makeText(context, "44", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.seek:
                    Toast.makeText(context, "55", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.classify:
                    Toast.makeText(context, "66", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    public class RecommendhostViewHolder extends RecyclerView.ViewHolder {

        private final Context context;
        @InjectView(R.id.recommendthehost)
        LinearLayout recommendthehost;
        @InjectView(R.id.gv_recommendhost)
        GridView gvRecommendhost;
        private RecommendhostAdapter recommendhostAdapter;

        public RecommendhostViewHolder(Context context, View view) {
            super(view);
            ButterKnife.inject(this, view);
            this.context = context;
        }

        public void setData(List<LiveStreamingBean.DataBean.EntranceIconsBean> entranceIcons) {

            recommendhostAdapter = new RecommendhostAdapter(context, entranceIcons);
            gvRecommendhost.setAdapter(recommendhostAdapter);

            gvRecommendhost.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Toast.makeText(context, "点我呀", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public class DrawingViewHolder extends RecyclerView.ViewHolder {
        private final Context context;
        @InjectView(R.id.iv_areas_icon)
        ImageView ivAreasIcon;
        @InjectView(R.id.tv_areas_title)
        TextView tvAreasTitle;
        @InjectView(R.id.tv_areas_number)
        TextView tvAreasNumber;
        @InjectView(R.id.gv_head)
        LinearLayout gvHead;
        @InjectView(R.id.gv_areas_drawing)
        MyGridView gvAreasDrawing;
        @InjectView(R.id.btn_areas_content)
        Button btnAreasContent;
        @InjectView(R.id.tv_areas_content_refresh)
        TextView tvAreasContentRefresh;

        public DrawingViewHolder(Context context, View view) {
            super(view);
            ButterKnife.inject(this, view);
            this.context = context;
        }

        public void setData(final List<LiveStreamingBean.DataBean.PartitionsBean> partitions) {

            Glide.with(context).load(partitions.get(getLayoutPosition() - 2).getPartition().getSub_icon().getSrc()).into(ivAreasIcon);
            tvAreasTitle.setText(partitions.get(getLayoutPosition() - 2).getPartition().getName());
            tvAreasNumber.setText("当前" + partitions.get(getLayoutPosition() - 2).getPartition().getCount() + "个直播");

            allareasAdapter = new AllareasAdapter(context, partitions.get(getLayoutPosition() - 2).getLives());
            gvAreasDrawing.setAdapter(allareasAdapter);

            tvAreasContentRefresh.setText(partitions.get(getLayoutPosition() - 2).getPartition().getCount() + "条动态，点击刷新！");


            gvAreasDrawing.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Toast.makeText(context, "35438468416", Toast.LENGTH_SHORT).show();
                }
            });

            gvHead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "ttttttt", Toast.LENGTH_SHORT).show();
                }
            });

            btnAreasContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    allareasAdapter.setCount(partitions.get(getLayoutPosition() - 2).getLives().size());
                    Toast.makeText(context, "1111111111", Toast.LENGTH_SHORT).show();
                }
            });

            tvAreasContentRefresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "34613", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}
