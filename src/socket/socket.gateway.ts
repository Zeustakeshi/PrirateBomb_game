import { OnModuleInit } from '@nestjs/common';
import {
    ConnectedSocket,
    MessageBody,
    OnGatewayConnection,
    OnGatewayDisconnect,
    SubscribeMessage,
    WebSocketGateway,
    WebSocketServer,
} from '@nestjs/websockets';
import { Server, Socket } from 'socket.io';
import { SocketService } from './socket.service';
import { PlayerTypes } from 'src/room/types/room.type';

@WebSocketGateway({
    cors: {
        origin: '*',
        credentials: true,
    },
    transports: ['websocket'],
})
export class SocketGateway
    implements OnModuleInit, OnGatewayConnection, OnGatewayDisconnect
{
    @WebSocketServer()
    server: Server = new Server();

    constructor(private readonly socketService: SocketService) {}

    onModuleInit() {}

    async handleConnection(socket: Socket, ...args: any[]) {
        this.socketService.handleConnection(socket);
    }

    @SubscribeMessage('new-game')
    async handleCreateNewRoom(@ConnectedSocket() socket: Socket) {
        const newGame = this.socketService.handleCreateGame(socket);
        if (!newGame) {
            socket.emit('error', "Can't create room");
            return;
        }
        socket.emit('new-game', newGame);
    }

    @SubscribeMessage('join-room')
    async handleJoinRoom(
        @ConnectedSocket() socket: Socket,
        @MessageBody() roomId: string,
    ) {
        const newPlayer = this.socketService.handleJoinRoom(socket, roomId);
        if (!newPlayer) {
            socket.emit('error', "Can't join room");
            return;
        }
        this.server
            .except(socket.id)
            .to(roomId)
            .emit('new user join room', newPlayer);
        socket.emit('join-room', this.socketService.getRoom(roomId));
    }

    @SubscribeMessage('leave-room')
    async handleLeaveRoom(
        @ConnectedSocket() socket: Socket,
        @MessageBody() roomId: string,
    ) {
        const room = this.socketService.handleLeaveRoom(socket, roomId);
        if (!room) {
            socket.emit('error', "can't leave room");
            return;
        }
        socket.to(roomId).emit('user leave room', room);
    }

    @SubscribeMessage('cancel-room')
    handleCancelRoom(
        @ConnectedSocket() socket: Socket,
        @MessageBody() roomId: string,
    ) {
        const ok = this.socketService.handleCancelRoom(socket, roomId);
        if (!ok) return;
        socket.to(roomId).emit('cancel-room', true);
        socket.in(roomId).socketsLeave(roomId);
    }

    @SubscribeMessage('start-room')
    handleStartRoom(
        @ConnectedSocket() socket: Socket,
        @MessageBody() roomId: string,
    ) {
        const room = this.socketService.handleStartRoom(socket, roomId);
        if (!room) {
            socket.to(roomId).emit('error', "cant't start room");
            return;
        }
        socket.to(roomId).emit('start-room', room);
    }

    @SubscribeMessage('enter-room')
    handleEnterRoom(
        @ConnectedSocket() socket: Socket,
        @MessageBody() roomId: string,
    ) {
        const room = this.socketService.handleEnterRoom(roomId);
        if (!room) {
            socket.to(roomId).emit('error', "cant't get room");
            return;
        }
        socket.to(roomId).emit('room-info', room);
    }

    @SubscribeMessage('update-state')
    handleUpdateState(
        @ConnectedSocket() socket: Socket,
        @MessageBody() data: string,
    ) {
        const { roomId, playerId, state, payload } = JSON.parse(data);
        const { x, y, director } = payload;
        const room = this.socketService.handleEnterRoom(roomId);
        if (!room) return;
        socket.to(roomId).except(socket.id).emit('update-state', {
            playerId: playerId,
            state: state,
            payload: {
                x,
                y,
                director,
            },
        });
    }

    @SubscribeMessage('create-bomb')
    handleCreateBomb(
        @ConnectedSocket() socket: Socket,
        @MessageBody() data: string,
    ) {
        const { roomId, x, y } = JSON.parse(data);
        if (
            this.socketService.getPlayerType(socket, roomId) !==
            PlayerTypes.BOMBER
        ) {
            socket.emit("can't create bomb with this player type");
            return;
        }
        socket.to(roomId).emit('create-bomb', { x, y });
    }

    async handleDisconnect(client: Socket) {
        console.log('a user diconnected');
    }
}
