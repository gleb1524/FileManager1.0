module ru.gb {
    requires javafx.controls;
    requires javafx.fxml;
    requires io.netty.buffer;
    requires io.netty.transport;
    requires io.netty.codec;
    requires java.sql;
    requires sqlite.jdbc;

    opens ru.gb.client to javafx.fxml;
    exports ru.gb.client;
    exports ru.gb.client.network;
    opens ru.gb.client.network to javafx.fxml;
    exports ru.gb.server.dto;
    opens ru.gb.server.dto to javafx.fxml;
}
