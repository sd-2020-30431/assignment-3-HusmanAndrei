package ro.utcn.wasteless.converter;

import org.springframework.stereotype.Component;
import ro.utcn.wasteless.domain.GroceryItem;
import ro.utcn.wasteless.dto.GroceryItemDto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static ro.utcn.wasteless.converter.DateUtil.convertDateToString;
import static ro.utcn.wasteless.converter.DateUtil.convertStringToDate;

@Component
public class GroceryItemConverter extends BaseConverter<GroceryItem, GroceryItemDto> {
    @Override
    public GroceryItem convertToModel(GroceryItemDto groceryItemDto) {
        Date purchaseDate = convertStringToDate(groceryItemDto.getPurchaseDate());
        Date expirationDate = convertStringToDate(groceryItemDto.getExpirationDate());
        Date consumptionDate = null;
        if(groceryItemDto.getConsumptionDate() != null){
            consumptionDate = convertStringToDate(groceryItemDto.getConsumptionDate());
        }
        GroceryItem item =  GroceryItem.builder().calories(groceryItemDto.getCalories()).name(groceryItemDto.getName()).quantity(groceryItemDto.getQuantity())
                .purchaseDate(purchaseDate).expirationDate(expirationDate).consumptionDate(consumptionDate).build();
        item.setId(groceryItemDto.getID());
        return item;
    }

    @Override
    public GroceryItemDto convertToDto(GroceryItem entity) {
        String purchaseDate = convertDateToString(entity.getPurchaseDate());
        String expirationDate = convertDateToString(entity.getExpirationDate());
        String consumptionDate = null;
        if (entity.getConsumptionDate() != null) {
            consumptionDate = convertDateToString(entity.getConsumptionDate());
        }
        return GroceryItemDto.builder().name(entity.getName()).calories(entity.getCalories()).ID(entity.getId()).quantity(entity.getQuantity())
                .consumptionDate(consumptionDate).expirationDate(expirationDate).purchaseDate(purchaseDate).build();
    }


}
