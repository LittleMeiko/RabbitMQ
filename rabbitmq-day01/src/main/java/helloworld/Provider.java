package helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Provider {

    // 生产消息
    @Test
    public void testSendMessage() throws IOException, TimeoutException {
        // 1.创建连接rabbitmq的的连接工厂对象
        ConnectionFactory connectionFactory = new ConnectionFactory();
        // 2.设置连接rabbitmq的主机
        connectionFactory.setHost("192.168.137.7");
        // 3.设置连接rabbitmq的主机端口
        connectionFactory.setPort(5672);
        // 4.设置要连接的那个虚拟主机
        connectionFactory.setVirtualHost("/ems");
        // 5.设置访问虚拟主机的用户名和密码
        connectionFactory.setUsername("ems");
        connectionFactory.setPassword("123");

        // 6.获取连接对象
        Connection connection = connectionFactory.newConnection();
        // 7.获取连接中的通道对象
        Channel channel = connection.createChannel();
        /**
         * 8.通道绑定对应消息队列
         * 参数1：队列名称，如果队列不存在则自动创建
         * 参数2：用来定义队列特性是否需要持久化，true 持久化队列，false 不持久化
         * 参数3：exclusive 是否独占队列 true独占队列，false不独占
         * 参数4：autoDelete 是否在消息完成后自动删除队列，true自动删除，false不自动删除
         * 参数5：额外附加参数
         */
        channel.queueDeclare("hello", false, false, false, null);
        /**
         * 9.发布消息
         *  参数1：交换机名称
         *  参数2：队列名称
         *  参数3：传递消息额外设置参数
         *  参数4：消息的具体内容
         */
        channel.basicPublish("", "hello", null, "hello rabbitmq".getBytes());

        // 10.关闭通道和连接
        channel.close();
        connection.close();
    }
}
