import axiosInstance from './axiosInstance'

export const startConsultation = (data) =>
  axiosInstance.post('/consultations/start', data)

export const endConsultation = (id) =>
  axiosInstance.post(`/consultations/${id}/end`)

export const getConsultationHistory = (params) =>
  axiosInstance.get('/consultations/history', { params })

export const getActiveConsultation = () =>
  axiosInstance.get('/consultations/active')

export const getConsultationById = (id) =>
  axiosInstance.get(`/consultations/${id}`)
