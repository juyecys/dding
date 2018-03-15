package cn.com.dingduoduo.exception;

import cn.com.dingduoduo.api.common.ApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class AdminExceptionAdvice {

    private Logger logger = LoggerFactory.getLogger(AdminExceptionAdvice.class);

    @ExceptionHandler({AdminException.class})
    @ResponseBody
    public ResponseEntity<ApiResult> handleException(AdminException e) {
        logger.error("system error: {}", e);
        return new ResponseEntity<>(new ApiResult(e.getErrorCode().getCode(), e.getErrorCode().getDesc())
                , HttpStatus.OK);
    }

    @ExceptionHandler({Exception.class})
    @ResponseBody
    public ResponseEntity<ApiResult> handleException(Exception e) {
        logger.error("system error: {}", e);
        return new ResponseEntity<>(new ApiResult(AdminException.AdminErrorCode.ERROR.getCode(), AdminException.AdminErrorCode.ERROR.getDesc())
                , HttpStatus.OK);
    }
}
