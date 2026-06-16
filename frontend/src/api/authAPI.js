import axiosInstance from './axiosInstance'

export const loginUser = (credentials) =>
  axiosInstance.post('/auth/login', credentials)

export const registerUser = (data) =>
  axiosInstance.post('/auth/register', data)

export const registerAstrologer = (data) =>
  axiosInstance.post('/auth/register/astrologer', data)

export const getCurrentUser = () =>
  axiosInstance.get('/auth/me')

export const updateProfile = (data) =>
  axiosInstance.put('/auth/profile', data)

export const logoutUser = () =>
  axiosInstance.post('/auth/logout')

export const googleLogin = (idToken) =>
  axiosInstance.post('/auth/google', { idToken })
