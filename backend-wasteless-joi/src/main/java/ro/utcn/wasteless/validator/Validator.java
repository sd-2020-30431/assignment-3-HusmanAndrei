package ro.utcn.wasteless.validator;

import ro.utcn.wasteless.domain.BaseEntity;
import ro.utcn.wasteless.validator.exception.ValidationException;

import java.io.Serializable;

public interface Validator<T extends BaseEntity<ID>, ID extends Serializable> {
    void validate(T entity);
}
