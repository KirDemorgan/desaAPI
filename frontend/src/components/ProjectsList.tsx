'use client';
import { useAppDispatch } from '@/stores/hooks';
import { useEffect } from 'react';
import { getProjects } from '@/stores/projects/projectSlice';
import { useSelector } from 'react-redux';
import { RootState } from '@/stores/stores';

export const ProjectsList = () => {
	const dispatcher = useAppDispatch();
	const projectsList = useSelector(
		(state: RootState) => state.projectSlice.projects,
	);

	useEffect(() => {
		dispatcher(getProjects());
	}, [dispatcher]);

	return (
		<ul>
			{projectsList.map((project) => (
				<li key={project.id}>{project.name}</li>
			))}
		</ul>
	);
};
