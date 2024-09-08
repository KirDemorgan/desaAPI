import { useDispatch } from 'react-redux';
import { AppDispatch } from '@/stores/stores';

export const useAppDispatch = () => useDispatch<AppDispatch>();
