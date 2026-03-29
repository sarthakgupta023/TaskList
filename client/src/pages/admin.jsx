import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api/api";
import { useAuth } from "../context/AuthContext";

const Admin = () => {
    const { logout } = useAuth();
    const navigate = useNavigate();

    const [users, setUsers] = useState([]);
    const [tasks, setTasks] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const res = await api.get("/admin");
                setUsers(res.data.users);
                setTasks(res.data.tasks);
            } catch {
                alert("Failed to load data");
            } finally {
                setLoading(false);
            }
        };
        fetchData();
    }, []);

    return (
        <div style={styles.container}>
            <div style={styles.navbar}>
                <h2 style={styles.logo}>Admin</h2>
                <div style={{ display: "flex", gap: "10px" }}>
                    <button style={styles.navBtn} type="button" onClick={() => navigate("/tasks")}>
                        Tasks
                    </button>
                    <button style={styles.logoutBtn} type="button" onClick={() => { logout(); navigate("/login"); }}>
                        Logout
                    </button>
                </div>
            </div>

            <div style={styles.body}>
                {loading ? <p>Loading...</p> : (
                    <>
                        <div style={styles.card}>
                            <h3 style={styles.title}>All users ({users.length})</h3>
                            <table style={styles.table}>
                                <thead>
                                    <tr>
                                        <th style={styles.th}>Name</th>
                                        <th style={styles.th}>Email</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {users.map((u) => (
                                        <tr key={u.id}>
                                            <td style={styles.td}>{u.name}</td>
                                            <td style={styles.td}>{u.email}</td>
                                        </tr>
                                    ))}
                                </tbody>
                            </table>
                        </div>

                        <div style={styles.card}>
                            <h3 style={styles.title}>All tasks ({tasks.length})</h3>
                            <table style={styles.table}>
                                <thead>
                                    <tr>
                                        <th style={styles.th}>Title</th>
                                        <th style={styles.th}>Content</th>
                                        <th style={styles.th}>Status</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {tasks.map((t) => (
                                        <tr key={t.id}>
                                            <td style={styles.td}>{t.title}</td>
                                            <td style={styles.td}>{t.content}</td>
                                            <td style={styles.td}>
                                                <span style={{
                                                    ...styles.badge,
                                                    backgroundColor: t.status === "completed" ? "#dcfce7" : "#fef9c3",
                                                    color: t.status === "completed" ? "#166534" : "#854d0e"
                                                }}>
                                                    {t.status}
                                                </span>
                                            </td>
                                        </tr>
                                    ))}
                                </tbody>
                            </table>
                        </div>
                    </>
                )}
            </div>
        </div>
    );
};

const styles = {
    container: { minHeight: "100vh", backgroundColor: "#f0f2f5", fontFamily: "sans-serif" },
    navbar: { display: "flex", justifyContent: "space-between", alignItems: "center", padding: "16px 32px", backgroundColor: "#1e1b4b", boxShadow: "0 1px 4px rgba(0,0,0,0.08)" },
    logo: { fontSize: "20px", color: "#fff", margin: 0 },
    navBtn: { padding: "8px 16px", borderRadius: "8px", border: "1px solid #fff", backgroundColor: "transparent", color: "#fff", cursor: "pointer", fontSize: "14px" },
    logoutBtn: { padding: "8px 16px", borderRadius: "8px", border: "none", backgroundColor: "#ef4444", color: "#fff", cursor: "pointer", fontSize: "14px" },
    body: { maxWidth: "860px", margin: "32px auto", padding: "0 16px", display: "flex", flexDirection: "column", gap: "24px" },
    card: { backgroundColor: "#fff", borderRadius: "12px", padding: "24px", boxShadow: "0 2px 8px rgba(0,0,0,0.07)" },
    title: { fontSize: "17px", margin: "0 0 16px 0", color: "#1e1b4b" },
    table: { width: "100%", borderCollapse: "collapse" },
    th: { textAlign: "left", padding: "10px 12px", fontSize: "13px", color: "#6b7280", borderBottom: "1px solid #e5e7eb" },
    td: { padding: "10px 12px", fontSize: "14px", color: "#111827", borderBottom: "1px solid #f3f4f6" },
    badge: { padding: "2px 10px", borderRadius: "20px", fontSize: "12px", fontWeight: "500" },
};

export default Admin;
