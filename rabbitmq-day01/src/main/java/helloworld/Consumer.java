package helloworld;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {

    public static void main(String[] args) throws IOException, TimeoutException {
        // 创建连接工厂对象
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.137.7");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/ems");
        connectionFactory.setUsername("ems");
        connectionFactory.setPassword("123");

        // 获取连接对象
        Connection connection = connectionFactory.newConnection();
        // 获取渠道绑定对应的队列
        Channel channel = connection.createChannel();
        channel.queueDeclare("hello", false, false, false, null);
        /**
         * 消费消息
         * 参数1：消费哪个队列的消息，即队列名称
         * 参数2：开启消息的自动确认机制
         * 参数3：消费消息时的回调接口
         */
        channel.basicConsume("hello", true, new DefaultConsumer(channel){
            // body:消息的具体内容
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("==============================" + new String(body));
            }
        });

        //channel.close();
        //connection.close();
    }
}
