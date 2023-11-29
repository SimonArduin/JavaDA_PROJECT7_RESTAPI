package com.nnk.springboot.unit.domain;

import com.nnk.springboot.TestVariables;
import com.nnk.springboot.domain.CurvePoint;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = CurvePoint.class)
public class CurvePointTests extends TestVariables {

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    @BeforeEach
    public void setUpPerTest() {
        initializeVariables();
    }

    @Test
    public void ContextLoads() {}

    @Nested
    public class ValidationTests {

        @Test
        public void ValidationTest() {
            Set<ConstraintViolation<CurvePoint>> result = validator.validate(curvePoint);
            assertTrue(result.isEmpty());
        }

        @Nested
        public class CreationDateTests
        {
            @Test
            public void ValidationTestIfCreationDateSize () {
                curvePoint.setCreationDate(new Timestamp(System.currentTimeMillis() + 999999));
                Set<ConstraintViolation<CurvePoint>> result = validator.validate(curvePoint);
                assertEquals(1, result.size());
                ConstraintViolation<CurvePoint> constraintViolation = (ConstraintViolation<CurvePoint>) result.toArray()[0];
                assertEquals("creationDate", constraintViolation.getPropertyPath().toString());
                assertEquals(curvePointCreationDatePast, constraintViolation.getMessage());
            }
        }
    }
}