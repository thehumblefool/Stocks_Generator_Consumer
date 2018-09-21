package com.company;

public class Main
{
    public static void main(String[] args)
    {
        String [] stockNames = new String[]{"Reliance", "ICICI", "TCS", "BankOfBaroda", "Cipla"};
        StockPricesGenerator stockPricesGenerator = new StockPricesGenerator(stockNames);
        stockPricesGenerator.start();
    }
}
