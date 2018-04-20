package cn.com.dingduoduo.api.wp.audio;

import cn.com.dingduoduo.api.common.ApiResult;
import cn.com.dingduoduo.contants.wp.WechatPublicContants;
import cn.com.dingduoduo.entity.audio.*;
import cn.com.dingduoduo.entity.common.Page;
import cn.com.dingduoduo.entity.courseorder.CourseOrder;
import cn.com.dingduoduo.entity.courseorder.CourseOrderDTO;
import cn.com.dingduoduo.service.audio.AudioAnswerService;
import cn.com.dingduoduo.service.audio.AudioCourseService;
import cn.com.dingduoduo.service.audio.AudioLectureGroupService;
import cn.com.dingduoduo.service.audio.AudioLectureService;
import cn.com.dingduoduo.service.courseorder.CourseOrderService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    private CourseOrderService courseOrderService;

    @RequestMapping(value = "/answer", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getAnswerList(HttpServletRequest request, AudioAnswerDTO audioAnswer) throws Exception {
        List<AudioAnswerDTO> audioAnswerList = audioAnswerService.findByCondition(audioAnswer);
        AudioCourseDTO audioCourse = new AudioCourseDTO();
        audioCourse.setId(audioAnswer.getCourseId());
        audioCourse = audioCourseService.findOneByCondition(audioCourse);
        if (!audioCourse.getFree() && !checkCourseBuy(request, audioAnswer.getCourseId()) ) {
            filterAudioAnswerUrl(audioAnswerList);
        }
        return new ResponseEntity<>(ApiResult.success(audioAnswerList), HttpStatus.OK);
    }

    @RequestMapping(value = "/lectureGroup", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getLectureList(HttpServletRequest request, AudioLectureGroupDTO audioLectureGroup) throws Exception {
        List<AudioLectureGroupDTO> audioLectureGroupList = audioLectureGroupService.findByCondition(audioLectureGroup);
        AudioCourseDTO audioCourse = new AudioCourseDTO();
        audioCourse.setId(audioLectureGroup.getCourseId());
        audioCourse = audioCourseService.findOneByCondition(audioCourse);
        if (!audioCourse.getFree() && !checkCourseBuy(request, audioLectureGroup.getCourseId())) {
            filterAudioLectureUrl(audioLectureGroupList);
        }
        return new ResponseEntity<>(ApiResult.success(audioLectureGroupList), HttpStatus.OK);
    }

    @RequestMapping(value = "/course", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getCoursePage(AudioCourseDTO audioCourse) throws Exception {
        Page<AudioCourseDTO> page = audioCourseService.findByConditionPage(audioCourse);
        return new ResponseEntity<>(ApiResult.success(page), HttpStatus.OK);
    }

    @RequestMapping(value = "/course/one", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getOneCourse(HttpServletRequest request, AudioCourseDTO audioCourse) throws Exception {
        audioCourse = audioCourseService.findOneByCondition(audioCourse);
        String courseId = audioCourse.getId();
        audioCourse.setBuy(checkCourseBuy(request, courseId));
        return new ResponseEntity<>(ApiResult.success(audioCourse), HttpStatus.OK);
    }

    @RequestMapping(value = "/course/page/notbuy", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getUserNotBuy(HttpServletRequest request, AudioCourseDTO audioCourse) throws Exception {
        String openId = (String) request.getSession().getAttribute(WechatPublicContants.SESSION_OPENID);
        audioCourse.setOpenId(openId);
        Page<AudioCourseDTO> page = audioCourseService.findByConditionPage(audioCourse);
        return new ResponseEntity<>(ApiResult.success(page), HttpStatus.OK);
    }

    /**
     * 检测用户是否购买课程
     * @param request
     * @param courseId
     * @return
     */
    private Boolean checkCourseBuy(HttpServletRequest request, String courseId) {
        String openId = (String) request.getSession().getAttribute(WechatPublicContants.SESSION_OPENID);
        if (openId == null) {
            return false;
        }
        CourseOrderDTO courseOrder = new CourseOrderDTO();
        courseOrder.setCourseId(courseId);
        courseOrder.setOpenId(openId);
        courseOrder.setStatus(CourseOrder.CourseOrderStatusEnum.PAID.name());
        courseOrder = courseOrderService.findOneByCondition(courseOrder);
        return courseOrder != null;
    }

    private void filterAudioLectureUrl(List<AudioLectureGroupDTO> audioLectureGroupList) {
        if (audioLectureGroupList == null) {
            return ;
        }
        for (AudioLectureGroupDTO one: audioLectureGroupList) {
            for (AudioLectureDTO lecture: one.getAudioLectureList()) {
                if (!lecture.getFreeListen()) {
                    lecture.setAudioUrl(null);
                }
            }
        }
    }

    private void filterAudioAnswerUrl(List<AudioAnswerDTO> audioAnswerList) {
        if (audioAnswerList == null) {
            return ;
        }
        for (AudioAnswerDTO one: audioAnswerList) {
            if (!one.getFreeListen()) {
                one.setAudioUrl(null);
            }
        }
    }
}
