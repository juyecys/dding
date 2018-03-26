package cn.com.dingduoduo.service.audio;

import cn.com.dingduoduo.entity.audio.AudioLecture;
import cn.com.dingduoduo.entity.audio.AudioLectureDTO;
import cn.com.dingduoduo.service.common.BaseService;

import java.util.List;

/**
 * Created by jeysine on 2018/3/16.
 */
public interface AudioLectureService extends BaseService<AudioLecture, AudioLectureDTO> {
    Long createOrUpdateByBatch(String groupId, List<AudioLectureDTO> audioLectureList) throws Exception;
    Long countAudioLectureByCourseId(String courseId);
}
