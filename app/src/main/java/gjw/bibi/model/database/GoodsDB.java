package gjw.bibi.model.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 皇上 on 2017/3/28.
 */

public class GoodsDB extends SQLiteOpenHelper {

    public GoodsDB(Context context) {
        super(context, "goods.db", null, 1);
    }

    /**
     * 常用 基本语句
     * 创建表 create table 表名(字段1 类型,字段2 类型,字段3 类型);
     * 删除表 drop table 表名;
     * <p>
     * 增   insert into 表名(字段1,字段2,字段3) values(值1,值2,值3);
     * 删   delete from 表名 where=条件;
     * 改   update 表名 set 字段=值 where=条件;
     * 查询 select * from 表名;
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TableConstant.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
