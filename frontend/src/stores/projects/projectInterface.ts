export interface Task {
	id: number;
	name: string;
	description: string;
	created_at: Date;
}

export interface TaskColumn {
	id: number;
	name: string;
	position: number;
	created_at: Date;
	updated_at: Date;
	tasks: Array<Task>;
}

export interface Project {
	id: number;
	name: string;
	created_at: Date;
	updated_at: Date;
	taskRows: Array<TaskColumn>;
}

export interface ProjectSlice {
	projects: Array<Project>;
	loading: boolean;
	error: string | null;
	currentProject: Project | null;
}
