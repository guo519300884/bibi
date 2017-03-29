package gjw.bibi.utils;

import android.content.Context;

import gjw.bibi.database.GoodsDAO;

/**
 * Created by 皇上 on 2017/3/28.
 */

public class CartStorage {

    private final Context context;

    public CartStorage(Context context) {
        this.context = context;
        initData();
    }

    private void initData() {
        GoodsDAO goodsDAO = new GoodsDAO(context);
        goodsDAO.query();

    }
}
