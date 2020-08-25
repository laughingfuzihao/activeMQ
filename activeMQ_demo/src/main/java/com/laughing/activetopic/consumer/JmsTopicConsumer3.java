package com.laughing.activetopic.consumer;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * @author Fu zihao
 * @version 1.0
 * @Description: topic持久化
 * @date 20202020/8/24 10:03
 */
public class JmsTopicConsumer3 {
    public static final String ACTIVEMQ_URL = "tcp://127.0.0.1:61616";
    public static final String TOPIC_NAME = "laughing-topic";

    public static void main(String[] args) throws JMSException, IOException {


        // 1、创建ActiveMQConnectionFactory
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        // 2、创建connection 启动
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.setClientID("laughing");

        // 3、创建session  createSession(boolean transacted, int acknowledgeMode)
        // transacted 事务 ， acknowledgeMode 签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 4、创建目的地 队列
        // Destination destination = session.createQueue(ACTIVEMQ_URL);
        Topic topic = session.createTopic(TOPIC_NAME);
        // 5、消费者

        // 订阅者
        TopicSubscriber topicSubscriber = session.createDurableSubscriber(topic, "remark...");
        connection.start();

        Message message = topicSubscriber.receive();
        while (message != null) {
            TextMessage textMessage = (TextMessage) message;
            System.out.println("持久化topic:" + textMessage.getText());
            message = topicSubscriber.receive(1000L);

        }

        session.close();
        connection.close();

    }
}
