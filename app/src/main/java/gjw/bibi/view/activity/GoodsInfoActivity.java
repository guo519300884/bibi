package gjw.bibi.view.activity;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import gjw.bibi.R;
import gjw.bibi.model.database.GoodsDAO;
import gjw.bibi.model.database.GoodsMiddleBean;
import gjw.bibi.presenter.adapter.GoodsAdapter;
import gjw.bibi.utils.VirtualkeyboardHeight;
import gjw.bibi.view.myview.AddSubView;

import static gjw.bibi.app.MyApplication.context;

public class GoodsInfoActivity extends AppCompatActivity {

    @InjectView(R.id.ib_good_info_back)
    ImageButton ibGoodInfoBack;
    @InjectView(R.id.ib_good_info_more)
    ImageButton ibGoodInfoMore;
    @InjectView(R.id.iv_good_info_image)
    ImageView ivGoodInfoImage;
    @InjectView(R.id.tv_good_info_name)
    TextView tvGoodInfoName;
    @InjectView(R.id.tv_good_info_desc)
    TextView tvGoodInfoDesc;
    @InjectView(R.id.tv_good_info_price)
    TextView tvGoodInfoPrice;
    @InjectView(R.id.tv_good_info_store)
    TextView tvGoodInfoStore;
    @InjectView(R.id.tv_good_info_style)
    TextView tvGoodInfoStyle;
    @InjectView(R.id.wb_good_info_more)
    WebView wbGoodInfoMore;
    @InjectView(R.id.progressbar)
    ProgressBar progressbar;
    @InjectView(R.id.tv_good_info_callcenter)
    TextView tvGoodInfoCallcenter;
    @InjectView(R.id.tv_good_info_collection)
    TextView tvGoodInfoCollection;
    @InjectView(R.id.tv_good_info_cart)
    TextView tvGoodInfoCart;
    @InjectView(R.id.btn_good_info_addcart)
    Button btnGoodInfoAddcart;
    @InjectView(R.id.ll_goods_root)
    LinearLayout llGoodsRoot;
    @InjectView(R.id.tv_more_share)
    TextView tvMoreShare;
    @InjectView(R.id.tv_more_search)
    TextView tvMoreSearch;
    @InjectView(R.id.tv_more_home)
    TextView tvMoreHome;
    @InjectView(R.id.btn_more)
    Button btnMore;
    @InjectView(R.id.ll_root)
    LinearLayout llRoot;

    private GoodsMiddleBean goodsMiddleBean;
    private WebSettings webSettings;
    private GoodsMiddleBean middleBean;
    private GoodsDAO goodsDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_info);
        ButterKnife.inject(this);
        getData();
    }

    private void getData() {

        goodsMiddleBean = (GoodsMiddleBean) getIntent().getSerializableExtra(GoodsAdapter.GOODS);

        String name = goodsMiddleBean.getName();
        String figure = goodsMiddleBean.getFigure();
        String price = goodsMiddleBean.getPrice();
        int number = goodsMiddleBean.getNumber();
        int id = goodsMiddleBean.getId();

        //
        middleBean = new GoodsMiddleBean(id, number, price, figure, name);

        setData();
        //获取数据库管理类
        goodsDAO = new GoodsDAO(context);
    }

    private void setData() {
        //设置图片
        Glide.with(this)
                .load(goodsMiddleBean.getFigure())
                .into(ivGoodInfoImage);
        //设置名字
        tvGoodInfoName.setText(goodsMiddleBean.getName());
        //设置价格
        tvGoodInfoPrice.setText("￥" + goodsMiddleBean.getPrice());

        //设置商品详情要加载的网页  实际工作是需要动态传入
        loadWeb("http://mp.weixin.qq.com/s/Cf3DrW2lnlb-w4wYaxOEZg");
    }

    private void loadWeb(String url) {

        webSettings = wbGoodInfoMore.getSettings();
        //设置支出Js
        webSettings.setJavaScriptEnabled(true);
        //设置添加缩放屏幕按钮
        webSettings.setBuiltInZoomControls(true);
        //设置屏幕双击变化大小
        webSettings.setUseWideViewPort(true);
        //设置WebViewClient,若不设置，打开新链接时将调起系统自带浏览器
        wbGoodInfoMore.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressbar.setVisibility(View.GONE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return true;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                //判断版本
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.loadUrl(request.getUrl().toString());
                }
                return true;
            }
        });
        wbGoodInfoMore.loadUrl(url);
    }

    @OnClick({R.id.ib_good_info_back, R.id.ib_good_info_more, R.id.tv_good_info_callcenter,
            R.id.tv_good_info_collection, R.id.tv_good_info_cart, R.id.btn_good_info_addcart,
            R.id.tv_more_share, R.id.tv_more_search, R.id.tv_more_home, R.id.btn_more})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_good_info_back:
                finish();
                break;
            case R.id.ib_good_info_more:
                if (llRoot.isShown()) {
                    llRoot.setVisibility(View.GONE);
                } else {
                    llRoot.setVisibility(View.VISIBLE);
                }
                Toast.makeText(this, "更多", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_good_info_callcenter:
////                Toast.makeText(this, "客服中心", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(this, CallCenterActivity.class);
//                startActivity(intent);
                break;
            case R.id.tv_good_info_collection:
                Toast.makeText(this, "收藏", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_good_info_cart:
//                Toast.makeText(this, "购物车", Toast.LENGTH_SHORT).show();
                //跳转到购物车
                Intent intentCart = new Intent(this, CartActivity.class);
//                intentCart.putExtra("checkId", R.id.rb_cart);
                startActivity(intentCart);
                break;
            case R.id.btn_good_info_addcart:
//                Toast.makeText(this, "加入购物车", Toast.LENGTH_SHORT).show();
//                CartStorage.getInstance(this).addData(goodsBean);

                showPopwindow();

                break;
            case R.id.tv_more_share:
//                Toast.makeText(this, "分享", Toast.LENGTH_SHORT).show();

//                Intent intent2 = new Intent(this, QRActivity.class);
//                intent2.putExtra(HomeAdapter.GOODS_BEAN, goodsBean);
//                startActivity(intent2);
                break;
            case R.id.tv_more_search:
                Toast.makeText(this, "搜索", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_more_home:
//                Toast.makeText(this, "回到首页", Toast.LENGTH_SHORT).show();

//                Intent intent1 = new Intent(this, MainActivity.class);
//                intent1.putExtra("checkId", R.id.rb_home);
//                startActivity(intent1);
                break;
            case R.id.btn_more:
                llRoot.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * 显示popupWindow
     */
    private void showPopwindow() {

        // 1 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popupwindow_add_product, null);

        // 2下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()
        final PopupWindow window = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);

        // 3 参数设置
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xFFFFFFFF);
        window.setBackgroundDrawable(dw);

        // 设置popWindow的显示和消失动画
        window.setAnimationStyle(R.style.mypopwindow_anim_style);


        // 4 控件处理
        ImageView iv_goodinfo_photo = (ImageView) view.findViewById(R.id.iv_goodinfo_photo);
        TextView tv_goodinfo_name = (TextView) view.findViewById(R.id.tv_goodinfo_name);
        TextView tv_goodinfo_price = (TextView) view.findViewById(R.id.tv_goodinfo_price);
        AddSubView nas_goodinfo_num = (AddSubView) view.findViewById(R.id.nas_goodinfo_num);
        Button bt_goodinfo_cancel = (Button) view.findViewById(R.id.bt_goodinfo_cancel);
        Button bt_goodinfo_confim = (Button) view.findViewById(R.id.bt_goodinfo_confim);

        // 加载图片
        Glide.with(GoodsInfoActivity.this).load(goodsMiddleBean.getFigure()).into(iv_goodinfo_photo);

        // 名称
        tv_goodinfo_name.setText(goodsMiddleBean.getName());
        // 显示价格
        tv_goodinfo_price.setText(goodsMiddleBean.getPrice());

        // 设置最大值和当前值
        nas_goodinfo_num.setMaxValue(100);

        //设置数量
        if (goodsDAO.query() == null) {
            nas_goodinfo_num.setValue(1);
        } else {
            List<GoodsMiddleBean> query = goodsDAO.query();
            for (int i = 0; i < query.size(); i++) {
                if (middleBean.getId() == query.get(i).getId()) {
                    int number = query.get(i).getNumber();
                    nas_goodinfo_num.setValue(number);
                }
            }
        }
        //数量变化
        nas_goodinfo_num.setOnNumberChangeListener(new AddSubView.OnNumberChangeListener() {
            @Override
            public void OnNumberChange(int value) {

                middleBean.setNumber(value);

            }
        });

        bt_goodinfo_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
            }
        });

        bt_goodinfo_confim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
                //添加购物车
                goodsDAO.add(middleBean);

//                Log.e("TAG", "GoodsInfoActivity onClick()666666666666" + goodsDAO.query().get(0).getNumber());

//                CartStorage.getInstance(GoodsInfoActivity.this).addData(goodsMiddleBean);
                Log.e("TAG", "66:" + goodsMiddleBean.toString());
                Log.e("TAG", "GoodsInfoActivity onClick()" + middleBean.getPrice());
                Toast.makeText(GoodsInfoActivity.this, "添加购物车成功", Toast.LENGTH_SHORT).show();
            }
        });

        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                window.dismiss();
            }
        });

        // 5 在底部显示
        window.showAtLocation(GoodsInfoActivity.this.findViewById(R.id.ll_goods_root),
                Gravity.BOTTOM, 0, VirtualkeyboardHeight.getBottomStatusHeight(GoodsInfoActivity.this));
    }


}
