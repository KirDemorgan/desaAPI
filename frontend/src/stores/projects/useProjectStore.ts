import create from 'zustand';
import { axiosApi } from '@/lib/axiosApi';
import { AxiosError, AxiosResponse } from 'axios';
import { Project } from '@/stores/projects/projectInterface';

const api = axiosApi();



const useProjectStore = create<ProjectSlice>((set) => ({
	projects: [],
	loading: false,
	error: null,
	currentProject: null,
	getProjects: async () => {
		set({ loading: true, error: null });
		try {
			const res: AxiosResponse<Array<Project>> = await api.get('/projects');
			set({ projects: res.data, loading: false });
		} catch (error) {
			const axiosError = error as AxiosError;
			set({ error: axiosError.message, loading: false });
		}
	},
	getProject: async (name: string) => {
		set({ loading: true, error: null });
		try {
			const res: AxiosResponse<Project> = await api.get('/projects', {
				params: {
					prefix_name: name,
				},
			});
			set({ currentProject: res.data, loading: false });
		} catch (error) {
			const axiosError = error as AxiosError;
			set({ error: axiosError.message, loading: false });
		}
	},
	setNewProject: async (name: string) => {
		set({ loading: true, error: null });
		try {
			const res: AxiosResponse<Project> = await api.post(
				'/projects',
				{},
				{
					params: {
						project_name: name,
					},
				},
			);
			set((state) => ({
				projects: [...state.projects, res.data],
				loading: false,
			}));
		} catch (error) {
			const axiosError = error as AxiosError;
			set({ error: axiosError.message, loading: false });
		}
	},
}));

export default useProjectStore;
