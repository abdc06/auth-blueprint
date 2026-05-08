"use client";
import { useAuthStore } from "@/store/authStore";
import { useRouter } from "next/navigation";
import { useEffect } from "react";

export default function AdminPage() {
    const { user, isLoggedIn } = useAuthStore();
    const router = useRouter();

    // 권한 체크 로직: authorities 배열에 'ROLE_ADMIN'이 있는지 확인
    const isAdmin = isLoggedIn && user?.authorities?.includes("ROLE_ADMIN");

    useEffect(() => {
        // 컴포넌트 마운트 시 권한이 없으면 튕겨냄
        if (!isAdmin) {
            alert("접근 권한이 없습니다. 관리자만 접근 가능합니다.");
            router.push('/');
        }
    }, [isAdmin, router]);

    // 권한이 없으면 화면에 아무것도 렌더링하지 않음 (깜빡임 방지)
    if (!isAdmin) return null;

    return (
        <main className="flex flex-col items-center min-h-[calc(100vh-65px)] p-8">
            <div className="text-center p-10 border-2 border-red-200 rounded-2xl bg-red-50 shadow-xl">
                <h1 className="text-4xl font-extrabold text-red-600 mb-4">Admin Dashboard</h1>
                <p className="text-lg text-gray-700 font-medium">
                    관리자 전용 페이지에 접속하셨습니다.
                </p>
                <div className="mt-6 p-4 bg-white rounded-lg shadow-inner text-sm text-gray-500">
                    현재 접속 계정: <span className="font-bold text-black">{user?.userId}</span>
                </div>
            </div>
        </main>
    );
}