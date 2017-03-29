package gjw.bibi.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 皇上 on 2017/3/28.
 * QQ:519300884
 */

public class GoodsDAO {

    private final Context context;
    private GoodsDB goodsDB;


    public GoodsDAO(Context context) {
        this.context = context;
        goodsDB = new GoodsDB(context);
    }

    //添加
    public void add(GoodsMiddleBean goods) {
        if (goods == null) {
            return;
        }

        SQLiteDatabase database = goodsDB.getReadableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(TableConstant.FIELD_ID, goods.getId());
        contentValues.put(TableConstant.FIELD_NUM, goods.getNumber());
        contentValues.put(TableConstant.FIELD_PRICE, goods.getPrice());
        contentValues.put(TableConstant.FIELD_NAME, goods.getName());
        contentValues.put(TableConstant.FIELD_FIGURE, goods.getFigure());

        database.replace(TableConstant.TABLE_NAME, null, contentValues);
    }

    //删除
    public void delete(GoodsMiddleBean goods) {
        if (goods == null) {
            return;
        }
        SQLiteDatabase database = goodsDB.getReadableDatabase();

        database.delete(TableConstant.TABLE_NAME,
                "id=?", new String[]{goods.getId() + ""});

    }

    //更新
    public void updata(GoodsMiddleBean goods) {
        if (goods == null) {
            return;
        }
        SQLiteDatabase database = goodsDB.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TableConstant.FIELD_NUM, goods.getName());
        contentValues.put(TableConstant.FIELD_ID, goods.getId());
        contentValues.put(TableConstant.FIELD_PRICE, goods.getPrice());
        contentValues.put(TableConstant.FIELD_FIGURE, goods.getFigure());
        contentValues.put(TableConstant.FIELD_NAME, goods.getName());

        database.update(TableConstant.TABLE_NAME, contentValues,
                "id=?", new String[]{goods.getId() + ""});
    }

    //查询
    public List<GoodsMiddleBean> query() {

        SQLiteDatabase database = goodsDB.getReadableDatabase();
        //游标
        Cursor cursor = database.rawQuery("select * from " + TableConstant.TABLE_NAME, null);

        List<GoodsMiddleBean> goodsList = new ArrayList<>();

        GoodsMiddleBean goods;

        while (cursor.moveToNext()) {
            int number = cursor.getInt(cursor.getColumnIndex(TableConstant.FIELD_NUM));
            int id = cursor.getInt(cursor.getColumnIndex(TableConstant.FIELD_ID));
            String price = cursor.getString(cursor.getColumnIndex(TableConstant.FIELD_PRICE));
            String figure = cursor.getString(cursor.getColumnIndex(TableConstant.FIELD_FIGURE));
            String name = cursor.getString(cursor.getColumnIndex(TableConstant.FIELD_NAME));

            goods = new GoodsMiddleBean(id, number, price, figure, name);

            goodsList.add(goods);
        }
        //关闭游标
        cursor.close();
        return goodsList;
    }

}
