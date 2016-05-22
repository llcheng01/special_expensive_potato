package net.hereisjohnny.exceptions;

/**
 * Created by gomez on 5/17/16.
 */
public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(String name) {
        super("could not found category '" + name + "'");
    }
}
