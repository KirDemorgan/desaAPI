'use client';
import { usePathname } from 'next/navigation';
import { useSelector } from 'react-redux';
import { RootState } from '@/stores/stores';
import { Project } from '@/stores/projects/projectInterface';

export default function ProjectsSlug() {
	const pathname = usePathname();
	const path = pathname.split('/');

	const name = path[path.length - 1];
	const projects = useSelector(
		(state: RootState) => state.projectSlice.projects,
	);

	const project = projects.find((project: Project) => project.name === name);

	return (
		<ul>
			{project ? (
				<li key={project.id}>{project.name}</li>
			) : (
				<li>Project not found</li>
			)}
		</ul>
	);
}
