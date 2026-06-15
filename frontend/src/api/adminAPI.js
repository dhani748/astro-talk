import axiosInstance from './axiosInstance'

export const getAdminDashboard = () =>
  axiosInstance.get('/admin/dashboard')

export const getUsers = (params) =>
  axiosInstance.get('/admin/users', { params })

export const verifyAstrologer = (id, status) =>
  axiosInstance.put(`/admin/astrologers/${id}/verify`, { status })

export const getRevenueReport = (params) =>
  axiosInstance.get('/admin/revenue', { params })

export const getPendingAstrologers = (params) =>
  axiosInstance.get('/admin/astrologers/pending', { params })
