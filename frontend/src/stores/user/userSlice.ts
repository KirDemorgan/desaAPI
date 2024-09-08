import { createSlice } from '@reduxjs/toolkit';
import type { PayloadAction } from '@reduxjs/toolkit';

interface UserState {
	isAdmin: boolean;
	startProject: string | null;
}

const initialState: UserState = { isAdmin: false, startProject: null };

const userSlice = createSlice({
	name: 'user',
	initialState,
	reducers: {
		setAdmin(state) {
			state.isAdmin = true;
		},
		setStartProject(state, action: PayloadAction<string>) {
			state.startProject = action.payload;
		},
	},
});

export const { setAdmin, setStartProject } = userSlice.actions;
export default userSlice.reducer;
