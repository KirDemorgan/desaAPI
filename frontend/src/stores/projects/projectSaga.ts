import { call, put, takeEvery } from 'redux-saga/effects';
import { axiosApi } from '@/lib/axiosApi';
import { AxiosError, AxiosResponse } from 'axios';
import {
	getProjects,
	getProjectsSuccess,
	getProjectsFailure,
	setNewProject,
	setNewProjectSuccess,
	setNewProjectFailure,
	getProject,
	getProjectSuccess,
	getProjectFailure,
} from './projectSlice';
import { Project } from '@/stores/projects/projectInterface';

const api = axiosApi();

function* fetchProjects() {
	try {
		const res: AxiosResponse<Array<Project>> = yield call(api.get, '/projects');
		yield put(getProjectsSuccess(res.data));
	} catch (error) {
		const axiosError = error as AxiosError;
		yield put(getProjectsFailure(axiosError.message));
	}
}

function* findProject({ payload }: { payload: { name: string } }) {
	try {
		const res: AxiosResponse<Project> = yield call(api.get, '/projects', {
			params: {
				prefix_name: payload.name,
			},
		});
		yield put(getProjectSuccess(res.data));
	} catch (error) {
		const axiosError = error as AxiosError;
		yield put(getProjectFailure(axiosError.message));
	}
}

function* createProject({ payload }: { payload: { name: string } }) {
	try {
		const res: AxiosResponse<Project> = yield call(
			api.post,
			'/projects',
			{},
			{
				params: {
					project_name: payload.name,
				},
			},
		);
		yield put(setNewProjectSuccess(res.data));
	} catch (error) {
		const axiosError = error as AxiosError;
		yield put(setNewProjectFailure(axiosError.message));
	}
}

function* projectSaga() {
	yield takeEvery(getProject, findProject);
	yield takeEvery(getProjects, fetchProjects);
	yield takeEvery(setNewProject, createProject);
}

export default projectSaga;
