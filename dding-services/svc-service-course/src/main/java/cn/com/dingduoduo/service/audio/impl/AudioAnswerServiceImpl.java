package cn.com.dingduoduo.service.audio.impl;

import cn.com.dingduoduo.dao.audio.AudioAnswerDAO;
import cn.com.dingduoduo.entity.audio.AudioAnswer;
import cn.com.dingduoduo.entity.audio.AudioAnswerDTO;
import cn.com.dingduoduo.entity.audio.AudioCourse;
import cn.com.dingduoduo.service.audio.AudioAnswerService;
import cn.com.dingduoduo.service.audio.AudioCourseService;
import cn.com.dingduoduo.service.common.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * Created by jeysine on 2018/3/16.
 */
@Service("audioAnswerService")
public class AudioAnswerServiceImpl extends BaseServiceImpl<AudioAnswer, AudioAnswerDTO> implements AudioAnswerService {
    @Autowired
    private AudioAnswerDAO dao;

    @Autowired
    private AudioCourseService audioCourseService;

    @Autowired
    public void setDao(AudioAnswerDAO dao) {
        this.dao = dao;
        super.setDAO(dao);
    }

    @Override
    public List<AudioAnswerDTO> createOrUpdateByBatch(List<AudioAnswerDTO> audioAnswerList) throws Exception {
        Long totalTime = 0L;
        if (audioAnswerList == null) {
            return null;
        }
        for (AudioAnswerDTO one: audioAnswerList) {
            if (Objects.nonNull(one.getDelete()) && one.getDelete()) {
                totalTime -= one.getAudioTimestamp();
                deleteById(one.getId());
            } else {
                totalTime += one.getAudioTimestamp();
                createOrUpdate(one);
            }
        }
        if (audioAnswerList.size() > 0) {
            AudioCourse audioCourse = new AudioCourse();
            audioCourse.setId(audioAnswerList.get(0).getCourseId());
            audioCourse.setTotalTime(totalTime);
            audioCourseService.createOrUpdate(audioCourse);
        }
        return audioAnswerList;
    }
}
