import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import { axiosApi } from '@/lib/axiosApi';
import { AxiosResponse } from 'axios';

const api = axiosApi();

interface Task {
	id: number;
	name: string;
	description: string;
	created_at: Date;
}

interface TaskColumn {
	id: number;
	name: string;
	position: number;
	created_at: Date;
	updated_at: Date;
	tasks: Array<Task>;
}

interface Project {
	id: number;
	name: string;
	created_at: Date;
	updated_at: Date;
	taskRows: Array<TaskColumn>;
}

interface ProjectSlice {
	projects: Array<Project>;
	loading: boolean;
}

const initialState: ProjectSlice = {
	projects: [],
	loading: false,
};

export const getProjects = createAsyncThunk('project/getProjects', async () => {
	const res: AxiosResponse<Array<Project>> = await api.get('/projects');
	return res.data;
});

const projectSlice = createSlice({
	name: 'project',
	initialState,
	reducers: {},
	extraReducers: (builder) => {
		builder
			.addCase(getProjects.pending, (state) => {
				state.loading = true;
			})
			.addCase(getProjects.rejected, (state) => {
				state.loading = false;
			})
			.addCase(getProjects.fulfilled, (state, action) => {
				state.loading = false;
				state.projects = action.payload;
			});
	},
});

export default projectSlice.reducer;
