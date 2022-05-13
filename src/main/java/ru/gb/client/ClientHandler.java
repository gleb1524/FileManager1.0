package ru.gb.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import ru.gb.server.dto.BasicResponse;
import ru.gb.server.dto.GetFileListRequest;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    ClientController clientController = (ClientController) ControllerRegistry
            .getControllerObject(ClientController.class);
    RegController regController = (RegController) ControllerRegistry
            .getControllerObject(RegController.class);
    WorkController workController = (WorkController) ControllerRegistry
            .getControllerObject(WorkController.class);


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        BasicResponse response = (BasicResponse) msg;
        System.out.println(response.getResponse());
        if("auth_ok".equals(response.getResponse())){
            System.out.println(clientController.password.getText());
            clientController.workPlace(false);
        }
        if("auth_no".equals(response.getResponse())){
            clientController.workPlace(true);
            clientController.exp.setText("Неверный!!11!!!11!!");
            clientController.exp.setVisible(true);
        }if("reg_ok".equals(response.getResponse())){
            System.out.println(regController.name.getText());
        }
        if("reg_no".equals(response.getResponse())){
           RegController.setIsRegistration("reg_no");
           RegController.test.setVisible(true);
           RegController.test.setText("fvdf");
        }
        if("file list...".equals(response.getResponse())){
            return;
        }
        ctx.writeAndFlush(new GetFileListRequest());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();

    }
}
