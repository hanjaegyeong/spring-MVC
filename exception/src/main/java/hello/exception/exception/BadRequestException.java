package hello.exception.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "잘못된 요청 오류")
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "error.bad") // 400 던지는 사용자 정의 예외. error.bad는 messages.properties에서 가져옴
public class BadRequestException extends RuntimeException {
}

