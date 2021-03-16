package fanout;

import com.rabbitmq.client.*;
import utils.RabbitMQUtils;

import java.io.IOException;

public class Consumer1 {

    public static void main(String[] args) throws IOException {
        // 1.获取连接对象
        Connection connection = RabbitMQUtils.getConnection();
        // 2.获取通道对象
        Channel channel = connection.createChannel();
        // 3.通道绑定到交换机
        channel.exchangeDeclare("logs", "fanout");
        // 4.创建临时队列，并在通道中将队列和交换机绑定到一起
        String queueName = channel.queueDeclare().getQueue();
        // 参数1：队列名称 参数2：交换机名称 参数3：路由名称
        channel.queueBind(queueName, "logs", "");
        // 5.消费消息
        channel.basicConsume(queueName, true, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者-1:" + new String(body));
            }
        });
    }
}
