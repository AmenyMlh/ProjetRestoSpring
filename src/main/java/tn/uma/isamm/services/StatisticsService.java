package tn.uma.isamm.services;

import java.time.LocalDate;
import java.util.Map;

import tn.uma.isamm.entities.Meal;

public interface StatisticsService {

	int getMealsServedByDate(LocalDate date);

	int getMealsServedByWeek(LocalDate startDate, LocalDate endDate);

	int getMealsServedByMonth(int year, int month);

	double calculateRestaurantRevenueByDate(LocalDate date);

	double calculateRestaurantRevenueByWeek(LocalDate startDate, LocalDate endDate);

	double calculateRestaurantRevenueByMonth(int year, int month);

	Map<Meal, Long> getMostPopularMeals();

}
