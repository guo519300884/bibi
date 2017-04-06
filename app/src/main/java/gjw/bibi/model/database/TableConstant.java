package gjw.bibi.model.database;

/**
 * Created by 皇上 on 2017/3/28.
 * <p>
 * 功能: 定义数据库字段和创建语句
 */

class TableConstant {

    public static final String TABLE_NAME = "goods";
    public static final String FIELD_ID = "id";
    public static final String FIELD_NUM = "number";
    public static final String FIELD_PRICE = "price";
    public static final String FIELD_FIGURE = "figure";
    public static final String FIELD_NAME = "name";


    public static final String CREATE_TABLE =
            "create table " + TABLE_NAME + " ("
                    + FIELD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + FIELD_NUM + " integer,"
                    + FIELD_NAME + " text,"
                    + FIELD_FIGURE + " text,"
                    + FIELD_PRICE + " text)";
}
