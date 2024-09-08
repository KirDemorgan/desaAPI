import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { Project, ProjectSlice } from '@/stores/projects/projectInterface';

const initialState: ProjectSlice = {
	projects: [],
	loading: false,
	error: null,
	currentProject: null,
};

const projectSlice = createSlice({
	name: 'project',
	initialState,
	reducers: {
		getProjects(state) {
			state.loading = true;
			state.error = null;
		},
		getProjectsSuccess(state, action: PayloadAction<Array<Project>>) {
			state.loading = false;
			state.projects = action.payload;
		},
		getProjectsFailure(state, action: PayloadAction<string>) {
			state.loading = false;
			state.error = action.payload;
		},
		getProject(state, action: PayloadAction<{ name: string }>) {
			state.loading = true;
			state.error = null;
		},
		getProjectSuccess(state, action: PayloadAction<Project>) {
			state.loading = false;
			state.currentProject = action.payload;
		},
		getProjectFailure(state, action: PayloadAction<string>) {
			state.loading = false;
			state.error = action.payload;
		},
		setNewProject(state, action: PayloadAction<{ name: string }>) {
			state.loading = true;
			state.error = null;
		},
		setNewProjectSuccess(state, action: PayloadAction<Project>) {
			state.loading = false;
			state.projects = [...state.projects, action.payload];
		},
		setNewProjectFailure(state, action: PayloadAction<string>) {
			state.loading = false;
			state.error = action.payload;
		},
	},
});

export const {
	getProjects,
	getProjectsSuccess,
	getProjectsFailure,
	getProject,
	getProjectSuccess,
	getProjectFailure,
	setNewProject,
	setNewProjectSuccess,
	setNewProjectFailure,
} = projectSlice.actions;

export default projectSlice.reducer;
