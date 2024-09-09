import create from 'zustand';

interface UserState {
	isAdmin: boolean;
	startProject: string | null;
	setAdmin: () => void;
	setStartProject: (project: string) => void;
}

const useUserStore = create<UserState>((set) => ({
	isAdmin: false,
	startProject: null,
	setAdmin: () => set({ isAdmin: true }),
	setStartProject: (project) => set({ startProject: project }),
}));

export default useUserStore;
