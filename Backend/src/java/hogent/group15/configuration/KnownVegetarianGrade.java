package hogent.group15.configuration;

import hogent.group15.User;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author Frederik
 */
@Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = KnownVegetarianGrade.KnownVegetarianGradeValidator.class)
public @interface KnownVegetarianGrade {
    
    String message() default "grade";

    public static class KnownVegetarianGradeValidator implements ConstraintValidator<KnownVegetarianGrade, User.VegetarianGrade> {

	@Override
	public void initialize(KnownVegetarianGrade constraintAnnotation) {
	}

	@Override
	public boolean isValid(User.VegetarianGrade value, ConstraintValidatorContext context) {
	    return value != User.VegetarianGrade.UNKOWN;
	}
	
    }
}
