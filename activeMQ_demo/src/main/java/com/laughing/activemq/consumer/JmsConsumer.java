package com.laughing.activemq.consumer;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.component.test.TestComponent;

import javax.jms.*;
import java.io.IOException;

/**
 * @author Fu zihao
 * @version 1.0
 * @Description:
 * @date 20202020/8/21 17:42
 */
public class JmsConsumer {
    public static final String ACTIVEMQ_URL = "tcp://127.0.0.1:61616";
    public static final String QUEUE_NAME = "laughing-queue";

    public static void main(String[] args) throws JMSException, IOException {


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
        // 5、消费者
        // receive 方法
        MessageConsumer messageConsumer = session.createConsumer(queue);
/*        while (true) {
            TextMessage textMessage = (TextMessage) messageConsumer.receive(4000L);
            System.out.println("接收MQ:");
            if (null != textMessage) {
                System.out.println(textMessage.getText());
            } else {
                break;
            }
        }*/
        // 消费者监听 方法

        messageConsumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                if (message != null && message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    try {
                        System.out.println("消费者接收消息--"+textMessage.getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }

                }

            }
        });

        System.in.read();

        messageConsumer.close();
        session.close();
        connection.close();

    }
}
