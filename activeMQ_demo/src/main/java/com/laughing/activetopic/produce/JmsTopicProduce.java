package com.laughing.activetopic.produce;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author Fu zihao
 * @version 1.0
 * @Description: 持久化
 * @date 20202020/8/24 10:03
 */
public class JmsTopicProduce {

    public static final String ACTIVEMQ_URL = "tcp://127.0.0.1:61616";
    public static final String TOPIC_NAME = "laughing-topic";

    public static void main(String[] args) throws JMSException {
        // 1、创建ActiveMQConnectionFactory
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        // 2、创建connection 启动
        Connection connection = activeMQConnectionFactory.createConnection();

        // 3、创建session  createSession(boolean transacted, int acknowledgeMode)
        // transacted 事务 ， acknowledgeMode 签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 4、创建目的地 主题
        Topic topic = session.createTopic(TOPIC_NAME);
        // 5、创建消息生产者 放入队列
        MessageProducer messageProducer = session.createProducer(topic);
        messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);

        connection.start();

        // 6、生成消息发送到topic
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
        System.out.println("MQ-TOPIC发送成功！");


    }
}
