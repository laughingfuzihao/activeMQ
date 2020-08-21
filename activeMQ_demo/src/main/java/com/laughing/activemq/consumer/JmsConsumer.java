package com.laughing.activemq.consumer;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author Fu zihao
 * @version 1.0
 * @Description:
 * @date 20202020/8/21 17:42
 */
public class JmsConsumer {
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
        // 5、消费者
        MessageConsumer messageConsumer = session.createConsumer(queue);
        while (true) {
            TextMessage textMessage = (TextMessage) messageConsumer.receive();
            System.out.println("接收MQ:");
            if (null != textMessage) {
                System.out.println(textMessage.getText());
            } else {
                break;
            }
        }
        messageConsumer.close();
        session.close();
        connection.close();

    }
}
