package cn.com.dingduoduo.service.audio.impl;

import cn.com.dingduoduo.dao.audio.AudioLectureGroupDAO;
import cn.com.dingduoduo.entity.audio.*;
import cn.com.dingduoduo.service.audio.AudioAnswerService;
import cn.com.dingduoduo.service.audio.AudioCourseService;
import cn.com.dingduoduo.service.audio.AudioLectureGroupService;
import cn.com.dingduoduo.service.audio.AudioLectureService;
import cn.com.dingduoduo.service.common.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * Created by jeysine on 2018/3/16.
 */
@Service("audioLectureGroupService")
public class AudioLectureGroupServiceImpl extends BaseServiceImpl<AudioLectureGroup, AudioLectureGroupDTO> implements AudioLectureGroupService {
    @Autowired
    private AudioLectureGroupDAO dao;

    @Autowired
    private AudioLectureService audioLectureService;

    @Autowired
    private AudioCourseService audioCourseService;

    @Autowired
    private AudioAnswerService audioAnswerService;

    @Autowired
    public void setDao(AudioLectureGroupDAO dao) {
        this.dao = dao;
        super.setDAO(dao);
    }

    @Override
    public List<AudioLectureGroupDTO> createOrUpdateByBatch(List<AudioLectureGroupDTO> audioLectureGroupList) throws Exception {
        Long totalTime = 0L;
        for (AudioLectureGroupDTO one: audioLectureGroupList) {
            if (Objects.nonNull(one.getDelete()) && one.getDelete()) {
                deleteById(one.getId());
            } else {
                createOrUpdate(one);
                totalTime += audioLectureService.createOrUpdateByBatch(one.getId(), one.getAudioLectureList());
            }
        }
        if (audioLectureGroupList.size() > 0) {
            Long audioAnswerTotalTime = audioAnswerService.countAudioAnswerByCourseId(audioLectureGroupList.get(0).getCourseId());
            audioAnswerTotalTime = (audioAnswerTotalTime == null? 0L:audioAnswerTotalTime);
            AudioCourse audioCourse = new AudioCourse();
            audioCourse.setId(audioLectureGroupList.get(0).getCourseId());
            audioCourse.setTotalTime(totalTime + audioAnswerTotalTime);
            audioCourseService.createOrUpdate(audioCourse);
        }
        return audioLectureGroupList;
    }

    @Override
    public List<AudioLectureGroupDTO> findByCondition(AudioLectureGroupDTO qm) {
        List<AudioLectureGroupDTO> list = dao.findByCondition(qm);
        AudioLectureDTO search = new AudioLectureDTO();
        if (list != null) {
            for (AudioLectureGroupDTO one: list) {
                search.setGroupId(one.getId());
                List<AudioLectureDTO> audioLectureList = audioLectureService.findByCondition(search);
                one.setAudioLectureList(audioLectureList);
            }
        }
        return list;
    }
}
