package com.vincentrungogh.global.exception;

import com.vincentrungogh.global.util.ResultDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@Slf4j
@ControllerAdvice
public class GlobalExcpetionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleCustomException(final CustomException ex) {
        ResultDto<Object> response = ResultDto.res(
                ex.getErrorCode().getHttpStatus().value(),
                ex.getMessage()
        );

        return new ResponseEntity<>(response, ex.getErrorCode().getHttpStatus());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(final RuntimeException ex) {
        ResultDto<Object> response = ResultDto.res(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "서버 내부 오류가 발생했습니다. 잠시 후 다시 시도해 주세요."
        );

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(final Exception ex) {
        ResultDto<Object> response = ResultDto.res(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "서버 내부 오류가 발생했습니다. 잠시 후 다시 시도해 주세요."
        );

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<?> handleMaxSizeException(MaxUploadSizeExceededException exc) {
        ResultDto<Object> response = ResultDto.res(
                HttpStatus.BAD_REQUEST.value(),
                "파일 크기가 너무 큽니다. 최대 10MB까지 업로드할 수 있습니다."
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
