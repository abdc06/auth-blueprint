"use client";
import Link from "next/link";
import { useAuthStore } from "@/store/authStore";

export default function Header() {
    const { isLoggedIn, user, logout } = useAuthStore();

    const isAdmin = isLoggedIn && user?.authorities?.includes("ROLE_ADMIN");

    return (
        <header className="flex justify-between items-center p-4 bg-gray-100 border-b">
            <Link href="/" className="font-bold text-xl">auth-01-jwt-basic-front</Link>
            <nav className="flex gap-4 items-center">
                {isLoggedIn ? (
                    <>
                        {isAdmin && (
                            <Link href="/admin" className="text-blue-600 hover:underline">관리자화면</Link>
                        )}
                        <span className="font-medium">{user?.userName}님</span>
                        <button onClick={logout} className="text-sm bg-gray-200 px-3 py-1 rounded">로그아웃</button>
                    </>
                ) : (
                    <>
                        <Link href="/login" className="hover:text-blue-500">로그인</Link>
                        <Link href="/signup" className="hover:text-blue-500">회원가입</Link>
                    </>
                )}
            </nav>
        </header>
    );
}