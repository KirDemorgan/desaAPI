import { configureStore } from '@reduxjs/toolkit';
import userSlice from '@/stores/user/userSlice';
import projectSlice from '@/stores/projects/projectSlice';
import createSagaMiddleware from 'redux-saga';
import projectSaga from '@/stores/projects/projectSaga';

const sagaMiddleware = createSagaMiddleware();

export const makeStore = () => {
	const store = configureStore({
		reducer: {
			userSlice,
			projectSlice,
		},
		middleware: (getDefaultMiddleware) =>
			getDefaultMiddleware().concat(sagaMiddleware),
	});

	sagaMiddleware.run(projectSaga);

	return store;
};

// Infer the type of makeStore
export type AppStore = ReturnType<typeof makeStore>;
// Infer the `RootState` and `AppDispatch` types from the store itself
export type RootState = ReturnType<AppStore['getState']>;
export type AppDispatch = AppStore['dispatch'];
