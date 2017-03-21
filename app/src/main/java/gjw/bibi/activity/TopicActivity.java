package gjw.bibi.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import gjw.bibi.R;
import gjw.bibi.adapter.TopicAdapter;
import gjw.bibi.bean.TopicBean;
import gjw.bibi.utils.AppNetConfig;
import okhttp3.Call;


public class TopicActivity extends AppCompatActivity {

    @InjectView(R.id.ib_back)
    ImageButton ibBack;
    @InjectView(R.id.lv_topic)
    ListView lvTopic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);
        ButterKnife.inject(this);

        getDataFromNet();
    }

    private void getDataFromNet() {
        OkHttpUtils.get()
                .id(100)
                .url(AppNetConfig.TOPIC)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Toast.makeText(TopicActivity.this, "你的网出毛病了", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, int id) {
                processData(response);
            }
        });
    }

    private void processData(String response) {
        TopicBean topicBean = JSON.parseObject(response, TopicBean.class);
        List<TopicBean.ListBean> topicBeanList = topicBean.getList();

        if (topicBeanList != null && topicBeanList.size() > 0) {
            TopicAdapter topicAdapter = new TopicAdapter(this, topicBeanList);
            lvTopic.setAdapter(topicAdapter);
        }
    }

    @OnClick(R.id.ib_back)
    public void onClick() {
        finish();
    }
}
