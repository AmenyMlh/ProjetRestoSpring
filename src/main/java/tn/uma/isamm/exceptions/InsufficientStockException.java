package tn.uma.isamm.exceptions;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(String ingredientName) {
        super("Stock insuffisant pour l'ingrédient : " + ingredientName);
    }
}
