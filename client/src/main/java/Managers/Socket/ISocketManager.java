package Managers.Socket;

import io.socket.client.Socket;

public interface ISocketManager {
    Socket getSocket ();

    void connect ();
    void disconnect();
    void listenError ();
}
