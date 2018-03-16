package cn.com.dingduoduo.service.radio.impl;

import cn.com.dingduoduo.dao.radio.AudioLectureDAO;
import cn.com.dingduoduo.entity.radio.AudioLecture;
import cn.com.dingduoduo.service.common.impl.BaseServiceImpl;
import cn.com.dingduoduo.service.radio.AudioLectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jeysine on 2018/3/16.
 */
@Service("radioLectureService")
public class RadioLectureServiceImpl extends BaseServiceImpl<AudioLecture, AudioLecture> implements AudioLectureService {

    @Autowired
    private AudioLectureDAO dao;

    @Autowired
    public void setDao(AudioLectureDAO dao) {
        this.dao = dao;
        super.setDAO(dao);
    }
}
