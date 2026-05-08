"use client";
import { useState } from "react";
import api from "@/utils/axios";
import { useRouter } from "next/navigation";

export default function SignupPage() {
    const [form, setForm] = useState({
        userId: "",
        userPassword: "",
        userName: "",
        userEmail: ""
    });
    const router = useRouter();

    const handleSignup = async (e) => {
        e.preventDefault();
        try {
            await api.post('/api/auth/signup', form);
            alert("회원가입 성공!");
            router.push('/login');
        } catch (error) {
            alert("회원가입 실패: " + error.response?.data?.message);
        }
    };

    return (
        <div className="max-w-md mx-auto mt-20 p-6 border rounded shadow">
            <h2 className="text-xl font-bold mb-4">회원가입</h2>
            <form onSubmit={handleSignup} className="flex flex-col gap-4">
                <input type="text" placeholder="아이디" className="p-2 border rounded" required
                       onChange={(e) => setForm({...form, userId: e.target.value})} />
                <input type="password" placeholder="비밀번호" className="p-2 border rounded" required
                       onChange={(e) => setForm({...form, userPassword: e.target.value})} />
                <input type="text" placeholder="이름" className="p-2 border rounded" required
                       onChange={(e) => setForm({...form, userName: e.target.value})} />
                <input type="email" placeholder="이메일" className="p-2 border rounded" required
                       onChange={(e) => setForm({...form, userEmail: e.target.value})} />
                <button type="submit" className="bg-green-600 text-white p-2 rounded">가입하기</button>
            </form>
        </div>
    );
}