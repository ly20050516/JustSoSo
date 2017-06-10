package com.ly.justsoso.greendao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.ly.justsoso.enjoypictures.bean.PictureData;
import com.ly.justsoso.headline.bean.NewsItem;

import com.ly.justsoso.greendao.PictureDataDao;
import com.ly.justsoso.greendao.NewsItemDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig pictureDataDaoConfig;
    private final DaoConfig newsItemDaoConfig;

    private final PictureDataDao pictureDataDao;
    private final NewsItemDao newsItemDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        pictureDataDaoConfig = daoConfigMap.get(PictureDataDao.class).clone();
        pictureDataDaoConfig.initIdentityScope(type);

        newsItemDaoConfig = daoConfigMap.get(NewsItemDao.class).clone();
        newsItemDaoConfig.initIdentityScope(type);

        pictureDataDao = new PictureDataDao(pictureDataDaoConfig, this);
        newsItemDao = new NewsItemDao(newsItemDaoConfig, this);

        registerDao(PictureData.class, pictureDataDao);
        registerDao(NewsItem.class, newsItemDao);
    }
    
    public void clear() {
        pictureDataDaoConfig.getIdentityScope().clear();
        newsItemDaoConfig.getIdentityScope().clear();
    }

    public PictureDataDao getPictureDataDao() {
        return pictureDataDao;
    }

    public NewsItemDao getNewsItemDao() {
        return newsItemDao;
    }

}
