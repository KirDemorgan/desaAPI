# Entities:
- Project
- - id (long)
- - name (string)
- - created_at (time)
- - updated_at (time)
- - tasksRows (TaskRow[])


- TaskColumns
- - id (long)
- - name (string)
- - position (int)
- - created_at (time)
- - updated_at (time)
- - tasks (Task[])


- Task
- - id (long)
- - name (string)
- - description (string)
- - created_at (time)

# Api funcs(for each):
- get
- create
- update
- delete