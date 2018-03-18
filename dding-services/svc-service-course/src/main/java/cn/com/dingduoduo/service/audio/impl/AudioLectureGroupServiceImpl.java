package cn.com.dingduoduo.service.audio.impl;

import cn.com.dingduoduo.dao.audio.AudioLectureDAO;
import cn.com.dingduoduo.entity.audio.AudioLectureGroup;
import cn.com.dingduoduo.entity.audio.AudioLectureGroupDTO;
import cn.com.dingduoduo.service.common.impl.BaseServiceImpl;
import cn.com.dingduoduo.service.audio.AudioLectureGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jeysine on 2018/3/16.
 */
@Service("audioLectureGroupService")
public class AudioLectureGroupServiceImpl extends BaseServiceImpl<AudioLectureGroup, AudioLectureGroupDTO> implements AudioLectureGroupService {
    @Autowired
    private AudioLectureDAO dao;

    @Autowired
    public void setDao(AudioLectureDAO dao) {
        this.dao = dao;
        super.setDAO(dao);
    }
}
