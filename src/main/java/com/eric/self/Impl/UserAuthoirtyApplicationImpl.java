package com.eric.self.Impl;

import com.eric.self.UserAuthoirtyApplication;
import com.eric.self.dao.mapper.UserAuthorityMapper;
import mapper.UserAuthority;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by wangfeng on 19/1/22.
 */
@Service("userAuthoirtyApplication")
public class UserAuthoirtyApplicationImpl implements UserAuthoirtyApplication {
    @Autowired
    private UserAuthorityMapper userAuthorityMapper;

    @Override
    public void insert(Iterator<UserAuthority> it){
        if(it.hasNext()) {
            List inserList=new ArrayList();
            while(it.hasNext()){
                UserAuthority userAuthority=it.next();
                System.out.println("userAuthority:"+userAuthority.toString());
                inserList.add(userAuthority);
            }
            userAuthorityMapper.batchInsert(inserList);
        }
    }
}
