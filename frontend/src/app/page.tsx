'use client';
import {
	Card,
	CardContent,
	CardDescription,
	CardHeader,
	CardTitle,
} from '@/components/ui/card';
import { ProjectsList } from '@/components/ProjectsList';
import { useCookies } from 'next-client-cookies';
import { useDispatch } from 'react-redux';
import { useEffect } from 'react';
import { setAdmin } from '@/stores/user/userSlice';

export default function Home() {
	const cookies = useCookies();

	const dispatch = useDispatch();

	const token = cookies.get('ACCESS_TOKEN');
	const adminPswd = process.env.NEXT_PUBLIC_ADMIN_SECURE;

	useEffect(() => {
		if (token === adminPswd) {
			dispatch(setAdmin());
		}
	}, [adminPswd, dispatch, token]);
	return (
		<div className="h-screen flex items-center justify-center w-full flex-grow">
			<Card className="w-[350px]">
				<CardHeader>
					<CardTitle>Join project</CardTitle>
					<CardDescription>
						Join your team project to see a list of activities
					</CardDescription>
				</CardHeader>
				<CardContent>
					<ProjectsList />
				</CardContent>
			</Card>
		</div>
	);
}
