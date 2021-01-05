import { UserRole } from './user-role';

export interface User{
    email: string;
    userName: string;
    avatar: string;
    role: UserRole
}