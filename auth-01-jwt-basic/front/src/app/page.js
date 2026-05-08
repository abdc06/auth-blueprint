"use client";
import { useAuthStore } from "@/store/authStore";

export default function Home() {
    const { isLoggedIn, user } = useAuthStore();

    return (
        <main className="flex flex-col items-center min-h-[calc(100vh-65px)] p-8">
            <h1 className="text-2xl font-bold mb-6">메인 화면</h1>

            <div className="p-6 border rounded-lg bg-white shadow-md max-w-2xl">
                {isLoggedIn ? (
                    <div className="space-y-4">
                        <h2 className="text-lg font-semibold border-b pb-2">내 프로필 정보</h2>

                        <div className="grid grid-cols-1 gap-3">
                            <p><span className="text-gray-500 w-24 inline-block">아이디:</span>
                                <span className="font-medium">{user?.userId}</span>
                            </p>
                            <p><span className="text-gray-500 w-24 inline-block">이름:</span>
                                <span className="font-medium">{user?.userName}</span>
                            </p>
                            <p><span className="text-gray-500 w-24 inline-block">이메일:</span>
                                <span className="font-medium">{user?.userEmail}</span>
                            </p>
                            <div className="flex items-center">
                                <span className="text-gray-500 w-24 inline-block">내 권한 정보:</span>
                                <div className="flex gap-2">
                                    {user?.authorities && user.authorities.length > 0 ? (
                                        user.authorities.map((auth, index) => (
                                            <span
                                                key={index}
                                                className="px-2 py-1 bg-blue-100 text-blue-700 text-xs font-bold rounded-full"
                                            >
                        {auth}
                      </span>
                                        ))
                                    ) : (
                                        <span className="text-gray-400">권한 없음</span>
                                    )}
                                </div>
                            </div>
                        </div>
                    </div>
                ) : (
                    <div className="text-center py-10">
                        <p className="text-gray-500 mb-4">로그인 후 상세 정보를 확인하실 수 있습니다.</p>
                        <p className="text-sm text-gray-400">현재 비로그인 상태입니다.</p>
                    </div>
                )}
            </div>
        </main>
    );
}