export enum PlayerStates {
    IDLE = 'IDLE',
    TURN_LEFT = 'TURN_LEFT',
    TURN_RIGHT = 'TURN_RIGHT',
    RUN = 'RUN',
    JUMP = 'JUMP',
    FALL = 'FALL',
    ATTACK = 'ATTACK',
    DIE = 'DIE',
    HIT = 'HIT',
    GROUND = 'GROUND',
}

export enum PlayerTypes {
    BOMBER = 'BOMBER',
    // BALD_PIRATE = 'BALD_PIRATE',
    CUCUMBER = 'CUCUMBER',
    BIG_GUY = 'BIG_GUY',
}

export type Room = {
    id: string;
    players: Map<string, Player>;
    mapId: number;
    state: 'waiting' | 'playing' | 'ending';
};

export type Player = {
    id: string;
    director: number;
    type: PlayerTypes;
    state: PlayerStates;
    position: {
        x: number;
        y: number;
    };
};
