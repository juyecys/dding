package cn.com.dingduoduo.service.keyword.impl;

import cn.com.dingduoduo.dao.keyword.LocalWechatKeyWordDAO;
import cn.com.dingduoduo.entity.keyword.LocalWechatKeyWord;
import cn.com.dingduoduo.service.common.impl.BaseServiceImpl;
import cn.com.dingduoduo.service.keyword.LocalWechatKeyWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("localWechatKeyWordService")
public class LocalWechatKeyWordServiceImpl extends BaseServiceImpl<LocalWechatKeyWord, LocalWechatKeyWord> implements LocalWechatKeyWordService {
    @Autowired
    private LocalWechatKeyWordDAO dao;

    @Autowired
    public void setLocalWechatKeyWordDAO(LocalWechatKeyWordDAO dao) {
        this.dao = dao;
        super.setDAO(dao);
    }
}
