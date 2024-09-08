export default function AdminLayout({
	children,
}: Readonly<{
	children: React.ReactNode;
}>) {
	return (
		<html className="h-screen w-full" lang="en">
			<body className="h-screen w-full">
				<aside className="h-screen w-max"></aside>
				{children}
			</body>
		</html>
	);
}
