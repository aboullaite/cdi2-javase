package me.aboullaite;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;

import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.interceptor.Interceptor;

@ApplicationScoped
public class Server {

        private static final int INET_PORT = 8080;
        @Inject
        private transient Logger logger;

        @Inject
        private ChannelHandler channelHandler;

        public void init(@Observes @Priority(Interceptor.Priority.APPLICATION - 100)
                              @Initialized(ApplicationScoped.class) Object init) throws Exception{
            logger.info(init.toString());
            start();
        }

        private void start() throws Exception {
            logger.info("Staring Server on port {}...", INET_PORT);
            EventLoopGroup bossGroup = new NioEventLoopGroup();
            EventLoopGroup workerGroup = new NioEventLoopGroup();
            try {
                ServerBootstrap b = new ServerBootstrap();
                b.childHandler(channelHandler);
                b.group(bossGroup, workerGroup)
                        .channel(NioServerSocketChannel.class)
                        .option(ChannelOption.SO_BACKLOG, 128)
                        .childOption(ChannelOption.SO_KEEPALIVE, true);
                ChannelFuture f = b.bind(INET_PORT).sync();
                f.channel().closeFuture().sync();
            } finally {
                workerGroup.shutdownGracefully();
                bossGroup.shutdownGracefully();
            }
        }
    }

