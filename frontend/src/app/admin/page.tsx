'use client';

import { useEffect, useState } from 'react';
import { useCookies } from 'next-client-cookies';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from '@/stores/stores';
import { setAdmin } from '@/stores/user/userSlice';

export default function AdminPage() {
	const cookies = useCookies();

	const isAdmin = useSelector((state: RootState) => state.userSlice.isAdmin);
	const dispatch = useDispatch();

	const token = cookies.get('ACCESS_TOKEN');
	const [password, setPassword] = useState<string>('');

	const adminPswd = process.env.NEXT_PUBLIC_ADMIN_SECURE;

	useEffect(() => {
		if (!adminPswd || adminPswd === 'undefined') {
			alert('YOU FORGOT TOKEN');
		} else {
			if (token === adminPswd) {
				dispatch(setAdmin());
			}
		}
	}, [token, adminPswd]);

	useEffect(() => {
		if (password === adminPswd) {
			dispatch(setAdmin());
			const today = new Date();
			const priorDate = new Date(new Date().setDate(today.getDate() + 30));
			cookies.set('ACCESS_TOKEN', adminPswd, { expires: priorDate });
		}
	}, [password, adminPswd]);

	return (
		<>
			{isAdmin ? (
				<h1>authed</h1>
			) : (
				<input
					type="text"
					value={password}
					onChange={(e) => setPassword(e.target.value)}
				/>
			)}
		</>
	);
}
