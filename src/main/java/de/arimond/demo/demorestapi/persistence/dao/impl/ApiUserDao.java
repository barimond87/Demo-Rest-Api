package de.arimond.demo.demorestapi.persistence.dao.impl;

import de.arimond.demo.demorestapi.persistence.dao.HibernateDaoSupport;
import de.arimond.demo.demorestapi.persistence.dao.IApiUserDao;
import de.arimond.demo.demorestapi.persistence.entity.ApiUser;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class ApiUserDao  extends HibernateDaoSupport<ApiUser> implements IApiUserDao {

    public ApiUserDao() {
        super(ApiUser.class);
    }

}

