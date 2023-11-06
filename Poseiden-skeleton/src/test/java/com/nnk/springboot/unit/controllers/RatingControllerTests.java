package com.nnk.springboot.unit.controllers;

import com.nnk.springboot.TestVariables;
import com.nnk.springboot.controllers.RatingController;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = RatingController.class)
public class RatingControllerTests extends TestVariables {

    @Autowired
    RatingController ratingController;

    @MockBean
    private RatingService ratingService;

    @MockBean
    private BindingResult bindingResult;

    private Model model;

    @BeforeEach
    public void setUpPerTest() {
        initializeVariables();
        model = new Model() {
            @Override
            public Model addAttribute(String attributeName, Object attributeValue) {
                return null;
            }

            @Override
            public Model addAttribute(Object attributeValue) {
                return null;
            }

            @Override
            public Model addAllAttributes(Collection<?> attributeValues) {
                return null;
            }

            @Override
            public Model addAllAttributes(Map<String, ?> attributes) {
                return null;
            }

            @Override
            public Model mergeAttributes(Map<String, ?> attributes) {
                return null;
            }

            @Override
            public boolean containsAttribute(String attributeName) {
                return false;
            }

            @Override
            public Object getAttribute(String attributeName) {
                return null;
            }

            @Override
            public Map<String, Object> asMap() {
                return null;
            }
        };

        when(ratingService.findAll()).thenReturn(ratingList);
        when(ratingService.findById(any(Integer.class))).thenReturn(ratingOptional);
        when(ratingService.findById(any(Integer.class))).thenReturn(ratingOptional);
        when(bindingResult.getErrorCount()).thenReturn(0);
        when(bindingResult.getFieldError()).thenReturn(fieldError);
    }

    @Test
    public void ContextLoads() {}

    @Nested
    public class HomeTests
    {
        @Test
        public void homeTest () {
            assertEquals("rating/list", ratingController.home(model));
            verify(ratingService, Mockito.times(1)).findAll();
        }
    }

    @Nested
    public class AddTests
    {
        @Test
        public void addRatingFormTest () {
            assertEquals("rating/add", ratingController.addRatingForm(rating));
        }
        @Test
        public void addRatingFormTestIfEmpty () {
            assertEquals("rating/add", ratingController.addRatingForm(new Rating()));
        }
        @Test
        public void addRatingFormTestIfNull () {
            assertEquals("rating/add", ratingController.addRatingForm(null));
        }
    }

    @Nested
    public class ValidateTests
    {
        @Test
        public void validateTest () {when(bindingResult.getErrorCount()).thenReturn(1);
            when(bindingResult.getFieldError()).thenReturn(fieldErrorIdNotNull);
            assertEquals("rating/add", ratingController.validate(rating, bindingResult, model));
            verify(ratingService, Mockito.times(1)).save(any(Rating.class));
        }
        @Test
        public void validateTestIfRatingHasNoError () {
            when(bindingResult.getErrorCount()).thenReturn(0);
            assertEquals("rating/add", ratingController.validate(rating, bindingResult, model));
            verify(ratingService, Mockito.times(0)).save(any(Rating.class));
        }
        @Test
        public void validateTestIfRatingHasUnexpectedError () {
            when(bindingResult.getErrorCount()).thenReturn(1);
            when(bindingResult.getFieldError()).thenReturn(fieldError);
            assertEquals("rating/add", ratingController.validate(rating, bindingResult, model));
            verify(ratingService, Mockito.times(0)).save(any(Rating.class));
        }
    }

    @Nested
    public class ShowUpdateTests
    {
        @Test
        public void showUpdateFormTest () {
            assertEquals("rating/update", ratingController.showUpdateForm(rating.getId(), model));
            verify(ratingService, Mockito.times(1)).findById(any(Integer.class));
        }
    }

    @Nested
    public class UpdateRatingTests
    {
        @Test
        public void updateRatingTest () {
            assertEquals("redirect:/rating/list", ratingController.updateRating(rating.getId(), rating, bindingResult, model));
            verify(ratingService, Mockito.times(1)).findById(any(Integer.class));
            verify(ratingService, Mockito.times(1)).save(any(Rating.class));
        }
        @Test
        public void updateRatingTestIfInvalidRating () {
            when(bindingResult.getErrorCount()).thenReturn(1);
            assertEquals("redirect:/rating/list", ratingController.updateRating(rating.getId(), rating, bindingResult, model));
            verify(ratingService, Mockito.times(0)).findById(any(Integer.class));
            verify(ratingService, Mockito.times(0)).save(any(Rating.class));
        }
        @Test
        public void updateRatingTestIfRatingNotInDB () {
            when(ratingService.findById(any(Integer.class))).thenReturn(Optional.empty());
            assertEquals("redirect:/rating/list", ratingController.updateRating(rating.getId(), rating, bindingResult, model));
            verify(ratingService, Mockito.times(1)).findById(any(Integer.class));
            verify(ratingService, Mockito.times(0)).save(any(Rating.class));
        }
    }

    @Nested
    public class DeleteRatingTests
    {
        @Test
        public void deleteRatingTest () {
            assertEquals("redirect:/rating/list", ratingController.deleteRating(rating.getId(), model));
            verify(ratingService, Mockito.times(1)).findById(any(Integer.class));
            verify(ratingService, Mockito.times(1)).deleteById(any(Integer.class));
        }
        @Test
        public void deleteRatingTestIfRatingNotInDB () {
            when(ratingService.findById(any(Integer.class))).thenReturn(Optional.empty());
            assertEquals("redirect:/rating/list", ratingController.deleteRating(rating.getId(), model));
            verify(ratingService, Mockito.times(1)).findById(any(Integer.class));
            verify(ratingService, Mockito.times(0)).deleteById(any(Integer.class));
        }
    }
}
