package com.nnk.springboot;

import com.nnk.springboot.domain.Rating;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class TestVariables {

    protected Rating rating;
    protected List<Rating> ratingList;
    protected Optional<Rating> ratingOptional;

    protected String longString;

    protected String ratingFitchRatingSize;
    protected String ratingIdMin;
    protected String ratingIdMax;
    protected String ratingIdNotNull;
    protected String ratingMoodysRatingSize;
    protected String ratingOrderMin;
    protected String ratingOrderMax;
    protected String ratingSandPRatingSize;

    protected FieldError fieldError;
    protected FieldError fieldErrorIdNotNull;

    public void initializeVariables() {
        rating = new Rating("moodysRatingTestValue", "sandPRatingTestValue", "fitchRatingTestValue", 10);
        rating.setId(1);
        ratingList = new ArrayList<>(List.of(rating));
        ratingOptional = Optional.of(rating);

        // 126 characters string used for size validation
        longString = "85368220096847824275049423209683" +
                "35786401076532718070358521773728" +
                "25842050212176069074456918678132" +
                "635754452771884752599433457836";

        ratingFitchRatingSize = "fitchRating should be less than 126 characters";
        ratingIdMin = "id should be more than -129";
        ratingIdMax = "id should be less than 128";
        ratingIdNotNull = "id should not be null";
        ratingMoodysRatingSize = "moodysRating should be less than 126 characters";
        ratingOrderMin = "order should be more than -129";
        ratingOrderMax = "order should be less than 128";
        ratingSandPRatingSize = "sandPRating should be less than 126 characters";

        fieldError = new FieldError("objectName",
                "field","rejectedValue",
                false, new String[]{}, new String[]{}, "defaultMessage");
        fieldErrorIdNotNull = new FieldError("objectName",
                "field","rejectedValue",
                false, new String[]{"NotNull.id"}, new String[]{}, "defaultMessage");
    }

}
