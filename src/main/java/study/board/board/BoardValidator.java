package study.board.board;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class BoardValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return BoardDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        BoardDto boardDto = (BoardDto) target;

        if (!StringUtils.hasText(boardDto.getTitle())) {
            errors.rejectValue("title", "required");
        }

        if (!StringUtils.hasText(boardDto.getContent())) {
            errors.rejectValue("content", "re.board.content");
        }
    }
}
