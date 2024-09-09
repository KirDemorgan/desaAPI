'use client';

import { useEffect } from 'react';
import useProjectStore from '@/stores/projects/useProjectStore';
import useUserStore from '@/stores/user/useUserStore';
import { AddNewProject } from '@/components/admin/AddNewProject';
import { useRouter } from 'next/navigation';
import {Project} from "@/stores/projects/projectInterface";

export const ProjectsList = () => {
	const { getProjects, projects } = useProjectStore();
	const { isAdmin } = useUserStore();
	const router = useRouter();

	useEffect(() => {
		getProjects();
	}, [getProjects]);

	if (projects.length <= 0) {
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
				{projects.map((project: Project) => (
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
