package gjw.bibi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import gjw.bibi.bean.ZoneTypeBean;

/**
 * Created by 皇上 on 2017/3/23.
 */

public class ZoneTypeAdapter extends RecyclerView.Adapter {


    public static final int ZONE = 0;
    public static final int BANNER = 1;
    public static final int ACTIVITY = 3;

    public int currentType = 0;

    private final List<ZoneTypeBean.DataBean> zoneTypeBeanData;
    private final Context context;

    public ZoneTypeAdapter(Context context, List<ZoneTypeBean.DataBean> zoneTypeBeanData) {
        this.context = context;
        this.zoneTypeBeanData = zoneTypeBeanData;

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == ZONE) {
            currentType = ZONE;
        } else if (position == BANNER) {
            currentType = BANNER;
        } else if (position == ACTIVITY) {
            currentType = ACTIVITY;
        }
        return currentType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


    }


}
