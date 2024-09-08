'use client';
import { useAppDispatch } from '@/stores/hooks';
import { useEffect } from 'react';
import { getProjects } from '@/stores/projects/projectSlice';
import { useSelector } from 'react-redux';
import { RootState } from '@/stores/stores';
import { AddNewProject } from '@/components/admin/AddNewProject';
import { useRouter } from 'next/navigation';

export const ProjectsList = () => {
	const dispatcher = useAppDispatch();
	const router = useRouter();
	const projectsList = useSelector(
		(state: RootState) => state.projectSlice.projects,
	);
	const isAdmin = useSelector((state: RootState) => state.userSlice.isAdmin);

	useEffect(() => {
		dispatcher(getProjects());
	}, [dispatcher]);

	if (projectsList.length <= 0) {
		if (isAdmin) {
			return (
				<>
					<AddNewProject />
				</>
			);
		}
		return <>Wait until leader create your project</>;
	}

	return (
		<>
			<ul className="flex flex-col gap-1.5">
				{projectsList.map((project) => (
					<li
						key={project.id}
						className="cursor-pointer"
						onClick={() =>
							router.push(
								isAdmin
									? `/admin/projects/${project.name}`
									: `/projects/${project.name}`,
							)
						}
					>
						{project.name}
					</li>
				))}
			</ul>
		</>
	);
};
