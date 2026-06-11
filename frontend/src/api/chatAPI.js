import axiosInstance from './axiosInstance'

export const getChatHistory = (consultationId) =>
  axiosInstance.get(`/chat/${consultationId}/messages`)

export const markAsRead = (consultationId) =>
  axiosInstance.put(`/chat/${consultationId}/read`)

export const getUnreadCount = () =>
  axiosInstance.get('/chat/unread-count')
