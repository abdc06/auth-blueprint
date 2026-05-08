import { create } from 'zustand';
import { persist } from 'zustand/middleware';

export const useAuthStore = create(persist((set) => ({
    user: null,
    isLoggedIn: false,

    login: (userInfo, token) => {
        localStorage.setItem('accessToken', token.accessToken);
        localStorage.setItem('refreshToken', token.refreshToken);
        set({ user: userInfo, isLoggedIn: true });
    },
    logout: () => {
        localStorage.removeItem('accessToken');
        localStorage.removeItem('refreshToken');
        set({ user: null, isLoggedIn: false });
    },
}),
    {
        name: 'auth-storage', // 로컬 스토리지에 저장될 키 이름
    })
);