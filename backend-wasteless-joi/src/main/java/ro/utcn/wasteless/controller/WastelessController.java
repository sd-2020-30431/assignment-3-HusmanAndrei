package ro.utcn.wasteless.controller;


import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.utcn.wasteless.config.ObserverConfig;
import ro.utcn.wasteless.converter.DateUtil;
import ro.utcn.wasteless.converter.GroceryItemConverter;
import ro.utcn.wasteless.converter.UserConverter;
import ro.utcn.wasteless.domain.GroceryItem;
import ro.utcn.wasteless.domain.User;
import ro.utcn.wasteless.dto.GroceryItemDto;
import ro.utcn.wasteless.dto.UserDto;
import ro.utcn.wasteless.dto.reports.ReportDto;
import ro.utcn.wasteless.mediator.Handler;
import ro.utcn.wasteless.mediator.Mediator;
import ro.utcn.wasteless.mediator.command.AddGrocery;
import ro.utcn.wasteless.mediator.command.DeleteItem;
import ro.utcn.wasteless.mediator.command.LoginUser;
import ro.utcn.wasteless.mediator.handler.*;
import ro.utcn.wasteless.mediator.query.GetItemsByUserQuery;
import ro.utcn.wasteless.mediator.query.GetUserId;
import ro.utcn.wasteless.mediator.response.*;
import ro.utcn.wasteless.observer.DateChangeObservable;
import ro.utcn.wasteless.observer.notifier.NotifyService;
import ro.utcn.wasteless.report.FactoryProvider;
import ro.utcn.wasteless.report.ReportAbstractFactory;
import ro.utcn.wasteless.report.service.ReportService;
import ro.utcn.wasteless.report.types.month.MonthlyReport;
import ro.utcn.wasteless.report.types.week.WeeklyReport;
import ro.utcn.wasteless.service.WastelessService;

import javax.swing.text.html.Option;
import javax.validation.Validation;
import javax.validation.ValidationException;
import java.util.*;

@RestController
public class WastelessController {


    @Autowired
    Mediator mediator;

    @Autowired
    UserConverter userConverter;

    @Autowired
    GroceryItemConverter groceryItemConverter;

    @Autowired
    NotifyService notifyService;

    @Autowired
    ReportService reportService;

    @Autowired
    DateChangeObservable dateChangeObservable;


    private Date currentDate = new Date();

//    @PostMapping("/user")
//    public UserDto createUser(@RequestBody UserDto userDto){
//        //Utilitar, nefolosit de app. Folosit din Postman.
//        User user = userConverter.convertToModel(userDto);
//        user = service.saveUser(user);
//        return userConverter.convertToDto(user);
//    }

    @PostMapping("/login")
    public UserDto loginUser(@RequestBody UserDto userInfo){
        System.out.println(userInfo.toString());
        LoginUser loginUser = new LoginUser(userInfo.getName(), userInfo.getPassword());
        LoginUserHandler loginUserHandler = (LoginUserHandler)mediator.<LoginUser, LoginUserResponse>getHandler(loginUser);
        Optional<User> user = loginUserHandler.handle(loginUser).getUserOptional();
        if(user.isEmpty()){
            return userInfo;
        }
        return userConverter.convertToDto(user.get());
    }


    private Long getUserId(String stringId){
        String actualId = stringId.split(" ")[1];
        Long userID = Long.parseLong(actualId);
        return userID;
    }

    private Optional<User> getUser(String stringId){
        Long id = getUserId(stringId);
        GetUserId getUserId = new GetUserId(id);
        GetUserIdHandler getItemsByUserHandler = (GetUserIdHandler) mediator.<GetUserId, GetUserIdResponse>getHandler(getUserId);
        GetUserIdResponse userIdResponse = getItemsByUserHandler.handle(getUserId);
        return userIdResponse.getUserOptional();
    }

    @PostMapping("/grocery")
    public GroceryItemDto addGrocery(@RequestBody GroceryItemDto itemDto, @RequestHeader(name="Authorization") String stringId){
        Optional<User> user = getUser(stringId);
        GroceryItem item = groceryItemConverter.convertToModel(itemDto);
        if(user.isEmpty()){
            return null;
        }
        AddGrocery addGrocery = new AddGrocery(item, user.get());
        AddGroceryHandler addGroceryHandler = (AddGroceryHandler)(mediator.<AddGrocery, AddGroceryResponse>getHandler(addGrocery));
        AddGroceryResponse addGroceryResponse = addGroceryHandler.handle(addGrocery);
        return groceryItemConverter.convertToDto(addGroceryResponse.getGroceryItem());

    }



    @Autowired
    private FactoryProvider factoryProvider;

    @GetMapping("/stats/weekly")
    public int getWeekly(@RequestParam boolean toCaloric, @RequestHeader(name="Authorization") String stringId){
        //Report -> uses the abstract factory, no need of mediation
        ReportAbstractFactory abstractFactory = factoryProvider.getFactory(toCaloric);
        WeeklyReport weeklyReport = abstractFactory.getWeeklyReport();
        Optional<User> userOptional = getUser(stringId);
        if (userOptional.isEmpty()){
            return -1;
        }
        return weeklyReport.getTotalByWeek(currentDate, userOptional.get());
    }
    @GetMapping("/stats/monthly")
    //Report -> uses the abstract factory, no need of mediation
    public int getMonthly(@RequestParam boolean toCaloric,  @RequestHeader(name="Authorization") String stringId){
        ReportAbstractFactory abstractFactory = factoryProvider.getFactory(toCaloric);
        MonthlyReport monthlyReport = abstractFactory.getMonthlyReport();
        Optional<User> userOptional = getUser(stringId);
        if (userOptional.isEmpty()){
            return -1;
        }
        return monthlyReport.getTotalByMonth(currentDate, userOptional.get());
    }

    @GetMapping("/stats/all")
    public ReportDto getAllReports(@RequestHeader(name="Authorization") String stringId){
        //Report -> uses the abstract factory, no need of mediation
        Optional<User> user = getUser(stringId);
        if(user.isEmpty()){
            return null;
        }
        return reportService.getAllReports(user.get(), currentDate);

    }


    @GetMapping("/grocery")
    public List<GroceryItemDto> getItemsByUser(@RequestHeader(name="Authorization") String stringId){
        Long userId = getUserId(stringId);
        GetItemsByUserQuery getItemsByUserQuery = new GetItemsByUserQuery(userId);
        GetItemsByUserHandler handler = (GetItemsByUserHandler) mediator.<GetItemsByUserQuery, GetItemsByUserResponse>getHandler(getItemsByUserQuery);
        GetItemsByUserResponse response = handler.handle(getItemsByUserQuery);

        List<GroceryItemDto> items = groceryItemConverter.convertAllToDtos(response.getGroceryItems());
        return items;
    }



    @GetMapping("/report")
    public Object getInvalidUsers(){
        //Reports, no need of mediator
        Map<User, Map<Date, Integer>> stat = notifyService.getNonIdealUsersByBurndownRate(currentDate);
        Map<UserDto, Map<Date, Integer>> dtoStat = new HashMap<>();
        stat.keySet().forEach(key -> {
            UserDto dto = userConverter.convertToDto(key);
            dto.setID(null);
            dtoStat.put(dto, stat.get(key));
        });
        return dtoStat;
    }

    @DeleteMapping("/donate")
    public GroceryItemDto donateItem(@RequestParam Long itemId, @RequestHeader(name="Authorization")String stringId){
        DeleteItem deleteItem = new DeleteItem(itemId);
        DeleteItemHandler deleteItemHandler = (DeleteItemHandler) mediator.<DeleteItem, DeleteItemResponse>getHandler(deleteItem);
        DeleteItemResponse deleteItemResponse = deleteItemHandler.handle(deleteItem);
        Optional<GroceryItem> item = deleteItemResponse.getGroceryItem();
        if(item.isEmpty()){
            return null;
        }
        return groceryItemConverter.convertToDto(item.get());
    }

    @PutMapping("/date")
    public String changeDate(@RequestParam String newDate){
        Date date = DateUtil.convertStringToDate(newDate);
        dateChangeObservable.changeDate(date);
        this.currentDate = date;
        return DateUtil.convertDateToString(this.currentDate);

    }
    @GetMapping("/date")
    public String getCurrentDate(){
        return DateUtil.convertDateToString(this.currentDate);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({ValidationException.class})
    @ResponseBody
    public ResponseEntity<ExceptionCause> handleException(ValidationException ex) {
        ExceptionCause exceptionCause = ExceptionCause.builder().cause(ex.getMessage()).build();
        return new ResponseEntity<>(exceptionCause, HttpStatus.INTERNAL_SERVER_ERROR);
    }




}
