import { Module } from '@nestjs/common';
import { UserModule } from 'src/user/User.module';
import { SocketGateway } from './socket.gateway';
import { SocketService } from './socket.service';
import { RoomModule } from 'src/room/room.module';

@Module({
    imports: [UserModule, RoomModule],
    providers: [SocketService, SocketGateway],
})
export class SocketModule {}
