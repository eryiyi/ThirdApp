package com.example.thirdapp.db;

import android.database.sqlite.SQLiteDatabase;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import java.util.Map;


// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig shoppingCartDaoConfig;
    private final DaoConfig doorKeyDaoConfig;

    private final ShoppingCartDao shoppingCartDao;
    private final DoorKeyDao doorKeyDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        shoppingCartDaoConfig = daoConfigMap.get(ShoppingCartDao.class).clone();
        shoppingCartDaoConfig.initIdentityScope(type);

        doorKeyDaoConfig = daoConfigMap.get(DoorKeyDao.class).clone();
        doorKeyDaoConfig.initIdentityScope(type);

        shoppingCartDao = new ShoppingCartDao(shoppingCartDaoConfig, this);
        doorKeyDao = new DoorKeyDao(doorKeyDaoConfig, this);

        registerDao(ShoppingCart.class, shoppingCartDao);
        registerDao(DoorKey.class, doorKeyDao);
    }
    
    public void clear() {
        shoppingCartDaoConfig.getIdentityScope().clear();
        doorKeyDaoConfig.getIdentityScope().clear();
    }

    public ShoppingCartDao getShoppingCartDao() {
        return shoppingCartDao;
    }

    public DoorKeyDao getDoorKeyDao() {
        return doorKeyDao;
    }

}
