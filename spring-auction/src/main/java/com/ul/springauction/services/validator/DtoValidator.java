package com.ul.springauction.services.validator;

import com.ul.springauction.shared.dto.DtoObject;
import com.ul.springauction.shared.exception.BadRequestException;
import com.ul.springauction.shared.response.ErrorResponse;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Service appelé pour faire la vérification des DTO
 * Si un ou des champs sont mal remplis, une erreur 400 est retournée
 */
@Service
public class DtoValidator {

    public ErrorResponse validate (DtoObject object){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<DtoObject>> violations = validator.validate(object);
        List<String> violationMessages = new ArrayList<>();
        for(ConstraintViolation<DtoObject> v : violations){
            violationMessages.add(v.getMessage());
        }
        return new ErrorResponse(violationMessages);
    }

    public void validation(DtoObject object) throws BadRequestException {
        ErrorResponse errors = validate(object);
        if (errors.getMessage().size() > 0){
            throw new BadRequestException(errors.getMessage().get(0));
        }
    }
}
