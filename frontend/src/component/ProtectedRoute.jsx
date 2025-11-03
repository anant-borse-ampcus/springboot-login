import { Navigate } from 'react-router-dom';

const ProtectedRoute = ({ children, allowedRoles = [] }) => {
    const userRole = sessionStorage.getItem('userRole');

    if (!userRole) {
        return <Navigate to="/login" />;
    }

    if (allowedRoles.length > 0 && !allowedRoles.includes(userRole)) {
        return <Navigate to="/unauthorized" />;
    }

    return children;
};

export default ProtectedRoute;