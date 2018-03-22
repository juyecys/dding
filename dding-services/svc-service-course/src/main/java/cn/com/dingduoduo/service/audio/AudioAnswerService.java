package cn.com.dingduoduo.service.audio;

import cn.com.dingduoduo.entity.audio.AudioAnswer;
import cn.com.dingduoduo.entity.audio.AudioAnswerDTO;
import cn.com.dingduoduo.service.common.BaseService;

import java.util.List;

/**
 * Created by jeysine on 2018/3/16.
 */
public interface AudioAnswerService extends BaseService<AudioAnswer, AudioAnswerDTO> {
    List<AudioAnswerDTO> createOrUpdateByBatch(List<AudioAnswerDTO> audioAnswerList) throws Exception;
    Long countAudioAnswerByCourseId(String courseId);
}
