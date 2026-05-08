"use client";
import { useState } from "react";
import api from "@/utils/axios";
import { useAuthStore } from "@/store/authStore";
import { useRouter } from "next/navigation";

export default function LoginPage() {
    const [form, setForm] = useState({ userId: "", userPassword: "" });
    const login = useAuthStore((state) => state.login);
    const router = useRouter();

    const handleLogin = async (e) => {
        e.preventDefault();
        try {
            const response = await api.post('/api/auth/token', form);
            const { userInfo, token } = response.data;
            login(userInfo, token);
            router.push('/');
        } catch (error) {
            alert("로그인 실패: " + error.response?.data?.message);
        }
    };

    return (
        <div className="max-w-md mx-auto mt-20 p-6 border rounded shadow">
            <h2 className="text-xl font-bold mb-4">로그인</h2>
            <form onSubmit={handleLogin} className="flex flex-col gap-4">
                <input type="text" placeholder="아이디" className="p-2 border rounded"
                       onChange={(e) => setForm({...form, userId: e.target.value})} />
                <input type="password" placeholder="비밀번호" className="p-2 border rounded"
                       onChange={(e) => setForm({...form, userPassword: e.target.value})} />
                <button type="submit" className="bg-blue-600 text-white p-2 rounded">로그인</button>
            </form>
        </div>
    );
}