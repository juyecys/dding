package cn.com.dingduoduo.service.audio.impl;

import cn.com.dingduoduo.dao.audio.AudioLectureDAO;
import cn.com.dingduoduo.entity.audio.AudioLecture;
import cn.com.dingduoduo.service.common.impl.BaseServiceImpl;
import cn.com.dingduoduo.service.audio.AudioLectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jeysine on 2018/3/16.
 */
@Service("audioLectureService")
public class AudioLectureServiceImpl extends BaseServiceImpl<AudioLecture, AudioLecture> implements AudioLectureService {

    @Autowired
    private AudioLectureDAO dao;

    @Autowired
    public void setDao(AudioLectureDAO dao) {
        this.dao = dao;
        super.setDAO(dao);
    }
}
