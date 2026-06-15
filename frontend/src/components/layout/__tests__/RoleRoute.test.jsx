import { render, screen } from '@testing-library/react'
import { MemoryRouter } from 'react-router-dom'
import { Provider } from 'react-redux'
import { configureStore } from '@reduxjs/toolkit'
import authReducer from '../../../features/auth/store/authSlice'
import RoleRoute from '../RoleRoute'

const renderWithProviders = (store, children, { initialEntries = ['/'] } = {}) => {
  return render(
    <Provider store={store}>
      <MemoryRouter initialEntries={initialEntries}>{children}</MemoryRouter>
    </Provider>
  )
}

describe('RoleRoute', () => {
  it('should redirect to /login when not authenticated', () => {
    const store = configureStore({
      reducer: { auth: authReducer },
      preloadedState: { auth: { isAuthenticated: false, role: null, user: null, token: null, loading: false, error: null } },
    })

    renderWithProviders(
      store,
      <RoleRoute allowedRoles={['USER']}><div>Protected Content</div></RoleRoute>
    )

    expect(screen.queryByText('Protected Content')).not.toBeInTheDocument()
  })

  it('should render children when authenticated with correct role', () => {
    const store = configureStore({
      reducer: { auth: authReducer },
      preloadedState: { auth: { isAuthenticated: true, role: 'USER', user: { id: '1', role: 'USER' }, token: 'abc', loading: false, error: null } },
    })

    renderWithProviders(
      store,
      <RoleRoute allowedRoles={['USER']}><div>Protected Content</div></RoleRoute>
    )

    expect(screen.getByText('Protected Content')).toBeInTheDocument()
  })

  it('should redirect to / when role does not match', () => {
    const store = configureStore({
      reducer: { auth: authReducer },
      preloadedState: { auth: { isAuthenticated: true, role: 'USER', user: { id: '1', role: 'USER' }, token: 'abc', loading: false, error: null } },
    })

    renderWithProviders(
      store,
      <RoleRoute allowedRoles={['ADMIN']}><div>Admin Content</div></RoleRoute>
    )

    expect(screen.queryByText('Admin Content')).not.toBeInTheDocument()
  })
})
