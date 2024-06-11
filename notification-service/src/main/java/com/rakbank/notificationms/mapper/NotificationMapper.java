package com.rakbank.notificationms.mapper;

import com.rakbank.notificationms.entity.Notification;
import com.rakbank.notificationms.model.NotificationRequest;
import com.rakbank.notificationms.model.NotificationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    @Mapping(target = "budgetCategory", source = "budgetCategory")
    @Mapping(target = "budgetAmt", source = "budgetAmount")
    @Mapping(target = "expenseAmt", source = "expenseAmount")
    @Mapping(target = "expenseDesc", source = "expenseDesc")
    Notification toEntity(NotificationRequest notificationRequest);

    @Mapping(target = "notificationId", source = "id")
    @Mapping(target = "budgetCategory", source = "budgetCategory")
    @Mapping(target = "budgetAmount", source = "budgetAmt")
    @Mapping(target = "expenseAmount", source = "expenseAmt")
    @Mapping(target = "expenseDesc", source = "expenseDesc")
    NotificationResponse toDomain(Notification notification);


}
