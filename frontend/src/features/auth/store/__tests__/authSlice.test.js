import authReducer, { loginStart, loginSuccess, loginFailure, logout, updateUser, clearError } from '../authSlice'

const initialState = {
  user: null,
  token: null,
  isAuthenticated: false,
  role: null,
  loading: false,
  error: null,
}

describe('authSlice', () => {
  beforeEach(() => {
    localStorage.clear()
  })

  it('should return initial state', () => {
    const state = authReducer(undefined, { type: 'unknown' })
    expect(state.loading).toBe(false)
    expect(state.isAuthenticated).toBe(false)
    expect(state.user).toBeNull()
  })

  it('should handle loginStart', () => {
    const state = authReducer(initialState, loginStart())
    expect(state.loading).toBe(true)
    expect(state.error).toBeNull()
  })

  it('should handle loginSuccess', () => {
    const payload = { id: '1', name: 'Test', email: 'test@test.com', role: 'USER', token: 'abc' }
    const state = authReducer(initialState, loginSuccess(payload))
    expect(state.loading).toBe(false)
    expect(state.isAuthenticated).toBe(true)
    expect(state.user).toEqual({ id: '1', name: 'Test', email: 'test@test.com', role: 'USER' })
    expect(state.token).toBe('abc')
    expect(state.role).toBe('USER')
    expect(localStorage.getItem('token')).toBe('abc')
  })

  it('should handle loginFailure', () => {
    const state = authReducer(initialState, loginFailure('Invalid credentials'))
    expect(state.loading).toBe(false)
    expect(state.error).toBe('Invalid credentials')
  })

  it('should handle logout', () => {
    const loggedIn = { user: { id: '1' }, token: 'abc', isAuthenticated: true, role: 'USER', loading: false, error: null }
    localStorage.setItem('token', 'abc')
    const state = authReducer(loggedIn, logout())
    expect(state.user).toBeNull()
    expect(state.token).toBeNull()
    expect(state.isAuthenticated).toBe(false)
    expect(state.role).toBeNull()
    expect(localStorage.getItem('token')).toBeNull()
  })

  it('should handle updateUser', () => {
    const state = authReducer(
      { ...initialState, user: { id: '1', name: 'Old' } },
      updateUser({ name: 'New' })
    )
    expect(state.user.name).toBe('New')
  })

  it('should handle clearError', () => {
    const state = authReducer({ ...initialState, error: 'some error' }, clearError())
    expect(state.error).toBeNull()
  })
})
