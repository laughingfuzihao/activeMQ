package com.laughing.activemq.produce;


import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author Fu zihao
 * @version 1.0
 * @Description:
 * @date 2020/8/21 17:05
 */
public class JmsProduce {

    public static final String ACTIVEMQ_URL = "tcp://127.0.0.1:61616";
    public static final String QUEUE_NAME = "laughing-queue";

    public static void main(String[] args) throws JMSException {
        // 1、创建ActiveMQConnectionFactory
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        // 2、创建connection 启动
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        // 3、创建session  createSession(boolean transacted, int acknowledgeMode)
        // transacted 事务 ， acknowledgeMode 签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 4、创建目的地 队列
        // Destination destination = session.createQueue(ACTIVEMQ_URL);
        Queue queue = session.createQueue(ACTIVEMQ_URL);
        // 5、创建消息生产者 放入队列
        MessageProducer messageProducer = session.createProducer(queue);
        // 6、生成消息发送到queue
        for (int i = 0; i < 10; i++) {
            // 7、生成消息
            TextMessage textMessage = session.createTextMessage("laughing-msg " + i);
            // 8、通过生产者messageProducer，发送给MQ
            messageProducer.send(textMessage);

        }
        // 9、关闭
        messageProducer.close();
        session.close();
        connection.close();
        System.out.println("MQ发送成功！");


    }
}
