package com.company;

import java.time.LocalTime;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Lock;

public class StockPricesConsumer extends Thread
{
    private String[] stockNames;
    private int[] stockPrices;
    private Lock lock;
    private BlockingQueue<String> queue;

    public StockPricesConsumer(String[] stockNames, int[] stockPrices, Lock lock, BlockingQueue<String> queue)
    {
        this.setName("StockPricesConsumer");
        this.stockNames = stockNames;
        this.stockPrices = stockPrices;
        this.lock = lock;
        this.queue = queue;
    }

    @Override
    public void run()
    {
        while (true)
        {
            if (queue.size() == 0)
                continue;

            queue.poll();
            int waitTime = ThreadLocalRandom.current().nextInt(80, 121);

            try
            {
                Thread.sleep(waitTime);
            }
            catch (InterruptedException e)
            {
                System.out.println(e);
            }

            if (lock.tryLock())
            {
                try
                {
                    System.out.format("%s Displaying after waiting %d milli seconds, queue Size, %d, ...\n", LocalTime.now(), waitTime, queue.size());
                    for (int i = 0; i < stockPrices.length; ++i)
                    {
                        System.out.format("%s,%d,", stockNames[i], stockPrices[i]);
                    }
                    System.out.format("\n");
                }
                finally
                {
                    lock.unlock();
                }
            }
            else
            {
                System.out.format("%s Displaying ... SKIPPED after waiting %d seconds, \n", LocalTime.now(), waitTime);
            }
        }
    }
}
