package com.laughing.activemq.produce;


import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.Map;

/**
 * @author Fu zihao
 * @version 1.0
 * @Description:
 * @date 2020/8/21 17:05
 */
public class JmsProduce {

    // public static final String ACTIVEMQ_URL = "tcp://127.0.0.1:61616";
    public static final String ACTIVEMQ_URL = "nio://127.0.0.1:61618";
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
        Queue queue = session.createQueue(QUEUE_NAME);
        // 5、创建消息生产者 放入队列
        MessageProducer messageProducer = session.createProducer(queue);
        // 持久化
/*        messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);*/
        // 6、生成消息发送到queue
        for (int i = 0; i < 10; i++) {
            // 7、生成消息
            TextMessage textMessage = session.createTextMessage("laughing-msg " + i);
            // 消息头
/*            textMessage.setJMSDestination(queue);
            textMessage.setJMSDeliveryMode(DeliveryMode.PERSISTENT);
            textMessage.setJMSDeliveryMode(DeliveryMode.NON_PERSISTENT);
            textMessage.setJMSExpiration(6000);
            textMessage.setJMSPriority(0);
            textMessage.setJMSMessageID("xxxxxxx");*/
            // 消息体
/*            session.createTextMessage();
            MapMessage mapMessage = session.createMapMessage();
            mapMessage.setString("K1","V1");
            session.createBytesMessage();
            session.createStreamMessage();
            session.createObjectMessage();*/

            textMessage.setStringProperty("PropertyName","PropertyValue");


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
