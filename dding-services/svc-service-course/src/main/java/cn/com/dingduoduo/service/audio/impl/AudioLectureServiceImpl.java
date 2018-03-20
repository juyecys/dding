package cn.com.dingduoduo.service.audio.impl;

import cn.com.dingduoduo.dao.audio.AudioLectureDAO;
import cn.com.dingduoduo.entity.audio.AudioLecture;
import cn.com.dingduoduo.entity.audio.AudioLectureDTO;
import cn.com.dingduoduo.service.audio.AudioLectureService;
import cn.com.dingduoduo.service.common.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jeysine on 2018/3/16.
 */
@Service("audioLectureService")
public class AudioLectureServiceImpl extends BaseServiceImpl<AudioLecture, AudioLectureDTO> implements AudioLectureService {

    @Autowired
    private AudioLectureDAO dao;

    @Autowired
    public void setDao(AudioLectureDAO dao) {
        this.dao = dao;
        super.setDAO(dao);
    }

    @Override
    public List<AudioLectureDTO> createOrUpdateByBatch(String groupId, List<AudioLectureDTO> audioLectureList) throws Exception {
        for (AudioLectureDTO one: audioLectureList) {
            if (one.getDelete()) {
                deleteById(one.getId());
            } else {
                one.setGroupId(groupId);
                createOrUpdate(one);
            }
        }
        return audioLectureList;
    }
}
