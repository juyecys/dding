package cn.com.dingduoduo.service.audio.impl;

import cn.com.dingduoduo.dao.audio.AudioLectureDAO;
import cn.com.dingduoduo.entity.audio.AudioCourse;
import cn.com.dingduoduo.entity.audio.AudioLecture;
import cn.com.dingduoduo.entity.audio.AudioLectureDTO;
import cn.com.dingduoduo.service.audio.AudioAnswerService;
import cn.com.dingduoduo.service.audio.AudioCourseService;
import cn.com.dingduoduo.service.audio.AudioLectureService;
import cn.com.dingduoduo.service.common.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * Created by jeysine on 2018/3/16.
 */
@Service("audioLectureService")
public class AudioLectureServiceImpl extends BaseServiceImpl<AudioLecture, AudioLectureDTO> implements AudioLectureService {

    @Autowired
    private AudioLectureDAO dao;

    @Autowired
    private AudioCourseService audioCourseService;

    @Autowired
    private AudioAnswerService audioAnswerService;

    @Autowired
    public void setDao(AudioLectureDAO dao) {
        this.dao = dao;
        super.setDAO(dao);
    }

    @Override
    public List<AudioLectureDTO> createOrUpdateByBatch(String groupId, List<AudioLectureDTO> audioLectureList) throws Exception {
        Long totalTime = 0L;
        if (audioLectureList == null) {
            return null;
        }
        int sequence = 1;
        for (AudioLectureDTO one: audioLectureList) {
            if (Objects.nonNull(one.getDelete()) && one.getDelete()) {
                totalTime -= one.getAudioTimestamp();
                deleteById(one.getId());
            } else {
                totalTime += one.getAudioTimestamp();
                one.setGroupId(groupId);
                one.setSequence(sequence ++);
                createOrUpdate(one);
            }
        }
        if (audioLectureList.size() > 0) {
            Long audioAnswerTotalTime = audioAnswerService.countAudioAnswerByCourseId(audioLectureList.get(0).getCourseId());
            audioAnswerTotalTime = (audioAnswerTotalTime == null? 0L:audioAnswerTotalTime);
            AudioCourse audioCourse = new AudioCourse();
            audioCourse.setId(audioLectureList.get(0).getCourseId());
            audioCourse.setTotalTime(totalTime + audioAnswerTotalTime);
            audioCourseService.createOrUpdate(audioCourse);
        }
        return audioLectureList;
    }

    @Override
    public Long countAudioLectureByCourseId(String courseId) {
        return dao.countAudioLectureByCourseId(courseId);
    }
}
