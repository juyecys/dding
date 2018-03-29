package cn.com.dingduoduo.api.admin.audio;

import cn.com.dingduoduo.api.common.ApiResult;
import cn.com.dingduoduo.entity.audio.AudioAnswerDTO;
import cn.com.dingduoduo.entity.audio.AudioCourse;
import cn.com.dingduoduo.entity.audio.AudioCourseDTO;
import cn.com.dingduoduo.entity.audio.AudioLectureGroupDTO;
import cn.com.dingduoduo.entity.common.Page;
import cn.com.dingduoduo.service.audio.AudioAnswerService;
import cn.com.dingduoduo.service.audio.AudioCourseService;
import cn.com.dingduoduo.service.audio.AudioLectureGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = {"/ding/mg/private/audio", "/ding/mg/public/audio"}, produces = "application/json")
public class PrivateAdminAudioController {

    @Autowired
    private AudioCourseService audioCourseService;

    @Autowired
    private AudioLectureGroupService audioLectureGroupService;

    @Autowired
    private AudioAnswerService audioAnswerService;

    @RequestMapping(value = "/course", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> saveOrUpdateCourse(@RequestBody AudioCourse audioCourse) throws Exception {
        audioCourse = audioCourseService.createOrUpdate(audioCourse);
        return new ResponseEntity<>(ApiResult.success(audioCourse), HttpStatus.OK);
    }

    @RequestMapping(value = "/course/page", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getCoursePage(@RequestBody AudioCourseDTO audioCourse) throws Exception {
        Page<AudioCourseDTO> page = audioCourseService.findByConditionPage(audioCourse);
        return new ResponseEntity<>(ApiResult.success(page), HttpStatus.OK);
    }

    @RequestMapping(value = "/course/count/minsequence", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> countMinSequence() throws Exception {
        Integer sequence = audioCourseService.countMinSequence();
        return new ResponseEntity<>(ApiResult.success(sequence), HttpStatus.OK);
    }

    @RequestMapping(value = "/lectureGroup", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> saveOrUpdateLecture(@RequestBody List<AudioLectureGroupDTO> audioLectureGroupList) throws Exception {
        audioLectureGroupList = audioLectureGroupService.createOrUpdateByBatch(audioLectureGroupList);
        return new ResponseEntity<>(ApiResult.success(audioLectureGroupList), HttpStatus.OK);
    }

    @RequestMapping(value = "/lectureGroup", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getLectureGroupList(AudioLectureGroupDTO audioLectureGroup) throws Exception {
        List<AudioLectureGroupDTO> audioLectureGroupList = audioLectureGroupService.findByCondition(audioLectureGroup);
        return new ResponseEntity<>(ApiResult.success(audioLectureGroupList), HttpStatus.OK);
    }

    @RequestMapping(value = "/answer", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> saveOrUpdateAnswer(@RequestBody List<AudioAnswerDTO> audioAnswerList) throws Exception {
        audioAnswerList = audioAnswerService.createOrUpdateByBatch(audioAnswerList);
        return new ResponseEntity<>(ApiResult.success(audioAnswerList), HttpStatus.OK);
    }

    @RequestMapping(value = "/answer", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getAnswerList(AudioAnswerDTO audioAnswer) throws Exception {
        List<AudioAnswerDTO> audioAnswerList = audioAnswerService.findByCondition(audioAnswer);
        return new ResponseEntity<>(ApiResult.success(audioAnswerList), HttpStatus.OK);
    }
}
