package com.example.thirdapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import de.greenrobot.dao.query.QueryBuilder;

import java.util.List;

/**
 * Created by liuzwei on 2015/3/13.
 */
public class DBHelper {
    private static Context mContext;
    private static DBHelper instance;
    private static DaoMaster.DevOpenHelper helper;
    private ShoppingCartDao testDao;
    private DoorKeyDao keyDao;
    private static SQLiteDatabase db;
    private static DaoMaster daoMaster;
    private DBHelper(){}
    public static DBHelper getInstance(Context context){
        if (instance == null){
            instance = new DBHelper();
            if (mContext == null){
                mContext = context;
            }
            helper = new DaoMaster.DevOpenHelper(context, "third_xuzhou_db", null);
            db = helper.getWritableDatabase();
            daoMaster = new DaoMaster(db);
            instance.testDao = daoMaster.newSession().getShoppingCartDao();
            instance.keyDao = daoMaster.newSession().getDoorKeyDao();
        }
        return instance;
    }

    /**
     * ��������
     * @param test
     */
    public void addShoppingToTable(ShoppingCart test){
        testDao.insert(test);
    }



    //��ѯĳ�����Ƿ����ĳ��id
    public boolean isSaved(String ID)
    {
        QueryBuilder<ShoppingCart> qb = testDao.queryBuilder();
        qb.where(ShoppingCartDao.Properties.Goods_id.eq(ID));
        qb.buildCount().count();
        return qb.buildCount().count() > 0 ? true : false;
    }

    //������������
    public void saveTestList(List<ShoppingCart> tests){
        testDao.insertOrReplaceInTx(tests);
    }

    /**
     * ��ѯ���еĹ��ﳵ
     * @return
     */
    public List<ShoppingCart> getShoppingList(){
        return testDao.loadAll();
    }

    /**
     * ������Ǹ�������
     * @param test
     * @return
     */
    public long saveShopping(ShoppingCart test){
        return testDao.insertOrReplace(test);
    }

    /**
     * ��������
     * @param test
     */
    public void updateTest(ShoppingCart test){
        testDao.update(test);
    }

//    /**
//     * ��������ղص���
//     * @return
//     */
//    public List<ShoppingCart> getFavourTest(){
//        QueryBuilder qb = testDao.queryBuilder();
//        qb.where(ShoppingCartDao.Properties.IsFavor.eq(true));
//        return qb.list();
//    }

    /**
     * ɾ������
     */
    public void removeAll(){
        testDao.deleteAll();
    }





    /**
     * ��������
     * @param test
     */
    public void addKeyToTable(DoorKey test){
        keyDao.insert(test);
    }
    /**
     * ɾ������
     */
    public void removeAllKey(){
        keyDao.deleteAll();
    }

    /**
     * ��ѯ����key
     * @return
     */
    public List<DoorKey> getDoorKey(){
        return keyDao.loadAll();
    }

}
