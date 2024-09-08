import { CookiesProvider } from 'next-client-cookies/server';

export const NextClientCookieWrapper = ({
	children,
}: Readonly<{
	children: React.ReactNode;
}>) => {
	return <CookiesProvider>{children}</CookiesProvider>;
};
