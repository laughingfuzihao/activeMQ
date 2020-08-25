package com.laughing.broker;

import org.apache.activemq.broker.BrokerService;

import java.awt.print.Book;

/**
 * @author Fu zihao
 * @version 1.0
 * @Description:
 * @date 20202020/8/25 10:18
 */
public class EmbedBroker {
    public static void main(String[] args) throws Exception {

        BrokerService brokerService = new BrokerService();
        brokerService.setUseJmx(true);
        brokerService.addConnector("tcp://localhost:61616");
        brokerService.start();


    }
}
