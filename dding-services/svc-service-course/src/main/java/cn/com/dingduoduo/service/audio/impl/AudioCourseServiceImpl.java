package cn.com.dingduoduo.service.audio.impl;

import cn.com.dingduoduo.dao.audio.AudioCourseDAO;
import cn.com.dingduoduo.entity.audio.AudioCourse;
import cn.com.dingduoduo.entity.audio.AudioCourseDTO;
import cn.com.dingduoduo.entity.audio.AudioLectureGroupDTO;
import cn.com.dingduoduo.entity.common.Page;
import cn.com.dingduoduo.service.audio.AudioCourseService;
import cn.com.dingduoduo.service.audio.AudioLectureGroupService;
import cn.com.dingduoduo.service.common.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jeysine on 2018/3/16.
 */
@Service("audioCourseService")
public class AudioCourseServiceImpl extends BaseServiceImpl<AudioCourse, AudioCourseDTO> implements AudioCourseService {
    @Autowired
    private AudioCourseDAO dao;

    @Autowired
    private AudioLectureGroupService audioLectureGroupService;

    @Autowired
    public void setDao(AudioCourseDAO dao) {
        this.dao = dao;
        super.setDAO(dao);
    }

    @Override
    public Page<AudioCourseDTO> findByConditionPage(AudioCourseDTO qm) {
        Page<AudioCourseDTO> page = findByConditionPage(qm);
        if (page.getResult() != null) {
            AudioLectureGroupDTO audioLectureGroup = new AudioLectureGroupDTO();
            for (AudioCourseDTO one : page.getResult()) {
                audioLectureGroup.setCourseId(one.getId());
                List<AudioLectureGroupDTO> audioLectureGroupList = audioLectureGroupService.findByCondition(audioLectureGroup);
                one.setLectureGroupList(audioLectureGroupList);
            }
        }
        return page;
    }
}
