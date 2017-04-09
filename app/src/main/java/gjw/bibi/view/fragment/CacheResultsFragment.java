package gjw.bibi.view.fragment;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import gjw.bibi.R;
import gjw.bibi.view.base.BaseFragment;
import gjw.bibi.listener.ResponseListener;
import gjw.bibi.utils.RetrofitUtils;
import gjw.bibi.view.myview.MyProgressBar;

/**
 * Created by 皇上 on 2017/3/30.
 * 作者：郭建伟
 * QQ:519300884
 */

public class CacheResultsFragment extends BaseFragment {
    @InjectView(R.id.progresss)
    MyProgressBar progresss;
    @InjectView(R.id.tv_progress)
    TextView tvProgress;
    @InjectView(R.id.bt_download)
    Button btDownload;
    //    @InjectView(R.id.fl_dl_no_result)
//    LinearLayout flDlNoResult;
//    @InjectView(R.id.gv_dl_result)
//    MyGridView gvDlResult;
    private View view;
    private File file;

    @Override
    public View initView() {
        view = View.inflate(context, R.layout.fragment_cacheresults, null);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    protected void initData() {
        file = new File(context.getExternalFilesDir(null), "1.apk");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick(R.id.bt_download)
    public void onViewClicked() {

        btDownload.setText("下载中!");
        btDownload.setEnabled(false);
        RetrofitUtils.getInstance().download(file, new ResponseListener() {
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
                        btDownload.setText("下载完成!");
                    }
                });
            }

            @Override
            public void onFailure(final String error) {


                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btDownload.setText("下载出错!");
                        btDownload.setEnabled(true);
                        Log.e("TAG", "DownloadActivity onFailure()" + error);
                    }
                });

            }
        });
    }
}
