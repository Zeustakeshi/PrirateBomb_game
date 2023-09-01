import { Injectable } from '@nestjs/common';
import { Socket, Server } from 'socket.io';
import { UserService } from 'src/user/user.service';
import { RoomService } from 'src/room/room.service';
import { PlayerStates } from 'src/room/types/room.type';

@Injectable()
export class SocketService {
    constructor(
        private readonly userService: UserService,
        private readonly roomService: RoomService,
    ) {}

    handleConnection(socket: Socket) {
        console.log(`a user ${socket.id} connection`);
        return this.userService.connection(socket);
    }

    handleCreateGame(socket: Socket) {
        const room = this.roomService.createRoom();
        socket.join(room.id);
        this.roomService.joinRoom(room.id, socket.id);
        return this.getRoom(room.id);
    }

    handleJoinRoom(socket: Socket, roomId: string) {
        const room = this.roomService.joinRoom(roomId, socket.id);
        if (room) socket.join(roomId);
        return room;
    }

    handleLeaveRoom(socket: Socket, roomId: string) {
        const playerId = this.roomService.leaveRoom(roomId, socket.id);
        if (playerId) socket.leave(roomId);
        return playerId;
    }

    handleStartRoom(socket: Socket, roomId: string) {
        return this.roomService.startRoom(roomId);
    }

    handleCancelRoom(socket: Socket, roomId: string) {
        return this.roomService.removeRoom(roomId);
    }

    handleEnterRoom(roomId: string) {
        const room = this.roomService.getRoomInfo(roomId);
        if (!room || room.state !== 'playing') return;
        return room;
    }

    getRoom(roomId: string) {
        return this.roomService.getRoomInfo(roomId);
    }

    getPlayerType(socket: Socket, roomId: string) {
        const player = this.roomService.getPlayer(roomId, socket.id);
        return player?.type;
    }
}
