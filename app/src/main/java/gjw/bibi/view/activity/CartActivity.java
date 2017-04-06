package gjw.bibi.view.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import gjw.bibi.R;
import gjw.bibi.adapter.CartAdapter;
import gjw.bibi.model.database.GoodsDAO;
import gjw.bibi.model.database.GoodsMiddleBean;
import gjw.bibi.utils.pay.AliPayUtils;
import gjw.bibi.view.base.BaseActivity;

import static gjw.bibi.app.MyApplication.context;

public class CartActivity extends BaseActivity {


//    //商户PID
//    public static final String PARTNER = PayKeys.DEFAULT_PARTNER;
//    //商户收款账号
//    public static final String SELLER = PayKeys.DEFAULT_SELLER;
//    //商户私钥，pkcs8格式
//    public static final String RSA_PRIVATE = PayKeys.PRIVATE;
//    //支付宝公钥
//    public static final String RSA_PUBLIC = PayKeys.PUBLIC;
//
//    private static final int SDK_PAY_FLAG = 1;
//
//    private static final int SDK_CHECK_FLAG = 2;

    @InjectView(R.id.tv_shopcart_edit)
    TextView tvShopcartEdit;
    @InjectView(R.id.listview)
    ListView listview;
    @InjectView(R.id.checkbox_all)
    CheckBox checkboxAll;
    @InjectView(R.id.tv_shopcart_total)
    TextView tvShopcartTotal;
    @InjectView(R.id.btn_check_out)
    Button btnCheckOut;
    @InjectView(R.id.ll_check_all)
    LinearLayout llCheckAll;
    @InjectView(R.id.checkbox_delete_all)
    CheckBox checkboxDeleteAll;
    @InjectView(R.id.btn_delete)
    Button btnDelete;
    @InjectView(R.id.btn_collection)
    Button btnCollection;
    @InjectView(R.id.ll_delete)
    LinearLayout llDelete;
    @InjectView(R.id.iv_empty)
    ImageView ivEmpty;
    @InjectView(R.id.tv_empty_cart_tobuy)
    TextView tvEmptyCartTobuy;
    @InjectView(R.id.ll_empty_shopcart)
    LinearLayout llEmptyShopcart;

    public static final int ACTION_EDIT = 1;
    public static final int ACTION_COMPLETE = 2;

    private GoodsDAO goodsDAO;
    private CartAdapter cartAdapter;
    private List<GoodsMiddleBean> queryList;
    private Intent intent;


//    private Handler mHandler = new Handler() {
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case SDK_PAY_FLAG: {
//                    PayResult payResult = new PayResult((String) msg.obj);
//
//                    // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
//                    String resultInfo = payResult.getResult();
//
//                    String resultStatus = payResult.getResultStatus();
//
//                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
//                    if (TextUtils.equals(resultStatus, "9000")) {
//                        Toast.makeText(CartActivity.this, "支付成功",
//                                Toast.LENGTH_SHORT).show();
//                    } else {
//                        // 判断resultStatus 为非“9000”则代表可能支付失败
//                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
//                        if (TextUtils.equals(resultStatus, "8000")) {
//                            Toast.makeText(CartActivity.this, "支付结果确认中",
//                                    Toast.LENGTH_SHORT).show();
//
//                        } else {
//                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
//                            Toast.makeText(CartActivity.this, "支付失败",
//                                    Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                    break;
//                }
//                case SDK_CHECK_FLAG: {
//                    Toast.makeText(CartActivity.this, "检查结果为：" + msg.obj,
//                            Toast.LENGTH_SHORT).show();
//                    break;
//                }
//                default:
//                    break;
//            }
//        }
//
//        ;
//    };


    @Override
    protected void initData() {
        goodsDAO = new GoodsDAO(context);

        //设置编辑状态
        tvShopcartEdit.setTag(ACTION_EDIT);
        tvShopcartEdit.setText("编辑");
        //显示结算页面
        llCheckAll.setVisibility(View.VISIBLE);

        tvShopcartEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //得到状态
                int action = (int) v.getTag();
                //根据不同的状态做不同的处理
                if (action == ACTION_EDIT) {
                    //切换到完成状态
                    showDelete();
                } else {
                    //切换到编辑状态
                    hideDelete();
                }
            }
        });
        showData();
    }


    @Override
    protected void initListener() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_cart;
    }

    private void hideDelete() {
        //设置编辑
        tvShopcartEdit.setTag(ACTION_EDIT);
        //隐藏删除页控件
        llDelete.setVisibility(View.GONE);
        //显示结算页控件
        llCheckAll.setVisibility(View.VISIBLE);
        //设置文本为编辑
        tvShopcartEdit.setText("编辑");
        //将所有数据设置选中状态
        if (cartAdapter != null) {

            cartAdapter.checkAll_none(true);
            cartAdapter.checkAll();
            cartAdapter.showTotalPrice();
        }


    }

    private void showDelete() {

        //1.设置完成
        tvShopcartEdit.setTag(ACTION_COMPLETE);
        //2.显示删除页kongjian
        llDelete.setVisibility(View.VISIBLE);
        //3.隐藏结算页控件
        llCheckAll.setVisibility(View.GONE);
        //4.设置文本为完成
        tvShopcartEdit.setText("完成");
        //将所有数据设置非选中状态
        if (cartAdapter != null) {
            cartAdapter.checkAll_none(false);
            cartAdapter.checkAll();
//            cartAdapter.showTotalPrice();
        }


    }

    private void showData() {
        queryList = goodsDAO.query();

        if (queryList != null && queryList.size() > 0) {

            //表示购物车内有数据
            llEmptyShopcart.setVisibility(View.GONE);
            //设置适配器
            cartAdapter = new CartAdapter(context, queryList, tvShopcartTotal, checkboxAll, checkboxDeleteAll);
            listview.setAdapter(cartAdapter);

//            Log.e("TAG", "CartActivity showData()");
//            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    //获取到购物车内的数据
//                    GoodsMiddleBean goodsMiddleBean = cartAdapter.queryList.get(position);
//                    //对选择状态取反
//                    Log.e("TAG", "CartActivity onItemClick()" + goodsMiddleBean.isChecked());
//                    goodsMiddleBean.setChecked(!goodsMiddleBean.isChecked());
//                    Log.e("TAG", "CartActivity onItemClick()" + goodsMiddleBean.isChecked());
//                    //刷新选择状态
//                    cartAdapter.notifyDataSetChanged();
//                    //刷新总和价格
//                    cartAdapter.showTotalPrice();
//                    //检验是否全选
//                    cartAdapter.checkAll();
//                }
//            });
            cartAdapter.setOnItemClickListener(new CartAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int postion) {
                    //获取到购物车内的数据
                    GoodsMiddleBean goodsMiddleBean = cartAdapter.queryList.get(postion);
                    //对选择状态取反
                    Log.e("TAG", "CartActivity onItemClick()" + goodsMiddleBean.isChecked());
                    goodsMiddleBean.setChecked(!goodsMiddleBean.isChecked());
                    Log.e("TAG", "CartActivity onItemClick()" + goodsMiddleBean.isChecked());
                    //刷新选择状态
                    cartAdapter.notifyDataSetChanged();
                    //刷新总和价格
                    cartAdapter.showTotalPrice();
                    //检验是否全选
                    cartAdapter.checkAll();
                }
            });
            //检验是否全选
            cartAdapter.checkAll();
            Log.e("TAG", "CartActivity onItemClick()" + "11111111111111111111111111111111111111");

        } else {
            llEmptyShopcart.setVisibility(View.VISIBLE);
        }
    }


    @OnClick({R.id.tv_shopcart_edit, R.id.checkbox_all, R.id.btn_check_out, R.id.checkbox_delete_all, R.id.btn_delete, R.id.btn_collection, R.id.tv_empty_cart_tobuy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_shopcart_edit:
                break;
            case R.id.checkbox_all:
                boolean isChecked = checkboxAll.isChecked();
                //全选和反全选
                cartAdapter.checkAll_none(isChecked);
                //显示总计价格
                cartAdapter.showTotalPrice();
                break;
            case R.id.btn_check_out:
                Toast.makeText(this, "结算", Toast.LENGTH_SHORT).show();
                //调支付宝
                AliPayUtils.getInstance().pay(this, "0.01");

//                // 订单
//                String orderInfo = getOrderInfo("测试的商品", "该测试商品的详细描述", "0.01");
//                // 对订单做RSA 签名
//                String sign = sign(orderInfo);
//                try {
//                    // 仅需对sign 做URL编码
//                    sign = URLEncoder.encode(sign, "UTF-8");
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
//                // 完整的符合支付宝参数规范的订单信息
//                final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
//                        + getSignType();
//
//                Runnable payRunnable = new Runnable() {
//                    @Override
//                    public void run() {
//                        // 构造PayTask 对象
//                        PayTask alipay = new PayTask(CartActivity.this);
//                        // 调用支付接口，获取支付结果
//                        String result = alipay.pay(payInfo, true);
//
//                        Message msg = new Message();
//                        msg.what = SDK_PAY_FLAG;
//                        msg.obj = result;
//                        mHandler.sendMessage(msg);
//                    }
//                };
//                // 必须异步调用
//                Thread payThread = new Thread(payRunnable);
//                payThread.start();

                break;
            case R.id.checkbox_delete_all:
                isChecked = checkboxDeleteAll.isChecked();
                //全选与分全选
                cartAdapter.checkAll_none(isChecked);
                //显示合计价格
                cartAdapter.showTotalPrice();
                break;
            case R.id.btn_delete:
                //删除
                cartAdapter.deleteData();
                //全选
                cartAdapter.checkAll();
                //购物车没数据显示此页面
                showEmpty();
                break;
            case R.id.btn_collection:
                break;
            case R.id.tv_empty_cart_tobuy:
                intent = new Intent(context, MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    private void showEmpty() {
        if (cartAdapter.getCount() == 0) {
            llEmptyShopcart.setVisibility(View.VISIBLE);
        }
    }


//    /**
//     * create the order info. 创建订单信息
//     */
//    public String getOrderInfo(String subject, String body, String price) {
//        // 签约合作者身份ID
//        String orderInfo = "partner=" + "\"" + PARTNER + "\"";
//
//        // 签约卖家支付宝账号
//        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";
//
//        // 商户网站唯一订单号
//        orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";
//
//        // 商品名称
//        orderInfo += "&subject=" + "\"" + subject + "\"";
//
//        // 商品详情
//        orderInfo += "&body=" + "\"" + body + "\"";
//
//        // 商品金额
//        orderInfo += "&total_fee=" + "\"" + price + "\"";
//
//        // 服务器异步通知页面路径
//        orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm"
//                + "\"";
//
//        // 服务接口名称， 固定值
//        orderInfo += "&service=\"mobile.securitypay.pay\"";
//
//        // 支付类型， 固定值
//        orderInfo += "&payment_type=\"1\"";
//
//        // 参数编码， 固定值
//        orderInfo += "&_input_charset=\"utf-8\"";
//
//        // 设置未付款交易的超时时间
//        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
//        // 取值范围：1m～15d。
//        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
//        // 该参数数值不接受小数点，如1.5h，可转换为90m。
//        orderInfo += "&it_b_pay=\"30m\"";
//
//        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
//        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";
//
//        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
//        orderInfo += "&return_url=\"m.alipay.com\"";
//
//        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
//        // orderInfo += "&paymethod=\"expressGateway\"";
//
//        return orderInfo;
//    }
//
//    /**
//     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
//     */
//    public String getOutTradeNo() {
//        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
//                Locale.getDefault());
//        Date date = new Date();
//        String key = format.format(date);
//
//        Random r = new Random();
//        key = key + r.nextInt();
//        key = key.substring(0, 15);
//        return key;
//    }
//
//    /**
//     * sign the order info. 对订单信息进行签名
//     *
//     * @param content 待签名订单信息
//     */
//    public String sign(String content) {
//        return SignUtils.sign(content, RSA_PRIVATE);
//    }
//
//
//    /**
//     * get the sign type we use. 获取签名方式
//     */
//    public String getSignType() {
//        return "sign_type=\"RSA\"";
//    }
}
