package cn.com.dingduoduo.service.audio.impl;

import cn.com.dingduoduo.dao.audio.AudioCourseDAO;
import cn.com.dingduoduo.entity.audio.AudioCourse;
import cn.com.dingduoduo.entity.audio.AudioCourseDTO;
import cn.com.dingduoduo.entity.common.Page;
import cn.com.dingduoduo.service.audio.AudioCourseService;
import cn.com.dingduoduo.service.common.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by jeysine on 2018/3/16.
 */
@Service("audioCourseService")
public class AudioCourseServiceImpl extends BaseServiceImpl<AudioCourse, AudioCourseDTO> implements AudioCourseService {
    @Autowired
    private AudioCourseDAO dao;


    @Autowired
    public void setDao(AudioCourseDAO dao) {
        this.dao = dao;
        super.setDAO(dao);
    }

    @Override
    public Integer countMinSequence() {
        return dao.countMinSequence();
    }

    @Override
    public List<AudioCourseDTO> findNeedOrderBySequence(AudioCourseDTO audioCourse) {
        return dao.findNeedOrderBySequence(audioCourse);
    }

    @Transactional
    @Override
    public AudioCourse createOrUpdate(AudioCourse entity) throws Exception {
        if (entity.getSequence() != null) {
            updateSequence(entity);
        }
        if (entity.getId() == null) {
            entity.setSequence(entity.getSequence() - 1);
            return create(entity);
        }
        return update(entity);
    }

    private synchronized void updateSequence(AudioCourse newAudioCourse) {
        List<AudioCourseDTO> list = null;
        AudioCourseDTO oldAudioCourse = new AudioCourseDTO();
        AudioCourseDTO updateAudioCourse = new AudioCourseDTO();
        int sequenceStep = 1;
        if (newAudioCourse.getId() != null) {
            oldAudioCourse.setId(newAudioCourse.getId());
            oldAudioCourse = findOneByCondition(oldAudioCourse);

            if (newAudioCourse.getSequence() > oldAudioCourse.getSequence()) {
                updateAudioCourse.setSequenceEnd(newAudioCourse.getSequence());
                updateAudioCourse.setSequenceStart(oldAudioCourse.getSequence());
                list = findNeedOrderBySequence(updateAudioCourse);
                sequenceStep = -1;
            } else if (newAudioCourse.getSequence() < oldAudioCourse.getSequence()) {
                updateAudioCourse.setSequenceEnd(oldAudioCourse.getSequence());
                updateAudioCourse.setSequenceStart(newAudioCourse.getSequence());
                list = findNeedOrderBySequence(updateAudioCourse);
            }
        }

        if (list != null) {
            for (AudioCourseDTO one: list) {
                one.setSequence(one.getSequence() + sequenceStep);
                update(one);
            }
        }
    }

}
