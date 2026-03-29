import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import api from "../api/api";
import { useAuth } from "../context/AuthContext";

const Login = () => {
    const { login } = useAuth();
    const navigate = useNavigate();

    const [form, setForm] = useState({ email: "", password: "" });
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(false);

    const handleChange = (e) => {
        setForm({ ...form, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError("");
        setLoading(true);
        try {
            const res = await api.post("/auth/login", form);
            login(res.data.token);   
            navigate("/tasks");      
        } catch (err) {
            setError(err.response?.data || "Login failed");
        } finally {
            setLoading(false);
        }
    };

    return (
        <div style={styles.container}>
            <div style={styles.card}>
                <h2 style={styles.title}>Login</h2>

                {error && <p style={styles.error}>{error}</p>}

                <form onSubmit={handleSubmit} style={styles.form}>
                    <input
                        style={styles.input}
                        type="email"
                        name="email"
                        placeholder="Email"
                        value={form.email}
                        onChange={handleChange}
                        required
                    />
                    <input
                        style={styles.input}
                        type="password"
                        name="password"
                        placeholder="Password"
                        value={form.password}
                        onChange={handleChange}
                        required
                    />
                    <button style={styles.button} type="submit" disabled={loading}>
                        {loading ? "Logging in..." : "Login"}
                    </button>
                </form>

                <p style={styles.link}>
                    Don't have an account? <Link to="/signup">Sign up</Link>
                </p>
            </div>
        </div>
    );
};

const styles = {
    container: { display: "flex", justifyContent: "center", alignItems: "center", height: "100vh", backgroundColor: "#f0f2f5" },
    card: { backgroundColor: "#fff", padding: "40px", borderRadius: "12px", width: "360px", boxShadow: "0 2px 12px rgba(0,0,0,0.1)" },
    title: { marginBottom: "24px", textAlign: "center", fontSize: "22px" },
    form: { display: "flex", flexDirection: "column", gap: "14px" },
    input: { padding: "10px 14px", borderRadius: "8px", border: "1px solid #ddd", fontSize: "14px", outline: "none" },
    button: { padding: "10px", borderRadius: "8px", backgroundColor: "#4f46e5", color: "#fff", border: "none", fontSize: "15px", cursor: "pointer" },
    error: { color: "red", fontSize: "13px", marginBottom: "10px" },
    link: { textAlign: "center", marginTop: "16px", fontSize: "13px" }
};

export default Login;