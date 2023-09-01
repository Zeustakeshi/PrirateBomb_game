import { Injectable } from '@nestjs/common';
import { User } from './types/user.type';
import { Socket } from 'socket.io';
import * as uuid from 'uuid';

@Injectable()
export class UserService {
    private users: Map<string, User>;
    constructor() {
        this.users = new Map<string, User>();
    }

    connection(socket: Socket): User {
        const newUser: User = {
            id: uuid.v4(),
            name: 'user',
        };
        this.users.set(socket.id, newUser);
        return newUser;
    }
}
