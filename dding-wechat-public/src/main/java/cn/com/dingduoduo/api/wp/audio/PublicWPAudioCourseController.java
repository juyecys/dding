package cn.com.dingduoduo.api.wp.audio;

import cn.com.dingduoduo.api.common.ApiResult;
import cn.com.dingduoduo.entity.audio.AudioAnswerDTO;
import cn.com.dingduoduo.entity.audio.AudioCourseDTO;
import cn.com.dingduoduo.entity.audio.AudioLectureDTO;
import cn.com.dingduoduo.entity.audio.AudioLectureGroupDTO;
import cn.com.dingduoduo.entity.common.Page;
import cn.com.dingduoduo.service.audio.AudioAnswerService;
import cn.com.dingduoduo.service.audio.AudioCourseService;
import cn.com.dingduoduo.service.audio.AudioLectureGroupService;
import cn.com.dingduoduo.service.audio.AudioLectureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by jeysine on 2018/3/22.
 */
@RestController
@RequestMapping(value = { "/ding/wp/public/audio","/ding/wp/private/audio" }, produces = "application/json")
public class PublicWPAudioCourseController {
    private static Logger logger = LoggerFactory.getLogger(PublicWPAudioCourseController.class);

    @Autowired
    private AudioCourseService audioCourseService;

    @Autowired
    private AudioAnswerService audioAnswerService;

    @Autowired
    private AudioLectureGroupService audioLectureGroupService;

    @RequestMapping(value = "/answer", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getAnswerList(AudioAnswerDTO audioAnswer) throws Exception {
        List<AudioAnswerDTO> audioAnswerList = audioAnswerService.findByCondition(audioAnswer);
        return new ResponseEntity<>(ApiResult.success(audioAnswerList), HttpStatus.OK);
    }

    @RequestMapping(value = "/lectureGroup", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getLectureList(AudioLectureGroupDTO audioLectureGroup) throws Exception {
        List<AudioLectureGroupDTO> audioLectureGroupList = audioLectureGroupService.findByCondition(audioLectureGroup);
        return new ResponseEntity<>(ApiResult.success(audioLectureGroupList), HttpStatus.OK);
    }

    @RequestMapping(value = "/course", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getCoursePage(AudioCourseDTO audioCourse) throws Exception {
        Page<AudioCourseDTO> page = audioCourseService.findByConditionPage(audioCourse);
        return new ResponseEntity<>(ApiResult.success(page), HttpStatus.OK);
    }

    @RequestMapping(value = "/course/one", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getOneCourse(AudioCourseDTO audioCourse) throws Exception {
        audioCourse = audioCourseService.findOneByCondition(audioCourse);
        return new ResponseEntity<>(ApiResult.success(audioCourse), HttpStatus.OK);
    }
}
