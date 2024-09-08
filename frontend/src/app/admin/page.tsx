'use client';

import { useEffect, useState } from 'react';
import { useCookies } from 'next-client-cookies';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from '@/stores/stores';
import { setAdmin } from '@/stores/user/userSlice';
import {
	Card,
	CardContent,
	CardDescription,
	CardHeader,
	CardTitle,
} from '@/components/ui/card';
import { Input } from '@/components/ui/input';

export default function AdminPage() {
	const cookies = useCookies();
	const dispatch = useDispatch();
	const isAdmin = useSelector((state: RootState) => state.userSlice.isAdmin);

	const [password, setPassword] = useState<string>('');

	const adminPswd = process.env.NEXT_PUBLIC_ADMIN_SECURE;

	useEffect(() => {
		if (!adminPswd || adminPswd === 'undefined') {
			alert('YOU FORGOT TOKEN');
		}
	}, [adminPswd]);

	useEffect(() => {
		if (password === adminPswd) {
			dispatch(setAdmin());
			const today = new Date();
			const priorDate = new Date(new Date().setDate(today.getDate() + 30));
			cookies.set('ACCESS_TOKEN', adminPswd, { expires: priorDate });
		}
	}, [password, adminPswd, dispatch, cookies]);

	return (
		<main className="h-screen">
			{isAdmin ? (
				<h1>authed</h1>
			) : (
				<div className="h-screen flex items-center justify-center flex-grow w-full">
					<Card className="w-[350px]">
						<CardHeader>
							<CardTitle>Enter admin page</CardTitle>
							<CardDescription>
								You must input your admin password down below
							</CardDescription>
						</CardHeader>
						<CardContent>
							<Input
								placeholder="NEXT_PUBLIC_ADMIN_SECURE"
								onChange={(e) => setPassword(e.target.value)}
							/>
						</CardContent>
					</Card>
				</div>
			)}
		</main>
	);
}
