package com.studentaccount.validations.serviceValidation.services;

import com.studentaccount.domain.entities.Relationship;

public interface RelationshipValidationService {
    boolean isValid(Relationship relationship);
}
