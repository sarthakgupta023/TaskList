import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";
import PrivateRoute from "./components/PrivateRoute";
import Admin from "./pages/admin";
import Login from "./pages/login";
import Signup from "./pages/signup";
import Tasks from "./pages/task";
function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Navigate to="/login" />} />
                <Route path="/login" element={<Login />} />
                <Route path="/signup" element={<Signup />} />
                <Route path="/tasks" element={
                    <PrivateRoute><Tasks /></PrivateRoute>
                } />
                <Route path="/admin" element={
                    <PrivateRoute><Admin /></PrivateRoute>
                } />   
            </Routes>
        </BrowserRouter>
    );
}

export default App;