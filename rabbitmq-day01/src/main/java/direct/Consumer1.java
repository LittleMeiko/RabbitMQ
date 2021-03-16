package direct;

import com.rabbitmq.client.*;
import utils.RabbitMQUtils;

import java.io.IOException;

public class Consumer1 {

    public static void main(String[] args) throws IOException {
        // 1.获取连接对象
        Connection connection = RabbitMQUtils.getConnection();
        // 2.获取连接通道对象
        Channel channel = connection.createChannel();
        // 3.声明指定类型的交换机 参数1：交换机名称 参数2：交换机类型
        String exchangeName = "logs_direct";
        channel.exchangeDeclare(exchangeName, "direct");
        // 4.创建临时队列
        String queue = channel.queueDeclare().getQueue();
        // 5.将临时队列和交换机基于routing key绑定起来
        channel.queueBind(queue, exchangeName, "error");
        // 6.消费消息
        channel.basicConsume(queue,true, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者1：" + new String(body));
            }
        });
    }
}
