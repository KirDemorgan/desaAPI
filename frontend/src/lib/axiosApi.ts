import axios from 'axios';

const URL = process.env.NEXT_PUBLIC_API_URL;

if (!URL) {
	throw new Error('YOU FORGOT SET API URL');
}

export const axiosApi = () => {
	return axios.create({
		baseURL: URL,
	});
};
