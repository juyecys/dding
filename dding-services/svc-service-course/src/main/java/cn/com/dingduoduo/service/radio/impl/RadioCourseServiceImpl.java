package cn.com.dingduoduo.service.radio.impl;

import cn.com.dingduoduo.dao.radio.AudioCourseDAO;
import cn.com.dingduoduo.entity.radio.AudioCourse;
import cn.com.dingduoduo.service.common.impl.BaseServiceImpl;
import cn.com.dingduoduo.service.radio.AudioCourseService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by jeysine on 2018/3/16.
 */
public class RadioCourseServiceImpl extends BaseServiceImpl<AudioCourse, AudioCourse> implements AudioCourseService {
    @Autowired
    private AudioCourseDAO dao;

    @Autowired
    public void setDao(AudioCourseDAO dao) {
        this.dao = dao;
        super.setDAO(dao);
    }
}
