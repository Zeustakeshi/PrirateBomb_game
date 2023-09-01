package Managers.Socket;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import java.net.URI;

public class SocketManager implements  ISocketManager {
    private final Socket socket;
    public SocketManager(URI uri,  IO.Options options) {
        this.socket = IO.socket(uri, options);

        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("Connected to server");
            }
        });


    }

    @Override
    public void connect() {
        this.socket.connect();
    }

    @Override
    public void disconnect() {
        this.socket.disconnect();
    }

    @Override
    public void listenError() {
        this.socket.on("error", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println(args[0]);
            }
        });
    }

    @Override
    public Socket getSocket() {
        return this.socket;
    }

}
