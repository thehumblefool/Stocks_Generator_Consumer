package com.company;

import java.time.LocalTime;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class StockPricesGenerator extends Thread
{
    private String [] stockNames;
    private int [] stockPrices;
    private StockPricesConsumer stockPricesConsumer;
    private Lock lock;
    private BlockingQueue<String> queue;

    public StockPricesGenerator(String [] stockNames)
    {
        this.setName("StockPricesGenerator");
        this.stockNames = stockNames;
        this.stockPrices = new int[stockNames.length];
        this.lock = new ReentrantLock(true);
        this.queue = new LinkedBlockingDeque();
        this.stockPricesConsumer = new StockPricesConsumer(stockNames, stockPrices, lock, queue);
    }

    @Override
    public void run()
    {
        while(true)
        {
            lock.lock();
            try
            {
                System.out.format("%s \t Generated ... ,", LocalTime.now());
                for(int i=0; i<stockPrices.length; ++i)
                {
                    stockPrices[i] = ThreadLocalRandom.current().nextInt(1, 101);
                    System.out.format("%s,%d,", stockNames[i], stockPrices[i]);
                }
                System.out.format("\n");
            }
            finally
            {
                lock.unlock();
            }

            try
            {
                if(stockPricesConsumer.getState() == State.NEW)
                {
                    stockPricesConsumer.start();
                }
                queue.offer("X");
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            {
                System.out.println(e);
            }
        }
    }
}
