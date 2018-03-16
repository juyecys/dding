package cn.com.dingduoduo.service.radio.impl;

import cn.com.dingduoduo.dao.radio.AudioAnswerDAO;
import cn.com.dingduoduo.entity.radio.AudioAnswer;
import cn.com.dingduoduo.service.common.impl.BaseServiceImpl;
import cn.com.dingduoduo.service.radio.AudioAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jeysine on 2018/3/16.
 */
@Service("radioAnswerService")
public class RadioAnswerServiceImpl extends BaseServiceImpl<AudioAnswer, AudioAnswer> implements AudioAnswerService {
    @Autowired
    private AudioAnswerDAO dao;

    @Autowired
    public void setDao(AudioAnswerDAO dao) {
        this.dao = dao;
        super.setDAO(dao);
    }
}
