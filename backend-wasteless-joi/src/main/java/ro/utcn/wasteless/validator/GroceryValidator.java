package ro.utcn.wasteless.validator;

import org.springframework.stereotype.Component;
import ro.utcn.wasteless.domain.GroceryItem;
import ro.utcn.wasteless.validator.exception.ValidationException;

@Component
public class GroceryValidator implements Validator<GroceryItem, Long> {

    @Override
    public void validate(GroceryItem entity)  {
        if(entity.getExpirationDate() == null || entity.getPurchaseDate() == null){
            throw new ValidationException("Expiration and Purchase dates should not be null");
        }
        if(entity.getConsumptionDate() != null){
            if(entity.getPurchaseDate().after(entity.getConsumptionDate())){
                throw new ValidationException("Purchase date should be before the consumption date");
            }

            if(entity.getExpirationDate().before(entity.getConsumptionDate())){
                throw new ValidationException("Expiration date should be after or equal to consumption date");
            }

        }
        if(entity.getPurchaseDate().after(entity.getExpirationDate())){
            throw new ValidationException("Expiration date should be after the purchase date");
        }

    }
}
