package ru.gb.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import ru.gb.server.dto.AuthRequest;
import ru.gb.server.dto.*;

public class BasicHandler extends ChannelInboundHandlerAdapter {
    private String addrr;
    DataBaseService dataBaseService = new DataBaseService();
//    private static final Map<Class<? extends BasicRequest>, Consumer<ChannelHandlerContext>> REQUEST_HANDLERS = new HashMap<>();

//    static {
//        REQUEST_HANDLERS.put(AuthRequest.class, channelHandlerContext -> {
//
//             channelHandlerContext.writeAndFlush(new BasicResponse("login ok"));
//        });
//        REQUEST_HANDLERS.put(GetFileListRequest.class, channelHandlerContext -> {
//            channelHandlerContext.writeAndFlush(new BasicResponse("file list..."));
//        });
//
//    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        addrr = ctx.channel().remoteAddress().toString();
        System.out.println(addrr);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof UploadRequest){
            UploadRequest request = (UploadRequest) msg;
            String serverFileName = new String(request.getRemPath()+"\\"+request.getName());
            ctx.writeAndFlush(new BasicResponse(addrr));
            System.out.println(addrr);

        }else if(msg instanceof RegRequest){
            if(!dataBaseService.hasRegistration(((RegRequest) msg).getLogin())){
            dataBaseService.creatRegistration(((RegRequest) msg).getLogin(), ((RegRequest) msg).getPassword(),
                    ((RegRequest) msg).getName(), ((RegRequest) msg).getSurname());
            ctx.writeAndFlush(new BasicResponse("reg_ok"));
            }else{
                ctx.writeAndFlush(new BasicResponse("reg_no"));
            }
        }else if(msg instanceof AuthRequest) {
            if(dataBaseService.hasAuth(((AuthRequest) msg).getLogin(), ((AuthRequest) msg).getPassword())){
                ctx.writeAndFlush(new BasicResponse("auth_ok"));
            }else{
                ctx.writeAndFlush(new BasicResponse("auth_no"));
            }
        }else{
//        BasicRequest request = (BasicRequest) msg;
//        Consumer<ChannelHandlerContext> channelHandlerContextConsumer = REQUEST_HANDLERS.get(request.getClass());
//        channelHandlerContextConsumer.accept(ctx);
        }
    }



    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
