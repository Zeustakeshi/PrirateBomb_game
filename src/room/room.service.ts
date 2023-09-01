import { Injectable } from '@nestjs/common';
import { Player, PlayerStates, PlayerTypes, Room } from './types/room.type';
import { GameConstants } from 'src/common/types/game.type';

@Injectable()
export class RoomService {
    private rooms: Map<String, Room>;
    constructor() {
        this.rooms = new Map<string, Room>();
    }

    private generateRoomId(length?: number) {
        length = length === undefined ? 5 : length;
        const minMultiplier = Math.pow(10, length - 1);
        const maxMultiplier = Math.pow(10, length) - 1;
        const randomNumber =
            Math.floor(Math.random() * (maxMultiplier - minMultiplier + 1)) +
            minMultiplier;
        return randomNumber.toString().padStart(length, '0');
    }

    private playerForClient(players: Map<string, Player>) {
        return Array.from(players.values());
    }

    private generateUserType(players: Map<string, Player>) {
        const existedTypes = [];

        players.forEach((player: Player) => {
            existedTypes.push(player.type);
        });

        const types = Object.values(PlayerTypes);
        let type = types[Math.floor(Math.random() * types.length)];
        while (existedTypes.includes(type)) {
            type = types[Math.floor(Math.random() * types.length)];
        }
        return type;
    }

    private getRoom(roomId: string) {
        return this.rooms.get(roomId);
    }

    public getRoomInfo(roomId: string) {
        const room = this.getRoom(roomId);
        if (!room) return;
        return {
            ...room,
            players: this.playerForClient(room.players),
        };
    }

    createRoom(): Room {
        let id = this.generateRoomId();
        while (this.rooms.has(id)) {
            id = this.generateRoomId();
        }

        this.rooms.set(id, {
            id,
            mapId: 1,
            players: new Map(),
            state: 'waiting',
        });
        return this.rooms.get(id);
    }

    joinRoom(roomId: string, playerId: string) {
        const room = this.getRoom(roomId);
        if (!room || room.players.size >= 3 || room.state !== 'waiting') {
            return;
        }

        const newPlayer: Player = {
            id: playerId,
            type: this.generateUserType(room.players),
            position: { x: 0, y: 0 },
            state: PlayerStates.IDLE,
            director: 1,
        };

        room.players.set(playerId, newPlayer);
        this.rooms.set(roomId, room);
        return newPlayer;
    }

    leaveRoom(roomId: string, playerId: string) {
        const room = this.getRoom(roomId);
        if (!room || room.state !== 'waiting') return;
        room.players.delete(playerId);
        this.rooms.set(roomId, room);
        return playerId;
    }

    startRoom(roomId: string) {
        const room = this.getRoom(roomId);
        if (!room || room.players.size < 3 || room.state !== 'waiting') {
            return;
        }

        room.state = 'playing';
        room.players.forEach((player: Player) => {
            room.players.set(player.id, {
                ...player,
                position: {
                    x: Math.random() * (GameConstants.GAME_WIDTH - 50),
                    y: 150,
                },
                director:
                    player.position.x >= GameConstants.GAME_WIDTH * 0.5 ? 0 : 1,
            });
        });

        // random map id here
        room.mapId = 1;

        this.rooms.set(roomId, room);

        return room;
    }

    removeRoom(roomId: string) {
        const room = this.getRoom(roomId);
        if (!room || room.state !== 'waiting') return false;
        this.rooms.delete(roomId);
        return true;
    }

    getPlayer(roomId: string, playerId: string) {
        return this.rooms.get(roomId)?.players?.get(playerId);
    }
}
