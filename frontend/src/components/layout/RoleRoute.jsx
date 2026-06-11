import { Navigate } from 'react-router-dom'
import { useSelector } from 'react-redux'

const RoleRoute = ({ children, allowedRoles }) => {
  const { isAuthenticated, role } = useSelector((state) => state.auth)

  if (!isAuthenticated) {
    return <Navigate to="/login" replace />
  }

  if (!allowedRoles.includes(role)) {
    return <Navigate to="/" replace />
  }

  return children
}

export default RoleRoute
