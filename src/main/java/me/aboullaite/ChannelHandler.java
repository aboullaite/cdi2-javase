package me.aboullaite;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.control.ActivateRequestContext;
import javax.inject.Inject;
import java.nio.charset.Charset;

@ApplicationScoped
public class ChannelHandler extends ChannelInboundHandlerAdapter {
    @Inject
    private TimeProvider timeProvider;

    @Inject
    private Logger logger;

    @Override
    @ActivateRequestContext
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf in = (ByteBuf) msg;
        try {
            logger.info("{} New message received: {}", timeProvider.getCurrentTime(), in.toString(Charset.defaultCharset()));
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }
}
