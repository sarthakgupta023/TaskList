import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api/api";
import { useAuth } from "../context/AuthContext";

const Tasks = () => {
    const { logout } = useAuth();
    const navigate = useNavigate();

    const [tasks, setTasks] = useState([]);
    const [loading, setLoading] = useState(true);
    const [form, setForm] = useState({ title: "", content: "" });
    const [submitting, setSubmitting] = useState(false);

    const fetchTasks = async () => {
        setLoading(true);
        try {
            const res = await api.get("/tasks");
            setTasks(res.data);
        } catch {
            
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => { fetchTasks(); }, []);

    const handleCreate = async (e) => {
        e.preventDefault();
        setSubmitting(true);
        try {
            await api.post("/tasks", { ...form, status: "pending" });
            setForm({ title: "", content: "" });
            fetchTasks();
        } finally {
            setSubmitting(false);
        }
    };

    const handleMark = async (task) => {
        await api.put(`/tasks/${task.id}`, { ...task, status: "completed" });
        fetchTasks();
    };

    return (
        <div style={styles.container}>

            
            <div style={styles.navbar}>
                <h2 style={styles.logo}>TaskList</h2>
                <div style={{ display: "flex", gap: "10px", alignItems: "center" }}>
                    <button type="button" style={styles.adminBtn} onClick={() => navigate("/admin")}>
                        Admin
                    </button>
                    <button type="button" style={styles.logoutBtn} onClick={() => { logout(); navigate("/login"); }}>
                        Logout
                    </button>
                </div>
            </div>

            <div style={styles.body}>

                
                <div style={styles.card}>
                    <h3 style={styles.title}>Add Task</h3>
                    <form onSubmit={handleCreate} style={styles.form}>
                        <input
                            style={styles.input}
                            placeholder="Title"
                            value={form.title}
                            onChange={(e) => setForm({ ...form, title: e.target.value })}
                            required
                        />
                        <input
                            style={styles.input}
                            placeholder="Content"
                            value={form.content}
                            onChange={(e) => setForm({ ...form, content: e.target.value })}
                            required
                        />
                        <button style={styles.btn} type="submit" disabled={submitting}>
                            {submitting ? "Adding..." : "Add Task"}
                        </button>
                    </form>
                </div>

                
                <div style={styles.card}>
                    <h3 style={styles.title}>My Tasks</h3>
                    {loading && <p style={styles.muted}>Loading...</p>}
                    {!loading && tasks.length === 0 && <p style={styles.muted}>No tasks yet!</p>}

                    {tasks.map((task) => (
                        <div key={task.id} style={styles.taskRow}>
                            <div>
                                <p style={{
                                    ...styles.taskTitle,
                                    textDecoration: task.status === "completed" ? "line-through" : "none",
                                    color: task.status === "completed" ? "#9ca3af" : "#111827"
                                }}>
                                    {task.title}
                                </p>
                                <p style={{
                                    ...styles.taskContent,
                                    textDecoration: task.status === "completed" ? "line-through" : "none",
                                }}>
                                    {task.content}
                                </p>
                            </div>

                            
                            {task.status !== "completed" && (
                                <button style={styles.markBtn} onClick={() => handleMark(task)}>
                                    ✓
                                </button>
                            )}
                        </div>
                    ))}
                </div>

            </div>
        </div>
    );
};

const styles = {
    container: { minHeight: "100vh", backgroundColor: "#f0f2f5", fontFamily: "sans-serif" },
    navbar: { display: "flex", justifyContent: "space-between", alignItems: "center", padding: "16px 32px", backgroundColor: "#1e1b4b", boxShadow: "0 1px 4px rgba(0,0,0,0.08)" },
    logo: { fontSize: "20px", color: "#fff", margin: 0 },
    adminBtn: { padding: "8px 16px", borderRadius: "8px", border: "1px solid #fff", backgroundColor: "transparent", color: "#fff", cursor: "pointer", fontSize: "14px" },
    logoutBtn: { padding: "8px 16px", borderRadius: "8px", border: "none", backgroundColor: "#ef4444", color: "#fff", cursor: "pointer", fontSize: "14px" },
    body: { maxWidth: "600px", margin: "32px auto", padding: "0 16px", display: "flex", flexDirection: "column", gap: "24px" },
    card: { backgroundColor: "#fff", borderRadius: "12px", padding: "24px", boxShadow: "0 2px 8px rgba(0,0,0,0.07)" },
    title: { fontSize: "17px", marginBottom: "16px", color: "#1e1b4b", margin: "0 0 16px 0" },
    form: { display: "flex", flexDirection: "column", gap: "10px" },
    input: { padding: "10px 14px", borderRadius: "8px", border: "1px solid #ddd", fontSize: "14px", outline: "none" },
    btn: { padding: "10px", borderRadius: "8px", backgroundColor: "#4f46e5", color: "#fff", border: "none", fontSize: "14px", cursor: "pointer" },
    muted: { color: "#9ca3af", fontSize: "14px" },
    taskRow: { display: "flex", justifyContent: "space-between", alignItems: "center", padding: "12px 0", borderBottom: "1px solid #f3f4f6" },
    taskTitle: { fontWeight: "500", fontSize: "15px", margin: "0 0 2px 0" },
    taskContent: { fontSize: "13px", color: "#6b7280", margin: 0 },
    markBtn: { minWidth: "32px", height: "32px", borderRadius: "50%", border: "2px solid #4f46e5", backgroundColor: "transparent", color: "#4f46e5", cursor: "pointer", fontSize: "14px", fontWeight: "bold" },
};

export default Tasks;