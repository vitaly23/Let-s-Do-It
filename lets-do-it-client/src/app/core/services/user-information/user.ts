import { UserRole } from './user-role';
import { UserId } from './user-id';

export interface User{
    userId: UserId;
    userName: string;
    avatar: string;
    role: UserRole
}