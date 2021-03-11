package workqueue;

import com.rabbitmq.client.*;
import utils.RabbitMQUtils;

import java.io.IOException;

/**
 * 消息消费者2
 *
 * @author Meiko
 * @version 1.0.0
 */
public class Consumer2 {

    public static void main(String[] args) throws IOException {
        // 1.获取连接对象
        Connection connection = RabbitMQUtils.getConnection();
        // 2.获取通道对象
        final Channel channel = connection.createChannel();
        // 3.绑定队列
        channel.queueDeclare("work", true, false, false, null);
        // 设置每次只消费1条消息
        channel.basicQos(1);
        // 4.消费消息
        channel.basicConsume("work", false, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者-2：" + new String(body));
                // 手动确认消息 参数1：手工确认消息标识 参数2：是否确认多条消息
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        });
    }
}
