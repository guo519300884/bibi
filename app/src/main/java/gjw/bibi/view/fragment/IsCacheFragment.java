package gjw.bibi.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.InjectView;
import gjw.bibi.R;
import gjw.bibi.listener.ResponseListener;
import gjw.bibi.utils.RetrofitUtils;
import gjw.bibi.view.base.BaseFragment;
import gjw.bibi.view.myview.MyProgressBar;


/**
 * Created by 皇上 on 2017/3/30.
 * 作者：郭建伟
 * QQ:519300884
 */

public class IsCacheFragment extends BaseFragment {
    @InjectView(R.id.tv_seekbar)
    TextView tvSeekbar;
    @InjectView(R.id.seekbar)
    SeekBar seekbar;
    @InjectView(R.id.recyclerview)
    RecyclerView recyclerview;
    @InjectView(R.id.activity_download_list)
    LinearLayout activityDownloadList;
    @InjectView(R.id.dl_number_bar)
    LinearLayout dlNumberBar;

//    @InjectView(R.id.fl_dl_no_cache)
//    LinearLayout flDlNoCache;
//    @InjectView(R.id.gv_dl_cache)
//    MyGridView gvDlCache;
//    @InjectView(R.id.btn_dl_pause)
//    Button btnDlPause;
//    @InjectView(R.id.btn_dl_begin)
//    Button btnDlBegin;

    private View view;
    private int maxDownloadNubmer; //最大同时下载数量
    private int downloadNumber; //当前下载数量


    @Override
    public View initView() {
        view = View.inflate(context, R.layout.fragment_iscache, null);
        ButterKnife.inject(this, view);
        return view;
    }


    @Override
    protected void initData() {

        recyclerview.setAdapter(new DLRVAdapter());
        recyclerview.setLayoutManager(new LinearLayoutManager(context));
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvSeekbar.setText("同时下载数量  " + (progress + 1) + "  ");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

//    private void initListener() {
//        if (gvDlCache == null) {
//            btnDlBegin.setEnabled(false);
//        } else {
//            btnDlBegin.setEnabled(true);
//        }
//    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    private class DLRVAdapter extends RecyclerView.Adapter<ViewHolder> {


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.item_download, parent, false));
        }


        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.setData();
        }

        @Override
        public int getItemCount() {
            return 10;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.progresss)
        MyProgressBar progresss;
        @InjectView(R.id.tv_progress)
        TextView tvProgress;
        @InjectView(R.id.bt_download)
        Button btDownload;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }

        public void setData() {

            btDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //seekbar 拖动范围0~4 +1相当于 同时下载数量范围为1~5
                    if (maxDownloadNubmer == 0) { //max=0 表示第一次下载赋值最大下载数量
                        maxDownloadNubmer = seekbar.getProgress() + 1;
                        Log.e("TAG", "ViewHolder onClick()" + maxDownloadNubmer);
                    }
                    if (downloadNumber >= maxDownloadNubmer) {
                        Toast.makeText(getActivity(), "同时下载数量过多 无法继续下载", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    downloadNumber++;
                    btDownload.setText("下载中!");
                    btDownload.setEnabled(false);
                    RetrofitUtils.getInstance().download(new File(context.getExternalFilesDir(null), getLayoutPosition() + ".apk"), new ResponseListener() {
                        @Override
                        public void onProgress(final long progress, final long total, boolean done) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    long l = progress * 100 / total;
                                    progresss.setProgress((int) l);

                                    String pro = (int) progress * 100 / (int) total + "%";
                                    String p = RetrofitUtils.formetFileSize(progress);
                                    String t = RetrofitUtils.formetFileSize(total);
                                    tvProgress.setText(p + " / " + t + "\t" + pro);
                                }
                            });
                        }

                        @Override
                        public void onResponse() {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    downloadNumber--;
                                    btDownload.setText("下载完成!");
                                }
                            });
                        }

                        @Override
                        public void onFailure(final String error) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    downloadNumber--;
                                    btDownload.setText("下载出错!");
                                    btDownload.setEnabled(true);
                                    Log.e("TAG", "DownloadActivity onFailure()" + error);
                                }
                            });

                        }
                    });
                }
            });

        }
    }
}
