package com.studentaccount.validations.serviceValidation.servicesImpl;

import com.studentaccount.domain.entities.Relationship;
import com.studentaccount.validations.serviceValidation.services.RelationshipValidationService;
import org.springframework.stereotype.Component;

@Component
public class RelationshipValidationServiceImpl implements RelationshipValidationService {
    @Override
    public boolean isValid(Relationship relationship) {
        return relationship != null;
    }
}
