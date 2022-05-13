package ru.gb.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.StandardCharsets;



public class ByteToStringEncoder extends MessageToByteEncoder<String> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, String s, ByteBuf out) throws Exception {
        byte[] outDataBytes = s.getBytes(StandardCharsets.UTF_8);
        out.writeBytes(outDataBytes);
    }
}
