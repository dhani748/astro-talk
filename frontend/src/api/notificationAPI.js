import axiosInstance from './axiosInstance'

export const getNotifications = (params) =>
  axiosInstance.get('/notifications', { params })

export const markNotificationRead = (id) =>
  axiosInstance.put(`/notifications/${id}/read`)

export const markAllNotificationsRead = () =>
  axiosInstance.put('/notifications/read-all')

export const getUnreadNotificationCount = () =>
  axiosInstance.get('/notifications/unread-count')
