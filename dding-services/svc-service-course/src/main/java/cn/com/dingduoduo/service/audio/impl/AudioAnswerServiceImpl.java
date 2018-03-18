package cn.com.dingduoduo.service.audio.impl;

import cn.com.dingduoduo.dao.audio.AudioAnswerDAO;
import cn.com.dingduoduo.entity.audio.AudioAnswer;
import cn.com.dingduoduo.service.common.impl.BaseServiceImpl;
import cn.com.dingduoduo.service.audio.AudioAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jeysine on 2018/3/16.
 */
@Service("audioAnswerService")
public class AudioAnswerServiceImpl extends BaseServiceImpl<AudioAnswer, AudioAnswer> implements AudioAnswerService {
    @Autowired
    private AudioAnswerDAO dao;

    @Autowired
    public void setDao(AudioAnswerDAO dao) {
        this.dao = dao;
        super.setDAO(dao);
    }
}
