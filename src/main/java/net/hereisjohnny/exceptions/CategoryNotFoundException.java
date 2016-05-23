package net.hereisjohnny.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by gomez on 5/17/16.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(String name) {
        super("could not found category '" + name + "'");
    }
}
