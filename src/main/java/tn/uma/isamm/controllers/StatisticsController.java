package tn.uma.isamm.controllers;

import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tn.uma.isamm.services.StatisticsService;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {
	    @Autowired
	    private StatisticsService statisticsService;

	    @GetMapping("/meals-served/day/{date}")
	    public int getMealsServedByDate(@PathVariable LocalDate date) {
	        return statisticsService.getMealsServedByDate(date);
	    }

	    @GetMapping("/meals-served/week")
	    public int getMealsServedByWeek(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
	        return statisticsService.getMealsServedByWeek(startDate, endDate);
	    }

	    @GetMapping("/meals-served/month")
	    public int getMealsServedByMonth(@RequestParam int year, @RequestParam int month) {
	        return statisticsService.getMealsServedByMonth(year, month);
	    }

	    @GetMapping("/revenue/day/{date}")
	    public double getRestaurantRevenueByDate(@PathVariable LocalDate date) {
	        return statisticsService.calculateRestaurantRevenueByDate(date);
	    }

	    @GetMapping("/revenue/week")
	    public double getRestaurantRevenueByWeek(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
	        return statisticsService.calculateRestaurantRevenueByWeek(startDate, endDate);
	    }

	    @GetMapping("/revenue/month")
	    public double getRestaurantRevenueByMonth(@RequestParam int year, @RequestParam int month) {
	        return statisticsService.calculateRestaurantRevenueByMonth(year, month);
	    }

	    @GetMapping("/most-popular-meals")
	    public Map<String, Long> getMostPopularMeals() {
	        return statisticsService.getMostPopularMeals().entrySet().stream()
	                .collect(Collectors.toMap(
	                        entry -> entry.getKey().getName(), 
	                        Map.Entry::getValue
	                ));
	    }

}
